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
 * JarTreeModel.java
 *
 * Created on October 30, 2006, 8:17 PM
 */

package jarexplorer.model;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import de.schlichtherle.io.File;
import java.util.HashMap;

/**
 *
 * @author ap
 */
public class JarTreeModel implements TreeModel {

    private File jarFile;
    private FileNode root;
    private HashMap<String, Integer> memory = new HashMap<String, Integer>();

    public JarTreeModel(String jar) {
        jarFile = new File(jar);

        root = new FileNode(jarFile, "RootNode");
        Utilities.setJarPath(jar);
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        MyTreeNode node = (MyTreeNode) parent;
        return node.getChild(parent, index);
    }

    @Override
    public int getChildCount(Object parent) {
        MyTreeNode node = (MyTreeNode) parent;
        return node.getChildCount(parent);
    }

    @Override
    public boolean isLeaf(Object node) {
        MyTreeNode leaf = (MyTreeNode) node;
        return leaf.isLeaf(node);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        MyTreeNode p = (MyTreeNode) parent;
        MyTreeNode c = (MyTreeNode) child;
        String key = p.toString() + c.toString();
        if (memory.containsKey(key)) {
            return memory.get(key);
        } else {
            /*
            System.out.println("+-----------------------------------------------------------+");
            System.out.println("parent: " + p.toString() + " child: " + c.toString());
            System.out.println("getIndexOfChild:  " + p.getIndexOfChild(parent, child));
            System.out.println("+-----------------------------------------------------------+");
             */
            int index = p.getIndexOfChild(parent, child);
            memory.put(key, index);
            return index;
        }
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }
}