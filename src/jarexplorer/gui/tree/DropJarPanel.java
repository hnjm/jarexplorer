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
 *  DropJarPanel.java
 *
 * Created on 30 ottobre 2006, 13.03
 */

package jarexplorer.gui.tree;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mmilon
 */
public class DropJarPanel extends JPanel {
    
    /**
     * Creates a new instance of DropJarPanel
     */
    
    JLabel label;
            
    public DropJarPanel() {
        initComponents();
    }
    
    private void initComponents() {
        label = new JLabel("Drop Jar Here.");
        
        label.setFont(new java.awt.Font("Dialog", 1, 10));
        label.setForeground(new java.awt.Color(51, 153, 255));
        label.setAlignmentX(0.5f);
        
        //Container contentPane = getContentPane();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add( Box.createVerticalGlue() );
        add( label );
        add( Box.createVerticalGlue() );
    }
    
    public void addToFrame(JFrame frame) {
        frame.add(this, BorderLayout.CENTER);
        repaint();
        revalidate();
    }
    
    public void removeFromFrame(JFrame frame) {
        frame.remove(this);
        repaint();
        revalidate();
    }
    
    public void loading(String path) {
        //label.setText("Loading " + path);
    }
    
    public void loaded() {
        //label.setText("Drop Jar Here");
        setLabel("Drop Jar Here.");
    }
    
    public void setLabel(String str) {
        label.setText(str);
    }
    
}
