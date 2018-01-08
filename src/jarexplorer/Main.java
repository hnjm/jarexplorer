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
 *  Main.java
 *
 * Created on 30 ottobre 2006, 11.53
 */

package jarexplorer;

import jarexplorer.gui.exceptions.JarExplorerException;
import jarexplorer.gui.tree.GuiManager;

/**
 *
 * @author mmilon
 */
public class Main {

    public static final String NAME = "JAREXPLORER";
    public static final String VERSION = "1.1.1";
    public static final int BUILD = 111;

    public static void main(final String[] args) {

        final String vendor = java.lang.System.getProperty("java.vendor");
        final String version = java.lang.System.getProperty("java.version");

        System.out.println("Vendor: " + vendor);
        System.out.println("Version: " + version);

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.setDefaultUncaughtExceptionHandler(new JarExplorerException());
                    GuiManager.getGuiManager(args);
                } catch (Exception ex) {
                    System.err.println("JarExplorer fatal error: Unabled to start application.");
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
