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
 * FileLabelNode.java
 *
 * Created on October 31, 2006, 8:47 PM
 */

package jarexplorer.model;

import jarexplorer.parser.MyField;
import jarexplorer.parser.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author ap
 */
public class FieldNodeLabel implements MyTreeNode {

    private String label;
    private Parser myParser;
    private MyField[] fields;

    public FieldNodeLabel(String label, String className) {

        this.label = label;
        String pathToClass = Utilities.formatClassPathDir(className);
        String jarFile = Utilities.getJarPath();

        try {
            myParser = new Parser(Utilities.getJarPath(), pathToClass);
            fields = myParser.getFields();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public Object getChild(Object parent, int index) {
        Arrays.sort(fields, new Comparator<MyField>() {

            @Override
            public int compare(MyField o1, MyField o2) {
                String s1 = o1.getName();
                String s2 = o2.getName();
                return s1.compareTo(s2);
            }
        });
        return new FieldNode(fields[index]);
    }

    @Override
    public int getChildCount(Object parent) {
        return fields.length;
    }

    @Override
    public boolean isLeaf(Object node) {
        return fields.length == 0 ? true : false;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        FieldNodeLabel p = (FieldNodeLabel) parent;
        FieldNode c = (FieldNode) child;

        ArrayList<MyField> tmp = new ArrayList<MyField>(Arrays.asList(p.fields));

        return tmp.indexOf(c.getField());
    }

    @Override
    public String getType() {
        return "FieldNodeLabel";
    }

    @Override
    public String toString() {
        return label;
    }
}
