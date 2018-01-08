/*
 * MyClass.java
 *
 * Created on March 19, 2007, 4:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.parser;

import de.schlichtherle.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Utility;

/**
 *
 * @author Marco
 */
public class MyClass {
	
	private String path;
	private String fileSize;
	private String modificationTime;
	private String name;
	private String access;
	private String superClass;
	private String className;
	
	/** Creates a new instance of MyClass */
	public MyClass(String fileName, String className, JavaClass jClass) {
		
		File file = new File(fileName);
		path = file.getAbsolutePath();
		fileSize = new Long(file.length()).toString();
		SimpleDateFormat formater = new SimpleDateFormat("MMM dd, yyyy k:m:s");
		modificationTime = formater.format(new Date(file.lastModified()));
		
		String tmpClassName = jClass.getClassName();
		int classNameIndex = tmpClassName.lastIndexOf(".") + 1;
		name = tmpClassName.substring(classNameIndex, tmpClassName.length());
		
		superClass = jClass.getSuperclassName();
		
		String tmpName = className.replace("/", ".");
		this.className = tmpName.substring(0, tmpName.length()-6);
		
		access = Utility.accessToString(jClass.getAccessFlags(), true);
	}
	
	public String getPath() {
		return path;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	
	public String getModificationTime() {
		return modificationTime;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAccess() {
		return access;
	}
	
	public String getSuperClass() {
		return superClass;
	}
	
	public String getClassName() {
		return className;
	}
	
}
