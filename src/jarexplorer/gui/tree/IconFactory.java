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
 *  IconFactory.java
 *
 * Created on 3 novembre 2006, 11.05
 */

package jarexplorer.gui.tree;

import jarexplorer.model.ConstructorNode;
import jarexplorer.model.FieldNode;
import jarexplorer.model.MethodNode;
import jarexplorer.model.MyTreeNode;
import jarexplorer.parser.MyField;
import jarexplorer.parser.MyMethod;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author mmilon
 */
public class IconFactory {
    
    private static final HashMap<String, String> defaultIcons = new HashMap<String, String>();
    private static final HashMap<String, String> classIcons = new HashMap<String, String>();
    
    private static IconFactory instance = null;
    
    private IconFactory() {
        defaultIcons.put("RootNode", "jarexplorer/icons/root.gif");
        defaultIcons.put("DirectoryNode", "jarexplorer/icons/directory.gif");
        defaultIcons.put("ClassNode", "jarexplorer/icons/class.gif");
        defaultIcons.put("ClassNodeLabel", "jarexplorer/icons/classLabel.gif");
        defaultIcons.put("FieldNodeLabel", "jarexplorer/icons/fields.gif");
        defaultIcons.put("MethodNodeLabel", "jarexplorer/icons/methods.gif");
        defaultIcons.put("ConstructorNodeLabel", "jarexplorer/icons/constructors.gif");
        
        defaultIcons.put("DefaultNode", "jarexplorer/icons/default.gif");
        defaultIcons.put("FileJava", "jarexplorer/icons/filejava.gif");
        defaultIcons.put("Error", "jarexplorer/icons/error.gif");
        
        classIcons.put("MethodNode", "jarexplorer/icons/methods/defaultmethod.gif");
        classIcons.put("MethodNodeprivate", "jarexplorer/icons/methods/privatemethod.gif");
        classIcons.put("MethodNodepublic", "jarexplorer/icons/methods/publicmethod.gif");
        classIcons.put("MethodNodeprotected", "jarexplorer/icons/methods/protectedmethod.gif");
        classIcons.put("MethodNodestatic", "jarexplorer/icons/methods/defaultstaticmethod.gif");
        classIcons.put("MethodNodepublicstatic", "jarexplorer/icons/methods/publicstaticmethod.gif");
        classIcons.put("MethodNodeprivatestatic", "jarexplorer/icons/methods/privatestaticmethod.gif");
        classIcons.put("MethodNodeprotectecstatic", "jarexplorer/icons/methods/protectedstatic.gif");
        
        classIcons.put("FieldNode", "jarexplorer/icons/fields/defaultfield.gif");
        classIcons.put("FieldNodeprivate", "jarexplorer/icons/fields/privatefield.gif");
        classIcons.put("FieldNodepublic", "jarexplorer/icons/fields/publicfield.gif");
        classIcons.put("FieldNodeprotected", "jarexplorer/icons/fields/protectedfield.gif");
        classIcons.put("FieldNodestatic", "jarexplorer/icons/fields/defaultstaticfield.gif");
        classIcons.put("FieldNodepublicstatic", "jarexplorer/icons/fields/publicstaticfield.gif");
        classIcons.put("FieldNodeprivatestatic", "jarexplorer/icons/fields/privatestaticfield.gif");
        classIcons.put("FieldNodeprotectedstatic", "jarexplorer/icons/fields/protectedstaticfield.gif");
        
        classIcons.put("ConstructorNode", "jarexplorer/icons/constructor/defaultconstructor.gif");
        classIcons.put("ConstructorNodeprivate", "jarexplorer/icons/constructor/privateconstructor.gif");
        classIcons.put("ConstructorNodepublic", "jarexplorer/icons/constructor/publicconstructor.gif");
        classIcons.put("ConstructorNodeprotected", "jarexplorer/icons/constructor/protectedconstructor.gif");
        classIcons.put("ConstructorNodestatic", "jarexplorer/icons/constructor/staticconstructor.gif");
        
        classIcons.put("DefaultNode", "jarexplorer/icons/default.gif");        
    }
    
    final public static IconFactory getInstance() {
        if(instance == null) {
            instance = new IconFactory();
        }
        return instance;
    }
    
    public HashMap getMap() {
        return defaultIcons;
    }
    
    public ImageIcon getIcon(String type) {
        URL imgURL = ClassLoader.getSystemResource(defaultIcons.get(type));
        return new ImageIcon(imgURL);
    }
    
    public ImageIcon getIcon(MyTreeNode node, boolean leaf) {
        String type = node.getType();
        String path;
        URL imgURL;
        
        if(defaultIcons.containsKey(type)) {
            imgURL = ClassLoader.getSystemResource(defaultIcons.get(type));
        } else if(type.equals("MethodNode")) {
            MyMethod method = ((MethodNode)node).getMethod();
            String key = getKey(method);
            imgURL = ClassLoader.getSystemResource(classIcons.get(key));
        } else if(type.equals("FieldNode")) {
            MyField field = ((FieldNode)node).getField();
            String key = getKey(field);
            imgURL = ClassLoader.getSystemResource(classIcons.get(key));
        } else if(type.equals("ConstructorNode")) {
            MyMethod constructor = ((ConstructorNode)node).getMethod();
            String key = getConstructorKey(constructor);
            imgURL = ClassLoader.getSystemResource(classIcons.get(key));
        } else if(leaf) {
            imgURL = ClassLoader.getSystemResource(defaultIcons.get("DefaultNode"));
        } else {
            imgURL = ClassLoader.getSystemResource(defaultIcons.get("DirectoryNode"));
        }
        
        return new ImageIcon(imgURL);
    }
    
    private String getConstructorKey(MyMethod method) {
        String key = "ConstructorNode" + method.getModifiers();
        key = cleanUpKey(key);
        if(classIcons.containsKey(key)) {
            return key;
        } else {
            return "DefaultNode";
        }
    }
    
    private String getKey(MyField field) {
        String key = "FieldNode" + field.getModifiers();
        key = cleanUpKey(key);
        if(classIcons.containsKey(key)) {
            return key;
        } else {
            return "DefaultNode";
        }
    }
    
    private String getKey(MyMethod method) {
        String key = "MethodNode" + method.getModifiers();
        key = cleanUpKey(key);
        if(classIcons.containsKey(key)) {
            return key;
        } else {
            return "DefaultNode";
        }
    }
    
    private String cleanUpKey(String key) {
        return key.replace(" ", "").replace("final", "").replace("abstract", "").replace("synchronized", "").replace("transient", "").replace("native", "");
    }
}