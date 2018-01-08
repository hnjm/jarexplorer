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
 *  Parser.java
 *
 * Created on 31 ottobre 2006, 10.10
 */

package jarexplorer.parser;

import de.schlichtherle.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

/**
 *
 * @author mmilon
 */
public class Parser {
    
    private String classPath;
    private String zipFile;
    private String fileName;
    private JavaClass jClass;
    
    /** Creates a new instance of Parser */
    public Parser(String zipFile, String fileName) throws IOException {
	
	this.zipFile = zipFile;
	this.fileName = fileName;
	
	ClassParser clsParser = new ClassParser(zipFile, fileName);
	jClass = clsParser.parse();
    }
    
    public MyClass getMyClass() {
	return new MyClass(zipFile + File.separator + fileName, fileName, jClass);
    }
    
    public MyField[] getFields() {
	Field[] fields = jClass.getFields();
	ArrayList<MyField> list = new ArrayList<MyField>();
	
	for (int i = 0; i < fields.length; i++) {
	    if(fields[i].getName().indexOf("$") == -1) {
		list.add(new MyField(fields[i]));
	    }
	}
	
	MyField[] tmp = new MyField[list.size()];
	return  list.toArray(tmp);
    }
    
    public MyMethod[] getMethods() {
	Method[] methods = jClass.getMethods();
	ArrayList<MyMethod> list = new ArrayList<MyMethod>();
	
	for (int i = 0; i < methods.length; i++) {
	    if(!methods[i].getName().equals("<init>") && !methods[i].getName().equals("<clinit>") && methods[i].getName().indexOf("$") == -1) {
		list.add(new MyMethod(jClass.getClassName() ,methods[i]));
	    }
	}
	
	MyMethod[] tmp = new MyMethod[list.size()];
	return list.toArray(tmp);
    }
    
    public MyMethod[] getConstructors() {
	Method[] methods = jClass.getMethods();
	ArrayList<MyMethod> list = new ArrayList<MyMethod>();
	
	for (int i = 0; i < methods.length; i++) {
	    if(methods[i].getName().equals("<init>") && methods[i].getName().indexOf("$") == -1) {
		list.add(new MyMethod(jClass.getClassName() ,methods[i]));
	    }
	}
	
	MyMethod[] tmp = new MyMethod[list.size()];
	return list.toArray(tmp);
    }
    
    public static void dump(String zipFile, String fileName) throws IOException {
	
	String str = zipFile + File.separator + fileName;
	File file = new File(zipFile + File.separator + fileName);
	
	if(file.isFile()) {
	    System.out.println("file: " + file.getAbsolutePath());
	    System.out.println("file size: " + file.length());
	    
	    SimpleDateFormat formater = new SimpleDateFormat("MMM dd, yyyy k:m:s");
	    System.out.println("file lastmodification: " + formater.format(new Date(file.lastModified())));
	}
	
	ClassParser clsParser = new ClassParser(zipFile, fileName);
	JavaClass jClass = clsParser.parse();
	
	String tmpClassName = jClass.getSourceFileName();
	String className = tmpClassName.substring(0, tmpClassName.length()-5);
	System.out.println("Name: " + className);
	
	System.out.println("className access: " + Utility.accessToString(jClass.getAccessFlags(), true));
	System.out.println("superClass: " + jClass.getSuperclassName());
	
	className = fileName.replace("/", ".");
	System.out.println("className file: " + className.substring(0, className.length()-6));
	
	/*
	Field fields[] = jClass.getFields();
	for (int i = 0; i < fields.length; i++) {
	    System.out.println(new MyField(fields[i]).dump());
	    System.out.println("+------------------------------------+");
	}
	 
	Method methods[] = jClass.getMethods();
	for (int i = 0; i < methods.length; i++) {
	    System.out.println(new MyMethod( jClass.getClassName(), methods[i]).dump());
	    System.out.println(methods[i].toString());
	    System.out.println("+------------------------------------+");
	}
	 */
    }
    
//    public static void main(String[] args) {
//	
//	try {
//	    String path = "C:\\JarExplorer.jar";
//	    String className = "jarexplorer/parser/MyMethod.class";
//	    Parser.dump(path, className);
//	} catch (IOException ex) {
//	    ex.printStackTrace();
//	}
//    }
    
}