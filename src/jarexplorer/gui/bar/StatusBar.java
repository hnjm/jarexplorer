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
 * StatusBar.java
 *
 * Created on June 29, 2007, 2:39 PM
 *
 */

package jarexplorer.gui.bar;

import jarexplorer.gui.tree.GuiManager;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author Marco
 */
public class StatusBar extends JPanel {

    private String iconPath;
    private Clickable clickable;
    private String tooltip;

    public StatusBar(Clickable c, String s, String t) {
        this.iconPath = s;
        this.clickable = c;
        this.tooltip = t;
        initComponents();
    }

    private void initComponents() {

        ImageIcon exceptionIcon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
        JLabel exception = new JLabel(exceptionIcon);
        exception.setToolTipText(tooltip);
        exception.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickable.onMouseClicked(evt);
                removeFromFrame(GuiManager.getUi());
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                onMouseEntered(evt);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                onMouseExited(evt);
            }
        });

        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(exception);
    }

    private void onMouseEntered(java.awt.event.MouseEvent evt) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void onMouseExited(java.awt.event.MouseEvent evt) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
