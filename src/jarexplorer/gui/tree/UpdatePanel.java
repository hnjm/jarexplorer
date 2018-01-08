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
 * UpdatePanel.java
 *
 * Created on July 2, 2007, 3:18 PM
 *
 */

package jarexplorer.gui.tree;

import jarexplorer.helpers.Preference;
import jarexplorer.helpers.Version;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Marco
 */
public class UpdatePanel extends JPanel {

    private String versionUptoDate = "No newer version found.";
    private String noServiceAvailable = "Cannot connect to server.";

    /** Creates a new instance of UpdatePanel */
    public UpdatePanel() {
        try {
            Version v = Version.getVersion();
            if (v.isUptodate()) {
                initInfoDatePanel(versionUptoDate);
            } else {
                String msg = "<html>" + "<div style='text-align:center; margin: 10px'>" + "New version " + v.getVersionNumber() + " available.<br><br>" + "<a href='http://jarexplorer.sourceforge.net'>http://jarexplorer.sourceforge.net</a>" + "</div>" + "</html>";
                initNewVersionAvailablePanel(msg);
            }
        } catch (Exception ex) {
            initInfoDatePanel(noServiceAvailable);           
        } finally {
            GuiManager.getUi().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

//    public UpdatePanel(boolean checkNeeded) {
//        if (Version.isUptodate() == false) {
//            initNewVersionAvailablePanel(versionAvailable);
//        } else {
//            initUptoDatePanel(versionUptoDate);
//        }
//        GuiManager.getUi().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//    }
    private void initInfoDatePanel(String msg) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel(msg);
        add(label, BorderLayout.CENTER);
    }

    private void initNewVersionAvailablePanel(String msg) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel(msg);
        label.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        label.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        add(label, BorderLayout.CENTER);

        Action action = new AbstractAction("Do not show me this message again.") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                JCheckBox cb = (JCheckBox) evt.getSource();
                Preference.getPreference().setShowMeUpdateMessage(!cb.isSelected());
            }
        };

        JCheckBox jCheckBox = new JCheckBox(action);
        jCheckBox.setSelected(!Preference.getPreference().isShowMeUpdateMessage());

        add(jCheckBox, BorderLayout.SOUTH);
    }
}