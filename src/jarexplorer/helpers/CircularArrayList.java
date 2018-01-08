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
 *  DropJarPanel.java
 *
 * Created on 30 ottobre 2006, 13.03
 */

package jarexplorer.helpers;

import java.util.ArrayList;

public class CircularArrayList {

    private ArrayList<String> list;
    private int index = 0;

    public CircularArrayList() {
        this.list = new ArrayList<String>();
    }

    public boolean put(String str) {
        list.add(str);
        return true;
    }

    public String get() {
        if(list.size() == index) {
            index = 0;
        }
        
        String str = list.get(index);
        index++;
        
        return str;
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
} 
