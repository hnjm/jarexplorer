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
 *  Register.java
 *
 * Created on 9 novembre 2006, 15.06
 */

package jarexplorer.helpers;

import java.io.File;

/**
 *
 * @author mmilon
 */
public class Register {
    
    private static File currentDirectory = null;
    private static boolean alwaysOnTop = false;

    public static File getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(File aCurrentDirectory) {
        currentDirectory = aCurrentDirectory;
    }

//    public static boolean isAlwaysOnTop() {
//        return alwaysOnTop;
//    }
//
//    public static void setAlwaysOnTop(boolean aAlwaysOnTop) {
//        alwaysOnTop = aAlwaysOnTop;
//    }
    
}
