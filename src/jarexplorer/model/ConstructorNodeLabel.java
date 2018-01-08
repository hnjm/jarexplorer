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
 * ConstructorLabelNode.java
 *
 * Created on October 31, 2006, 8:46 PM
 */

package jarexplorer.model;

import jarexplorer.parser.MyMethod;
import jarexplorer.parser.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author ap
 */
public class ConstructorNodeLabel implements MyTreeNode {

    private String label;
    private String className;
    private Parser myParser;
    private MyMethod[] methods;

    public ConstructorNodeLabel(String label, String className) {
        this.label = label;
        String pathToClass = Utilities.formatClassPathDir(className);
        String jarFile = Utilities.getJarPath();
        try {
            myParser = new Parser(Utilities.getJarPath(), pathToClass);
            methods = myParser.getConstructors();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public Object getChild(Object parent, int index) {
        Arrays.sort(methods, new Comparator<MyMethod>() {

            @Override
            public int compare(MyMethod o1, MyMethod o2) {
                String s1 = o1.getName();
                String s2 = o2.getName();
                return s1.compareTo(s2);
            }
        });
        return new ConstructorNode(methods[index]);
    }

    @Override
    public int getChildCount(Object parent) {
        return methods.length;
    }

    @Override
    public boolean isLeaf(Object node) {
        return methods.length == 0 ? true : false;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        ConstructorNodeLabel p = (ConstructorNodeLabel) parent;
        ConstructorNode c = (ConstructorNode) child;

        ArrayList<MyMethod> tmp = new ArrayList<MyMethod>(Arrays.asList(p.methods));

        return tmp.indexOf(c.getMethod());
    }

    @Override
    public String getType() {
        return "ConstructorNodeLabel";
    }

    @Override
    public String toString() {
        return label;
    }
}