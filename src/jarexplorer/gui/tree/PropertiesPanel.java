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
 *  PropertiesPanel.java
 *
 * Created on 6 novembre 2006, 9.56
 */

package jarexplorer.gui.tree;

import jarexplorer.model.ConstructorNode;
import jarexplorer.model.FieldNode;
import jarexplorer.model.MethodNode;
import jarexplorer.model.MyTreeNode;
import jarexplorer.model.PropertiesTableModel;
import jarexplorer.parser.MyField;
import jarexplorer.parser.MyMethod;
import jarexplorer.helpers.Utilities;
import jarexplorer.model.ClassNode;
import jarexplorer.parser.MyClass;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;

/**
 *
 * @author mmilon
 */
public class PropertiesPanel extends JFrame {
	
	String title;
	String description;
	
	/** Creates a new instance of PropertiesPanel */
	public PropertiesPanel(MyTreeNode node) {
		//super(parent, "Properties", true);
		JLabel label = new JLabel("Properties");
		label.setBackground(java.awt.Color.lightGray);
		label.setText(" Properties");
		label.setOpaque(true);
		add(label, BorderLayout.NORTH);
		
		String type = node.getType();
		JTable table;
		
		if(type.equals("FieldNode")) {
			MyField myField = ((FieldNode)node).getField();
			table = new JTable(new PropertiesTableModel(myField));
			title = myField.getName();
			description = getDescription(myField);
		} else if(type.equals("ClassNode")) {
			MyClass myClass = ((ClassNode)node).getMyClass();
			table = new JTable(new PropertiesTableModel(myClass));
			title = myClass.getName() + ".class";
			description = getDescription(myClass);
		} else if(type.equals("MethodNode")) {
			MyMethod myMethod = ((MethodNode)node).getMethod();
			table = new JTable(new PropertiesTableModel(myMethod, false));
			title = myMethod.getName();
			description = getDescription(myMethod);
		} else {
			MyMethod myMethod = ((ConstructorNode)node).getMethod();
			table = new JTable(new PropertiesTableModel(myMethod, true));
			title = myMethod.getName();
			description = getDescription(myMethod);
		}
		
		table.setGridColor(java.awt.Color.lightGray);
		table.setIntercellSpacing(new java.awt.Dimension(5, 5));
		
		
		TableColumn col0 = table.getColumnModel().getColumn(0);
		TableColumn col1 = table.getColumnModel().getColumn(1);
		col0.setCellRenderer(new PropertiesTableCellRender());
		col1.setCellRenderer(new PropertiesTableCellRender());
		//add(table.getTableHeader(), BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BorderLayout());
		JLabel desc = new JLabel(description);
		desc.setOpaque(true);
		Border emptyBoder = BorderFactory.createEmptyBorder(2,2,0,0);
		desc.setBorder(emptyBoder);
		parentPanel.add(desc, BorderLayout.NORTH);
		
		JPanel childPanel = new JPanel();
		childPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			@Override
public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		childPanel.add(close);
		parentPanel.add(childPanel, BorderLayout.SOUTH);
		add(parentPanel, BorderLayout.SOUTH);
		
		Utilities util = new Utilities();
		setTitle(title + " - Properties");
		URL imgURL = ClassLoader.getSystemResource("jarexplorer/icons/viewmag.png");
		setIconImage(new ImageIcon(imgURL).getImage());
		setSize(util.getFrameWidth()-50, util.getFrameHeight()-50);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private String getDescription(MyClass myClass) {
		return "<html><b>" + myClass.getName() + ".class</b></html>";
	}
	
	private String getDescription(MyField field) {
		String tmp = "<html><b>" + field.getName() + "</b><br>" +
		    field.getModifiers() + " " +
		    field.getSignature() + " " +
		    field.getName() + "</html>";
		
		return tmp;
	}
	
	private String getDescription(MyMethod method) {
		String tooltip = method.getModifiers() + " " +
		    method.getReturnType() + " " +
		    method.getName() + " " +
		    method.getSignature();
		if(method.getExceptions() != null && method.getExceptions().length() > 0) {
			tooltip += " throws " + method.getExceptions();
		}
		
		String tmp = "<html><b>" + method.getName() + "</b><br>" + tooltip + "</html>";
		
		return tmp;
	}
	
}
