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
 * ClassNameTreeNode.java
 *
 * Created on October 31, 2006, 8:08 PM
 */

package jarexplorer.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ap
 */
public class ClassNodeLabel implements MyTreeNode {

    private String name;
    private String className;
    private MyTreeNode[] childs;

    public ClassNodeLabel(String className) {

        String tmp = Utilities.formatClassName(className);
        name = tmp.substring(0, tmp.indexOf(".class"));

        this.className = className;
        childs = new MyTreeNode[3];

        childs[0] = new FieldNodeLabel("Fields", className);
        childs[1] = new ConstructorNodeLabel("Constructors", className);
        childs[2] = new MethodNodeLabel("Methods", className);

        String pathToClass = Utilities.formatClassPathDir(className);
        String jarFile = Utilities.getJarPath();
    }

    @Override
    public Object getChild(Object parent, int index) {
        return childs[index];
    }

    @Override
    public int getChildCount(Object parent) {
        return 3;
    }

    @Override
    public boolean isLeaf(Object node) {
        return false;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        ArrayList<MyTreeNode> list = new ArrayList<MyTreeNode>(Arrays.asList(childs));
        return list.indexOf(child);
    }

    @Override
    public String getType() {
        return "ClassNodeLabel";
    }

    @Override
    public String toString() {
        return name;
    }
}