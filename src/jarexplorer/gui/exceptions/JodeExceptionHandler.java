/*
 * UncaughtExceptionHandler.java
 *
 * Created on March 7, 2007, 4:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.gui.exceptions;

import jarexplorer.gui.bar.Clickable;
import jarexplorer.gui.bar.StatusBar;
import jarexplorer.gui.decompiler.*;
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
public class JodeExceptionHandler implements UncaughtExceptionHandler {

    SingletonCodePanel codePanel;
    String label;

    /** Creates a new instance of UncaughtExceptionHandler **/
    public JodeExceptionHandler(SingletonCodePanel codePanel, String label) {
        this.codePanel = codePanel;
        this.label = label;
    }

    @Override
    public void uncaughtException(Thread t, final Throwable e) {
        codePanel.addLogTab("Error ", label);
        codePanel.disabledProgressPanel();

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
