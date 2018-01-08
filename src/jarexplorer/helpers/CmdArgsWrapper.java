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
 *  GuiManager.java
 *
 * Created on May 10, 2007, 10:44 AM
 */

package jarexplorer.helpers;

import java.io.File;

public class CmdArgsWrapper {
        
        public static boolean isJar(String[] args) {
                if(args.length == 0) {
                        return false;
                } else{
                        String jarFilePath = args[0];
                        File jarFile = new File(jarFilePath);
                        boolean isJar = jarFile.isFile() && jarFile.getName().toLowerCase().endsWith(".jar");
                        if(isJar == false)
                                System.err.println("found invalid argument: " + args[0]);
                        
                        return isJar;
                }
        }
        
}
