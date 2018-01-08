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
 *  MainUI.java
 *
 * Created on 30 ottobre 2006, 11.54
 */

package jarexplorer.gui.tree;

import jarexplorer.gui.bar.FindBar;
import jarexplorer.helpers.Utilities;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author mmilon
 */
public class MainUI extends JFrame {

    public MainUI() throws Exception {
        Utilities util = new Utilities();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            //Swing look and fell
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }

        setTitle("Jar Explorer");
        URL imgURL = ClassLoader.getSystemResource("jarexplorer/icons/viewmag.png");
        setIconImage(new ImageIcon(imgURL).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(util.getFrameWidth(), util.getFrameHeight());
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (e.isControlDown() && keyCode == KeyEvent.VK_F) {
                    FindBar findBar = new FindBar();
                    findBar.addToFrame(GuiManager.getUi());
                }
            }
            
            public void keyReleased(KeyEvent e) {
            }
        });
    }
}
