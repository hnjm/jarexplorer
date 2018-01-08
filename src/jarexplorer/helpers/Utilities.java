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
 *  Utilities.java
 *
 * Created on 30 ottobre 2006, 11.54
 */

package jarexplorer.helpers;

import de.schlichtherle.io.File;
import jarexplorer.model.FileNode;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

/**
 *
 * @author mmilon
 */
public class Utilities {

    private Dimension screenSize;

    public Utilities() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        screenSize = kit.getScreenSize();
    }

    private Dimension getScreenSize() {
        return screenSize;
    }

    public int getFrameWidth() {
        return (getScreenSize().width / 2) - 50;
    }

    public int getFrameHeight() {
        return (getScreenSize().height / 2) + 100;
    }

    public int getScreenWidth() {
        return getScreenSize().width;
    }

    public int getScreenHeigth() {
        return getScreenSize().height;
    }

    public static boolean isWindows() {
        //System.out.println("os: " + System.getProperty("os.name"));
        return System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }

    public static void launchFile(final FileNode node, final String path) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                File file = new File(path);

                java.io.File tmp = new java.io.File(System.getProperty("java.io.tmpdir") + File.separator + node.toString());
                file.archiveCopyTo(tmp);
                tmp.deleteOnExit();

                try {
                    String cmd = "cmd /c start " + tmp.getAbsolutePath();
                    Runtime.getRuntime().exec(cmd);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
    }
}