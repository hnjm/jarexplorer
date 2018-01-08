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
 *  ClassTreeNode.java
 *
 * Created on 31 ottobre 2006, 17.08
 */

package jarexplorer.model;

import jarexplorer.parser.MyClass;
import jarexplorer.parser.Parser;
import java.io.IOException;

/**
 *
 * @author mmilon
 */
public class ClassNode implements MyTreeNode {

    String className;
    String classPathDir;
    String path;
    private MyClass myClass;

    public ClassNode(String className) {
        this.path = className;

        classPathDir = className;
        this.className = Utilities.formatClassName(className);

        String pathToClass = Utilities.formatClassPathDir(className);
        String jarFile = Utilities.getJarPath();

        try {
            Parser myParser = new Parser(jarFile, pathToClass);
            myClass = myParser.getMyClass();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public String getPath() {
        return path;
    }

    @Override
    public String getType() {
        return "ClassNode";
    }

    @Override
    public String toString() {
        return className;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return new ClassNodeLabel(classPathDir);
    }

    @Override
    public int getChildCount(Object parent) {
        return 1;
    }

    @Override
    public boolean isLeaf(Object node) {
        return false;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    public MyClass getMyClass() {
        return myClass;
    }
}
