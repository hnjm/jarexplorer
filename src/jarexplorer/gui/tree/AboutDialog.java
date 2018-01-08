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
 * AboutDialog.java
 *
 * Created on November 3, 2006, 9:17 PM
 */

package jarexplorer.gui.tree;

import jarexplorer.Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author Marco Milon
 *
 */
public class AboutDialog extends JDialog {

    public AboutDialog(JFrame parent) {
        super(parent, "Jar Explorer", true);

        JTabbedPane pane = buildTabs();
        pane.add("About", buildFirstTab());
        pane.add("License Agreement", buildSecondTab());

        add(pane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton ok = new JButton("Close");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });

        panel.add(ok);
        add(panel, BorderLayout.SOUTH);

        //URL imgURL = ClassLoader.getSystemResource("jarexplorer/icons/root_trans.gif");
        //setIcon(new ImageIcon(imgURL).getImage());
        setSize(450, 300);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JTabbedPane buildTabs() {
        // Specify on which edge the tabs should appear
        int location = JTabbedPane.TOP; // or BOTTOM, LEFT, RIGHT
        // Create the tabbed pane
        JTabbedPane pane = new JTabbedPane();

        return pane;
    }

    private JLabel buildFirstTab() {
        JLabel label = new JLabel("<html><h2><i>Jar Explorer</i></h2>" + "<h4>Powered by Truezip and Bcel</h4>" + "Java decompiler engine provided by Jode<br>" + "Syntax highlighting library <b>sdoc</b>" + "<hr>" + "Marco Milon<br>" + "marcov8@gmail.com<br>" + Main.VERSION + "</html>", SwingConstants.CENTER);
        return label;
    }

    private JTextArea buildSecondTab() {
        JTextArea textarea = new JTextArea("This program is free software; you can redistribute it and/or\n" + "modify it under the terms of the GNU General Public License\n" + "as published by the Free Software Foundation; either version 2\n" + "of the License, or (at your option) any later version.\n" + "\n" + "This program is distributed in the hope that it will be useful,\n" + "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + "GNU General Public License for more details.\n" + "\n" + "You should have received a copy of the GNU General Public License\n" + "along with this program; if not, write to the Free Software\n" + "Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.");

        textarea.setEditable(false);
        textarea.setFont(new java.awt.Font("Dialog", 1, 10));
        return textarea;
    }
}