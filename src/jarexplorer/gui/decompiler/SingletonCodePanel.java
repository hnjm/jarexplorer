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
 *  PropertiesPanel.java
 *
 * Created on 26 febbraio 2006, 9.56
 */

package jarexplorer.gui.decompiler;

import jarexplorer.gui.exceptions.JodeExceptionHandler;
import jarexplorer.gui.tree.IconFactory;
import jarexplorer.helpers.Preference;
import jarexplorer.helpers.SwingWorker;
import jarexplorer.model.ClassNode;
import jarexplorer.helpers.Utilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import org.wonderly.swing.tabs.CloseableTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import jode.decompiler.ProgressListener;
import org.wonderly.swing.tabs.TabCloseEvent;
import org.wonderly.swing.tabs.TabCloseListener;
import sdoc.Gutter;
import sdoc.SyntaxDocument;
import sdoc.SyntaxSupport;

/**
 *
 * @author Marco
 */
public class SingletonCodePanel extends JFrame implements ProgressListener {

    private CloseableTabbedPane tabPanel;
    private static SingletonCodePanel singletonCodePanel;

    private JPanel progressBarPanel;
    private JProgressBar progressBar;
    private static String log = "";

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem save;
    private JMenuItem close;
    private JMenuItem closeAll;
    private JMenuItem exit;

    private ArrayList<String> memory = new ArrayList<String>();

    /** Creates a new instance of CodePanel */
    private SingletonCodePanel() {

        Utilities util = new Utilities();

        tabPanel = new CloseableTabbedPane();
        //tabPanel.setTabLayoutPolicy(CloseableTabbedPane.SCROLL_TAB_LAYOUT);
        tabPanel.addTabCloseListener(new TabCloseListener() {

            @Override
            public void tabClosed(TabCloseEvent ev) {
                String label = tabPanel.getTitleAt(tabPanel.getSelectedIndex());
                memory.remove(label);
                tabPanel.remove(ev.getClosedTab());

                int tabCount = tabPanel.getTabCount();
                if (tabCount == 0) {
                    SingletonCodePanel.singletonCodePanel = null;
                    dispose();
                }
            }
        });
        setTitle("Jar Explorer");

        URL imgURL = ClassLoader.getSystemResource("jarexplorer/icons/viewmag.png");
        setIconImage(new ImageIcon(imgURL).getImage());

        setSize(util.getFrameWidth() + 200, util.getFrameHeight() + 50);
        setResizable(true);

        setLocation((util.getScreenWidth() / 2) - 200, (util.getScreenHeigth() / 2) - 200);

        setLayout(new BorderLayout());
        add(tabPanel, BorderLayout.CENTER);
        add(buildProgressBarPanel(), BorderLayout.SOUTH);
        setJMenuBar(buildMenu());

        disabledMenu();

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent event) {
                SingletonCodePanel.singletonCodePanel = null;
                dispose();
            }
        });

        setAlwaysOnTop(Preference.getPreference().isAlwaysOnTop());
        toFront();
    }

    public static SingletonCodePanel getSingletonCodePanel() {
        if (singletonCodePanel == null) {
            singletonCodePanel = new SingletonCodePanel();
        }
        return singletonCodePanel;
    }

    public static void alwaysOnTop(boolean b) {
        if (singletonCodePanel != null) {
            singletonCodePanel.setAlwaysOnTop(b);
        }
    }

    public static String getLog() {
        String tmp = log;
        log = "";
        return tmp;
    }

    public void addTabWorker(final ClassNode node, final SingletonCodePanel codePanel) {
        final SwingWorker worker = new SwingWorker() {

            @Override
            public Object construct() {
                synchronized (SingletonCodePanel.class) {
                    String tmpLabel = node.toString();
                    final String label = tmpLabel.substring(0, tmpLabel.indexOf(".class")) + ".java";

                    Thread.setDefaultUncaughtExceptionHandler(new JodeExceptionHandler(codePanel, label));

                    codePanel.setVisible(true);
                    enabledProgressPanel();

                    Jode decompiler = new Jode(node.getPath(), codePanel);
                    String sourceCode = decompiler.decompile();
                    log = decompiler.getLog();

                    addTab(label, sourceCode);
                    disabledProgressPanel();
                }
                return null;
            }

            //Runs on the event-dispatching thread.
            @Override
            public void finished() {
            }
        };
        worker.start();
    }

    public void addTab(String label, String str) {
        if (memory.contains(label) == false) {
            tabPanel.insertTab(label, IconFactory.getInstance().getIcon("FileJava"), buildTextAreaPanel(str), label, 0);
            tabPanel.setSelectedIndex(0);
            memory.add(label);
            enabledMenu();
        }
        toFront();
    }

    public void addLogTab(String label, String javaFile) {
        synchronized (JodeExceptionHandler.class) {

            if (memory.contains(label) == false) {
                JTextArea textarea = new JTextArea();
                textarea.setFont(new java.awt.Font("Dialog", 1, 10));
                textarea.setEditable(false);
                textarea.setText("Error decompiling " + javaFile + System.getProperty("line.separator"));
                textarea.addMouseListener(new TextAreaPopupMenu(textarea));

                JScrollPane scrollableTextArea = new JScrollPane(textarea);
                tabPanel.addTab(label, IconFactory.getInstance().getIcon("Error"), scrollableTextArea, label);
                tabPanel.setSelectedIndex(tabPanel.getTabCount() - 1);
                memory.add(label);
                enabledMenu();
            } else {
                int index = tabPanel.indexOfTab("Error ");
                //JPanel tmpPanel = (JPanel) tabPanel.getComponentAt(index);
                JScrollPane tmpScroll = (JScrollPane) tabPanel.getComponentAt(index);
                JTextArea textarea = (JTextArea) tmpScroll.getViewport().getComponent(0);
                textarea.append("Error decompiling " + javaFile + System.getProperty("line.separator"));
            }

            toFront();
        }
    }

    private JPanel buildTextAreaPanel(String str) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        JTextArea textarea = new JTextArea();
        //textarea.setFont(new java.awt.Font("Dialog", 1, 10));
        //get an instance of SyntaxSupport
        SyntaxSupport support = SyntaxSupport.getInstance();
        support.setPrintMarginWidth(80);


        //have the support object add highlight support to a text component
        //the text component will get a special ui and a SyntaxDocument
        //This will also set the text component with a fixed width font
        support.addSupport(SyntaxSupport.JAVA_LEXER, textarea);

        textarea.getDocument().putProperty(SyntaxDocument.tabSizeAttribute, 8);

        textarea.setSelectionColor(new Color(184, 207, 229));
        textarea.setText(str);
        textarea.setEditable(false);

        JScrollPane scrollableTextArea = new JScrollPane(textarea);
        //add the gutter - line number area
        Gutter gutter = new Gutter(textarea, scrollableTextArea);
        scrollableTextArea.setRowHeaderView(new Gutter(textarea, scrollableTextArea));

        panel.add(scrollableTextArea, BorderLayout.CENTER);
        return panel;
    }

    private JMenuBar buildMenu() {
        menuBar = new JMenuBar();
        file = new JMenu("File");

        save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                File target = showFileChooser();
                if (target != null) {
                    saveFile(target);
                }
            }
        });

        close = new JMenuItem("Close");
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tabPanel.getTabCount() > 0) {
                    String label = tabPanel.getTitleAt(tabPanel.getSelectedIndex());
                    memory.remove(label);
                    tabPanel.remove(tabPanel.getSelectedIndex());
                }

                if (tabPanel.getTabCount() == 0) {
                    disabledMenu();
                }
            }
        });

        closeAll = new JMenuItem("Close all");
        closeAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        closeAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                closeAllTabs();
            }
        });

        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                closeAllTabs();
                dispose();
                SingletonCodePanel.singletonCodePanel = null;
            }
        });

        file.add(save);
        file.add(close);
        file.add(closeAll);
        file.add(new JSeparator());
        file.add(exit);
        menuBar.add(file);

        return menuBar;
    }

    private void saveFile(File target) {
        JPanel tmpPanel = (JPanel) tabPanel.getSelectedComponent();
        JScrollPane tmpScroll = (JScrollPane) tmpPanel.getComponent(0);
        JTextArea textarea = (JTextArea) tmpScroll.getViewport().getComponent(0);
        String s = textarea.getText();
        FileOutputStream fstream;
        try {
            fstream = new FileOutputStream(target);
            fstream.write(s.getBytes());
            fstream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private File showFileChooser() {
        JFileChooser fc = new JFileChooser();

        String label = tabPanel.getTitleAt(tabPanel.getSelectedIndex());

        File f = new File(label);
        fc.setSelectedFile(f);
        int result = fc.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File tmpfile = fc.getSelectedFile();
            return tmpfile;
        } else {
            return null;
        }
    }

    private JPanel buildProgressBarPanel() {
        progressBarPanel = new JPanel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        progressBarPanel.setLayout(new BorderLayout());
        progressBarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        progressBarPanel.add(progressBar, BorderLayout.CENTER);

        return progressBarPanel;
    }

    @Override
    public void updateProgress(double d, String string) {
        progressBar.setValue((int) (d*100));
    }

    private void disabledMenu() {
        save.setEnabled(false);
        close.setEnabled(false);
        closeAll.setEnabled(false);
    }

    private void enabledMenu() {
        save.setEnabled(true);
        close.setEnabled(true);
        closeAll.setEnabled(true);
    }

    private void closeAllTabs() {
        memory.clear();
        disabledMenu();
        tabPanel.removeAll();
    }

    public void enabledProgressPanel() {
        progressBar.setValue(0);
        progressBarPanel.setVisible(true);
        repaint();
    }

    public void disabledProgressPanel() {
        progressBarPanel.setVisible(false);
        repaint();
    }
}