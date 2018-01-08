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
 * Created on 2 novembre 2006, 9.59
 */

package jarexplorer.model;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mmilon
 */
public class Utilities {
    
    private static String jarPath;
    private static final String patternStr = "\\w+\\.class";
    private static final Pattern pattern = Pattern.compile(patternStr);
    
    public static String getJarPath() {
        return jarPath;
    }

    public static void setJarPath(String aJarPath) {
        jarPath = aJarPath;
    }
    
    /*
     * @todo: fix potential IndexOutOfBoundsException
     */
    public static String formatClassPathDir(String className) {
        if(className.indexOf(".jar") != -1) {
            String tmp = className.substring(className.indexOf(".jar")+5);
            tmp = tmp.replace(File.separator, "/");
            return tmp;
        } else {
            return className;
        }
    }
    
    /*
     * @todo: fix potential IndexOutOfBoundsException
     */
    public static String formatImportPathDir(String className) {
        if(className.indexOf(".jar") != -1) {
            String tmp = className.substring(className.indexOf(".jar")+5, className.indexOf(".class"));
            tmp = tmp.replace(File.separator, ".");
            return tmp;
        } else {
            return className;
        }
    }
    
    public static String formatClassName(String className) {
        Matcher matcher = pattern.matcher(className);
        boolean matchFound = matcher.find();

        if(matchFound) {
            className = matcher.group();
            return className;
        } else {
            return className;
        }
    }
   
}