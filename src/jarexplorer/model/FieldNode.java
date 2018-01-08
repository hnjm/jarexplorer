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
 * FieldTreeNode.java
 *
 * Created on October 31, 2006, 8:02 PM
 */

package jarexplorer.model;

import jarexplorer.parser.MyField;

/**
 *
 * @author ap
 */
public class FieldNode implements MyTreeNode {

    private MyField field;

    public FieldNode(MyField field) {
        this.field = field;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        return true;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 1;
    }

    @Override
    public String getType() {
        return "FieldNode";
    }

    @Override
    public String toString() {
        return field.getName();
    }

    public MyField getField() {
        return field;
    }
}
