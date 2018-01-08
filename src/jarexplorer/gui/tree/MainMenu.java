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
 *  MainMenu.java
 *
 * Created on 30 ottobre 2006, 12.08
 */

package jarexplorer.gui.tree;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 *
 * @author mmilon
 */
public class MainMenu implements LifeCycle {

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem open;
    private JMenuItem close;
    private JMenuItem exit;

    private JCheckBoxMenuItem onTop;

    private GuiManager uiManager;

    public MainMenu(GuiManager manager) {
        uiManager = manager;
        initComponents();
    }

    private void initComponents() {
        menuBar = new JMenuBar();
        file = new JMenu("File");

        open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        open.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                uiManager.preLoading();
            }
        });

        close = new JMenuItem("Close");
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                uiManager.closed();
            }
        });

        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });

        file.add(open);
        file.add(close);
        file.add(new JSeparator());
        file.add(exit);

        JMenu window = new JMenu("Window");
        onTop = new JCheckBoxMenuItem("Always On top");
        onTop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        onTop.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    uiManager.setAlwaysOnTop(false);
                } else {
                    uiManager.setAlwaysOnTop(true);
                }
            }
        });
        window.add(onTop);

        JMenu help = new JMenu("Help");
        JMenuItem checkForUpdates = new JMenuItem("Check for updates...");
        checkForUpdates.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        checkForUpdates.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                GuiManager.getUi().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                JOptionPane.showMessageDialog(GuiManager.getUi(), new UpdatePanel(), "Check for updates...", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                uiManager.showAboutPanel();
            }
        });
        help.add(checkForUpdates);
        help.add(about);

        menuBar.add(file);
        menuBar.add(window);
        menuBar.add(help);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void init() {
        open.setEnabled(true);
        close.setEnabled(false);
        exit.setEnabled(true);
    }

    public void init(boolean alwaysOnTop) {
        open.setEnabled(true);
        close.setEnabled(false);
        exit.setEnabled(true);

        if (alwaysOnTop) {
            onTop.setSelected(true);
        }
    }

    @Override
    public void preLoading() {
    }

    @Override
    public void loading() {
        open.setEnabled(false);
        close.setEnabled(false);
        exit.setEnabled(true);
    }

    @Override
    public void loaded() {
        open.setEnabled(false);
        close.setEnabled(true);
        exit.setEnabled(true);
    }

    @Override
    public void closed() {
        init();
    }
}