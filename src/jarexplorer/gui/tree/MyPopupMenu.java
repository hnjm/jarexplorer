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

package jarexplorer.gui.tree;

import jarexplorer.gui.decompiler.SingletonCodePanel;
import jarexplorer.helpers.Utilities;
import jarexplorer.model.ClassNode;
import jarexplorer.model.FileNode;
import jarexplorer.model.MyTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 *
 * @author Marco
 */
public class MyPopupMenu extends MouseAdapter {

    private JTree tree;
    private JPopupMenu menu;
    private JMenuItem open;
    private JMenuItem decompile;
    private JMenuItem properties;

    private int x;
    private int y;

    public MyPopupMenu(final JTree tree) {

        this.tree = tree;

        menu = new JPopupMenu();

        open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath selPath = tree.getPathForLocation(x, y);
                FileNode node = (FileNode) selPath.getLastPathComponent();

                String path = node.getFile().getAbsolutePath();
                Utilities.launchFile(node, path);
            }
        });

        decompile = new JMenuItem("Decompile");
        decompile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                TreePath selPath = tree.getPathForLocation(x, y);

                TreePath[] paths = tree.getSelectionPaths();
                SingletonCodePanel codePanel = SingletonCodePanel.getSingletonCodePanel();
                for (TreePath curPath : paths) {
                    MyTreeNode node = (MyTreeNode) curPath.getLastPathComponent();
                    if (node.getType().equalsIgnoreCase("ClassNode")) {
                        ClassNode classNode = (ClassNode) node;
                        codePanel.addTabWorker(classNode, codePanel);
                    }
                }
                tree.setSelectionPath(null);
            }
        });

        properties = new JMenuItem("Properties");
        properties.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath selPath = tree.getPathForLocation(x, y);
                MyTreeNode node = (MyTreeNode) selPath.getLastPathComponent();
                if (node.getType().equalsIgnoreCase("ClassNode")) {
                    new PropertiesPanel(node).setVisible(true);
                }
            }
        });

        menu.add(open);
        menu.addSeparator();
        menu.add(properties);
        menu.add(decompile);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (isNodeValid(e)) {
            displayMenu(e);
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        if (isNodeValid(e)) {
            displayMenu(e);
        }
    }

    private void displayMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private boolean isNodeValid(java.awt.event.MouseEvent e) {
        int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());

        if (selRow != -1) {
            this.x = e.getX();
            this.y = e.getY();

            MyTreeNode node = (MyTreeNode) selPath.getLastPathComponent();
            String type = node.getType();

            if (type.equalsIgnoreCase("ClassNode")) {
                TreePath[] paths = tree.getSelectionPaths();
                if (paths == null || paths.length == 1) {
                    tree.setSelectionPath(selPath);
                }

                showClassMenu();
                return true;
            } else if (type.equalsIgnoreCase("FileNode") && node.isLeaf(node) && Utilities.isWindows()) {

                showFileMenu();
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private void showFileMenu() {
        open.setEnabled(true);
        properties.setEnabled(false);
        decompile.setEnabled(false);
    }

    private void showClassMenu() {
        open.setEnabled(false);
        properties.setEnabled(true);
        decompile.setEnabled(true);
    }
}