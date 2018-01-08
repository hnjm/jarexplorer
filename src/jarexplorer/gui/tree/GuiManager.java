/*
 * Copyright (C) 2006  Marco Milon
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 *  GuiManager.java
 *
 * Created on 30 ottobre 2006, 12.56
 */

package jarexplorer.gui.tree;

import jarexplorer.gui.bar.Clickable;
import jarexplorer.gui.bar.StatusBar;
import jarexplorer.gui.decompiler.SingletonCodePanel;
import jarexplorer.helpers.SwingWorker;
import jarexplorer.helpers.CircularArrayList;
import jarexplorer.helpers.CmdArgsWrapper;
import jarexplorer.helpers.Preference;
import jarexplorer.helpers.Register;
import jarexplorer.helpers.Version;
import java.awt.Cursor;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author mmilon
 */
public class GuiManager implements LifeCycle {
        private static GuiManager guiManager;
        private static MainUI ui;
        private MainMenu menu;
        private DropJarPanel dropJarPanel;
        private TreePanel treePanel;
        private DropJarListener dropJarListener;
        private Timer timer;
        
        private GuiManager(String[] args) throws Exception {
                ui = new MainUI();
                
                dropJarListener = new DropJarListener(this);
                
                dropJarPanel = new DropJarPanel();
                new DropTarget(dropJarPanel, dropJarListener);
                
                treePanel = new TreePanel();
                menu = new MainMenu(this);
                ui.setJMenuBar(menu.getMenuBar());
                
                if(CmdArgsWrapper.isJar(args)) {
                        initWidthArguments(args[0]);
                } else {
                        init();
                }
                
                ui.setVisible(true);
                
                checkForUpdates();
        }
        
        public static GuiManager getGuiManager(String[] args) throws Exception {
                if (guiManager == null) {
                        guiManager = new GuiManager(args);
                }
                return guiManager;
        }
        
        public static GuiManager getGuiManager() {
                String[] args = {};
                if (guiManager == null) {
                        try {
                                guiManager = new GuiManager(args);
                        } catch (Exception ex) {
                                ex.printStackTrace();
                        }
                }
                return guiManager;
        }
        
        public void initWidthArguments(String jarFilePath) {
                getUi().requestFocus();
                if(jarFilePath != null) {
                        startWorker(jarFilePath);
                }
        }
        
        public void init() {
                dropJarPanel.addToFrame(getUi());
                //new StatusBar().addToFrame(getUi());
                menu.init(Preference.getPreference().isAlwaysOnTop());
                setAlwaysOnTop(Preference.getPreference().isAlwaysOnTop());
                //treePanel.addToFrame(ui);
        }
        
        @Override
        public void preLoading() {
                final String path = showFileChooser(getUi());
                getUi().requestFocus();
                if(path != null) {
                        startWorker(path);
                }
        }
        
        public void jarDropped(String path) {
                //dropJarPanel.removeFromFrame(ui);
                if(treePanel.isVisible()) {
                        treePanel.removeFromFrame(getUi());
                        dropJarPanel.addToFrame(getUi());
                }
                startWorker(path);
        }
        
        private void checkForUpdates() {
                final SwingWorker worker = new SwingWorker() {
                        @Override
                        public Object construct() {
                                if(Preference.getPreference().isShowMeUpdateMessage()) {
                                        String iconPath = "jarexplorer/icons/update.gif";
                                        Clickable c = new Clickable() {
                                                @Override
                                                public void onMouseClicked(MouseEvent evt) {
                                                    JOptionPane.showMessageDialog(GuiManager.getUi(), new UpdatePanel(), "New version available", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                        };
                                        
                                        try {
                                            Version v = Version.getVersionWithDelay();
                                            if(v.isUptodate() == false) {
                                                    StatusBar statusBar = new StatusBar(c, iconPath, "New version available");
                                                    statusBar.addToFrame(GuiManager.getUi());
                                            }
                                        } catch(Exception ex) {
                                            // ignore cannot connect to server
                                        }
                                }
                                
                                return null;
                        }
                };
                worker.start();
        }
        
        private void startWorker(final String path) {
                timer = new Timer();
                final SwingWorker worker = new SwingWorker() {
                        
                        int delay = 0;
                        int period = 25;
                        
                        @Override
                        public Object construct() {
                                loading();
                                dropJarPanel.loading(path);
                                getUi().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                
                                final CircularArrayList circularArrayList = new CircularArrayList();
                                circularArrayList.put("Loading " + path + " |");
                                circularArrayList.put("Loading " + path + " /");
                                circularArrayList.put("Loading " + path + " -");
                                circularArrayList.put("Loading " + path + " \\");
                                
                                timer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                            public void run() {
                                                dropJarPanel.setLabel(circularArrayList.get());
                                            }
                                }, delay, period);
                                
                                treePanel.buildTree(path);
                                new DropTarget(treePanel.tree, dropJarListener);
                                return null;
                        }
                        
                        //Runs on the event-dispatching thread.
                        @Override
                        public void finished() {
                                timer.cancel();
                                getUi().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                loaded();
                        }
                };
                worker.start();
        }
        
        @Override
        public void loading() {
                getUi().toFront();
                getUi().requestFocus();
                menu.loading();
        }
        
        @Override
        public void loaded() {
                menu.loaded();
                dropJarPanel.removeFromFrame(getUi());
                treePanel.addToFrame(getUi());
                //treePanel.addListeners(ui);
        }
        
        public void loadFailed() {
                timer.cancel();
                dropJarPanel.setLabel("Loading failed.");
                getUi().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        
        public void reset() {
                dropJarPanel.setLabel("Drop Jar Here.");
        }
        
        @Override
        public void closed() {
                menu.closed();
                dropJarPanel.loaded();
                treePanel.removeFromFrame(getUi());
                dropJarPanel.addToFrame(getUi());
        }
        
        public void showAboutPanel() {
                new AboutDialog(getUi()).setVisible(true);
        }
        
        private String showFileChooser(JFrame ui) {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                        @Override
        public boolean accept(File f) {
                                return f.getName().toLowerCase().endsWith(".jar") || f.isDirectory();
                        }
                        
                        @Override
        public String getDescription() {
                                return "*.jar";
                        }
                });
                fc.setCurrentDirectory(Register.getCurrentDirectory());
                int result = fc.showOpenDialog(ui);
                
                if(result == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        Register.setCurrentDirectory(file);
                        return file.getAbsolutePath();
                } else {
                        return null;
                }
        }
        
        void setAlwaysOnTop(boolean b) {
                getUi().setAlwaysOnTop(b);
                SingletonCodePanel.alwaysOnTop(b);
                Preference.getPreference().setAlwaysOnTop(b);
        }
        
        public static MainUI getUi() {
                return ui;
        }
}

