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
 *  FileTreeNode.java
 *
 * Created on 31 ottobre 2006, 10.10
 */

package jarexplorer.model;

import de.schlichtherle.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author mmilon
 */
public class FileNode implements MyTreeNode {

    private File file;
    private static HashMap<String, ArrayList<File>> memory = new HashMap<String, ArrayList<File>>();

    private String type;

    public FileNode(File file) {
        this.file = file;
        type = "FileNode";
    }

    public FileNode(File file, String type) {
        this.file = file;
        this.type = "RootNode";
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    @Override
    public String getType() {
        return type;
    }

    /**
     *
     * @todo find a way to cast Object[] childs into File[] childs
     */
    @SuppressWarnings(value = "unchecked")
    @Override
    public Object getChild(Object parent, int index) {
        FileNode node = (FileNode) parent;
        ArrayList<File> list;

        if (memory.containsKey(node.getFile().getAbsolutePath())) {
            list = memory.get(node.getFile().getAbsolutePath());
        } else {
            Object[] childs = node.getFile().listFiles(new FileFilter() {

                @Override
                public boolean accept(java.io.File file) {
                    return file.getName().indexOf("$") != -1 ? false : true;
                }
            });

            list = new ArrayList(Arrays.asList(childs));

            Collections.sort(list, new FileComparator());
            memory.put(node.getFile().getAbsolutePath(), list);
        }

        File child = list.get(index);
        if (child.getName().endsWith(".class")) {
            String name = child.getAbsolutePath();
            return new ClassNode(name);
        } else {
            return new FileNode(child);
        }
    }

    @Override
    public int getChildCount(Object parent) {
        FileNode node = (FileNode) parent;
        return node.getFile().listFiles(new FileFilter() {

            @Override
            public boolean accept(java.io.File file) {
                return file.getName().indexOf("$") != -1 ? false : true;
            }
        }).length;
    }

    @Override
    public boolean isLeaf(Object node) {
        FileNode leaf = (FileNode) node;
        return leaf.getFile().isFile();
    }

    /**
     *
     * @todo find a way to cast Object[] childs into File[] childs
     */
    @SuppressWarnings(value = "unchecked")
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        FileNode p = (FileNode) parent;
        MyTreeNode c = (MyTreeNode) child;
        ArrayList<File> list;

        if (memory.containsKey(p.getFile().getAbsolutePath())) {
            list = memory.get(p.getFile().getAbsolutePath());
        } else {
            Object[] childs = p.getFile().listFiles();
            list = new ArrayList(Arrays.asList(childs));
            Collections.sort(list, new FileComparator());
            memory.put(p.getFile().getAbsolutePath(), list);
        }

        /*
         * @todo  may slowdown application
         */
        ArrayList<String> tmp = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            tmp.add(list.get(i).getName());
        }

        return tmp.indexOf(c.toString());
    }
}

class FileComparator implements java.util.Comparator<File> {

    @Override
    public int compare(File file1, File file2) {
        if (file1.isDirectory() && file2.isDirectory() || file1.isFile() && file2.isFile()) {
            return file1.getName().compareTo(file2.getName());
        } else if (file1.isDirectory() && file2.isFile()) {
            return -1;
        } else {
            return +1;
        }
    }
}