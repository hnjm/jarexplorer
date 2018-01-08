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
 *  TreeRenderer.java
 *
 * Created on 2 novembre 2006, 16.18
 */

package jarexplorer.gui.tree;

import jarexplorer.model.MyTreeNode;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author mmilon
 */
public class TreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        IconFactory iconFactory = IconFactory.getInstance();
        TooltipManager manager = new TooltipManager();

        //URL imgDirURL = ClassLoader.getSystemResource("jarexplorer/icons/directory.gif");
        //setClosedIcon(new ImageIcon(imgDirURL));
        //setOpenIcon(new ImageIcon(imgDirURL));
        MyTreeNode node = (MyTreeNode) value;

        ImageIcon icon = iconFactory.getIcon(node, leaf);
        setIcon(icon);
        setToolTipText(manager.getTooltip(node.getType(), node));

        return this;
    }
}