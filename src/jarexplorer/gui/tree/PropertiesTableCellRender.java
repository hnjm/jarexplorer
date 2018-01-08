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
 *  PropertiesTableCellRender.java
 *
 * Created on 6 novembre 2006, 15.21
 */

package jarexplorer.gui.tree;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author mmilon
 */
public class PropertiesTableCellRender extends JLabel implements TableCellRenderer {
    
    @Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Configure the component with the specified value
        setText(value.toString());
        
        // Set tool tip if desired
        String text = (String)value;
        if(text != null && text.length() > 0) {
            setToolTipText(text);
        }
        
        //setForeground(java.awt.Color.lightGray);
        
        // Since the renderer is a component, return itself
        return this;
    }
    
    // The following methods override the defaults for performance reasons
    @Override
public void validate() {}
    @Override
public void revalidate() {}
    @Override
protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    @Override
public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}
