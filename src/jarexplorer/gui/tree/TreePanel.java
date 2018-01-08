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
 *  TreePanel.java
 *
 * Created on 30 ottobre 2006, 13.04
 */

package jarexplorer.gui.tree;

import jarexplorer.helpers.Utilities;
import jarexplorer.model.FileNode;
import jarexplorer.model.JarTreeModel;
import jarexplorer.model.MyTreeNode;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author mmilon
 */
public class TreePanel extends JPanel {

    JTree tree;
    private TreeModel jarTreeModel = null;
    private JScrollPane scrollPane;
    private boolean visible;

    private MouseMotionListener mm;
    private MouseListener ml;

    public TreePanel() {
        initComponents();
    }

    private void initComponents() {
        //setBorder(javax.swing.BorderFactory.createTitledBorder("Jar Explorer"));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        setLayout(new BorderLayout());
    }

    public void addToFrame(JFrame frame) {
        frame.add(this, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    public void removeFromFrame(JFrame frame) {
        this.remove(scrollPane);

        tree = null;
        jarTreeModel = null;
        scrollPane = null;
        visible = false;
        frame.remove(this);
        repaint();
        revalidate();
    }

    public JTree buildTree(String pathToJar) {
        jarTreeModel = new JarTreeModel(pathToJar);
        tree = new JTree(jarTreeModel);
        tree.setCellRenderer(new TreeRenderer());
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.setShowsRootHandles(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        ml = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());

                if (selRow != -1) {
                    if (e.getClickCount() == 1) {
                        mySingleClick(selRow, selPath);
                    } else if (e.getClickCount() == 2) {
                        myDoubleClick(selRow, selPath);
                    }
                }
            }
        };
        tree.addMouseListener(ml);

        mm = new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    onMouseMoved(selRow, selPath);
                } else {
                    getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        };
        tree.addMouseMotionListener(mm);

        tree.addMouseListener(new MyPopupMenu(tree));

        ToolTipManager.sharedInstance().registerComponent(tree);
        scrollPane = new JScrollPane(tree);
        scrollPane.setBorder(null);
        visible = true;
        add(scrollPane, BorderLayout.CENTER);



        return tree;
    }

    private void onMouseMoved(int selRow, TreePath selPath) {
        MyTreeNode node = (MyTreeNode) selPath.getLastPathComponent();
        String type = node.getType();
        String[] types = {"FieldNode", "MethodNode", "ConstructorNode"};
        List validNodes = Arrays.asList(types);

        Cursor normalCursor;
        if (validNodes.contains(type)) {
            normalCursor = new Cursor(Cursor.HAND_CURSOR);
        } else {
            normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        }
        getParent().setCursor(normalCursor);
    }

    private void mySingleClick(int selRow, TreePath selPath) {
        MyTreeNode node = (MyTreeNode) selPath.getLastPathComponent();
        String type = node.getType();

        String[] types = {"FieldNode", "MethodNode", "ConstructorNode"};
        List validNodes = Arrays.asList(types);

        if (validNodes.contains(type)) {
            new PropertiesPanel(node).setVisible(true);
        }
    }

    private void myDoubleClick(int selRow, TreePath selPath) {
        MyTreeNode node = (MyTreeNode) selPath.getLastPathComponent();
        String type = node.getType();

        if (type.equalsIgnoreCase("FileNode") && node.isLeaf(node) && Utilities.isWindows()) {
            FileNode fileNode = (FileNode) node;
            Utilities.launchFile(fileNode, fileNode.getFile().getAbsolutePath());
        }
    }

    void addListeners(MainUI ui) {
        tree.addTreeExpansionListener(new TreeEventListener(ui));
        tree.addTreeWillExpandListener(new TreeEventListener(ui));
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
