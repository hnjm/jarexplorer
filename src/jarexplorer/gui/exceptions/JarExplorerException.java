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
 * JarExplorerException.java
 *
 * Created on May 24, 2007, 1:09 PM
 *
 */

package jarexplorer.gui.exceptions;

import jarexplorer.gui.bar.Clickable;
import jarexplorer.gui.bar.StatusBar;
import jarexplorer.gui.tree.GuiManager;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Marco
 */
public class JarExplorerException implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, final Throwable e) {
        String iconPath = "jarexplorer/icons/warning.gif";

        Clickable c = new Clickable() {
            @Override
            public void onMouseClicked(MouseEvent evt) {
                GuiManager.getGuiManager().reset();

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.close();
                String msg = sw.toString();

                int rows = 20;
                int columns = 60;
                JTextArea textArea = new JTextArea(msg, rows, columns);
                textArea.setEditable(false);
                textArea.setFont(new java.awt.Font("Dialog", 1, 10));
                textArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));

                JScrollPane scrollPane = new JScrollPane(textArea);

                JOptionPane.showMessageDialog(GuiManager.getUi(), scrollPane, e.toString(), JOptionPane.ERROR_MESSAGE);
            }
        };

        StatusBar statusBar = new StatusBar(c, iconPath, "A " + e.getMessage() + " exception occurred");
        statusBar.addToFrame(GuiManager.getUi());
        GuiManager.getGuiManager().loadFailed();
    }
}