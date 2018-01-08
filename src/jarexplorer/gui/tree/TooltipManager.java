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
 *  TooltipManager.java
 *
 * Created on 3 novembre 2006, 11.55
 */

package jarexplorer.gui.tree;

import jarexplorer.model.ConstructorNode;
import jarexplorer.model.FieldNode;
import jarexplorer.model.MethodNode;
import jarexplorer.model.MyTreeNode;
import jarexplorer.parser.MyField;
import jarexplorer.parser.MyMethod;
import java.util.HashMap;

interface ToolTipeable {

    String getTooltip(MyTreeNode node);
}

/**
 *
 * @author mmilon
 */
public class TooltipManager {

    HashMap<String, ToolTipeable> map = new HashMap<String, ToolTipeable>();

    /** Creates a new instance of TooltipManager */
    public TooltipManager() {
        map.put("FieldNode", new FieldTooltip());
        map.put("MethodNode", new MethodTooltip());
        map.put("ConstructorNode", new ConstructorTooltip());
    }

    public String getTooltip(String type, MyTreeNode node) {
        if (map.containsKey(type)) {
            return map.get(type).getTooltip(node);
        } else {
            return null;
        }
    }
}

class FieldTooltip implements ToolTipeable {

    @Override
    public String getTooltip(MyTreeNode node) {
        FieldNode fieldNode = (FieldNode) node;
        MyField field = fieldNode.getField();
        return field.getModifiers() + " " + field.getSignature() + " " + field.getName();
    }
}

class MethodTooltip implements ToolTipeable {

    @Override
    public String getTooltip(MyTreeNode node) {
        MethodNode methodNode = (MethodNode) node;
        MyMethod method = methodNode.getMethod();
        String tooltip = method.getModifiers() + " " + method.getReturnType() + " " + method.getName() + " " + method.getSignature();
        if (method.getExceptions() != null && method.getExceptions().length() > 0) {
            tooltip += " throws " + method.getExceptions();
        }

        return tooltip;
    }
}

class ConstructorTooltip implements ToolTipeable {

    @Override
    public String getTooltip(MyTreeNode node) {
        ConstructorNode methodNode = (ConstructorNode) node;
        MyMethod method = methodNode.getMethod();
        String tooltip = method.getModifiers() + " " + method.getReturnType() + " " + method.getName() + " " + method.getSignature();
        if (method.getExceptions() != null && method.getExceptions().length() > 0) {
            tooltip += " throws " + method.getExceptions();
        }

        return tooltip;
    }
}
