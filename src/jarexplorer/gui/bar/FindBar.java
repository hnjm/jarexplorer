/*
 * FindBar.java
 * 
 * Created on Nov 30, 2007, 11:40:54 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jarexplorer.gui.bar;

import jarexplorer.gui.tree.GuiManager;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author Marco
 */
public class FindBar extends JPanel {
    
    String iconPath = "jarexplorer/icons/close.gif";
    
    public FindBar() {
        initComponents();
    }
    
    private void initComponents() {
        this.setBackground(Color.lightGray);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        ImageIcon closeIcon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
        JLabel icon = new JLabel(closeIcon);
        icon.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeFromFrame(GuiManager.getUi());
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            }
        });
        
        add(icon);
        
        JLabel label = new JLabel("Find: ");
        add(label);
        
        JTextField textField = new JTextField(20);
        add(textField);
    }
    
    public void addToFrame(JFrame frame) {
        //frame.toFront();
        frame.add(this, BorderLayout.SOUTH);
        repaint();
        revalidate();
    }

    public void removeFromFrame(JFrame frame) {
        this.setVisible(false);
        frame.remove(this);
        repaint();
        revalidate();
    }
}
