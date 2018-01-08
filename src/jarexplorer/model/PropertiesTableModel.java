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
 *  PropertiesTableModel.java
 *
 * Created on 6 novembre 2006, 14.26
 */

package jarexplorer.model;

import jarexplorer.parser.MyClass;
import jarexplorer.parser.MyField;
import jarexplorer.parser.MyMethod;
import java.util.LinkedHashMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mmilon
 */
public class PropertiesTableModel extends AbstractTableModel {

    private LinkedHashMap<String, String> table;
    private int tableSize;

    /** Creates a new instance of PropertiesTableModel */
    public PropertiesTableModel(MyField field) {
        table = new LinkedHashMap<String, String>();
        table.put("Modifiers", field.getModifiers());
        table.put("Name", field.getName());
        table.put("Type", field.getSignature());
        table.put("Initial Value", field.getValue());
        tableSize = table.size();
    }

    public PropertiesTableModel(MyMethod method, boolean constructor) {
        table = new LinkedHashMap<String, String>();
        table.put("Modifiers", method.getModifiers());
        table.put("Name", method.getName());
        String parameters = method.getSignature().replace("(", "").replace(")", "");
        table.put("Parameters", parameters);
        if (!constructor) {
            table.put("Return Type", method.getReturnType());
        }
        table.put("Exceptions", method.getExceptions());
        tableSize = table.size();
    }

    public PropertiesTableModel(MyClass classFile) {
        table = new LinkedHashMap<String, String>();
        table.put("Name", classFile.getName());
        table.put("All Files", classFile.getPath());
        table.put("File Size", classFile.getFileSize());
        table.put("Modification Time", classFile.getModificationTime());
        table.put("Class Name", classFile.getClassName());
        table.put("Modifiers", classFile.getAccess());
        table.put("Superclass", classFile.getSuperClass());

        tableSize = table.size();
    }

    @Override
    public int getRowCount() {
        return tableSize;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] keys = table.keySet().toArray();
        Object[] values = table.values().toArray();

        if (columnIndex == 0) {
            return keys[rowIndex];
        } else {
            return values[rowIndex];
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
