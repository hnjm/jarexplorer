/*
 * PopupMenu.java
 *
 * Created on March 8, 2007, 2:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.gui.decompiler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

/**
 *
 * @author Marco
 */
class TextAreaPopupMenu extends MouseAdapter {

    private JPopupMenu menu;

    TextAreaPopupMenu(final JTextArea textarea) {
        menu = new JPopupMenu();
        JMenuItem clear = new JMenuItem("Clear");

        menu.add(clear);
        clear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                textarea.setText("");
            }
        });
    }

//    public void mousePressed(java.awt.event.MouseEvent e) {
//            displayMenu(e);
//    }
    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        //System.out.println("showMenu");
        displayMenu(e);
    }

    private void displayMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
