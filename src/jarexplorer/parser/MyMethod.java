/*
 * MyMethod.java
 *
 * Created on 20 ottobre 2006, 14.00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.parser;

import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

/**
 *
 * @author mmilon
 */
public class MyMethod {
    
    private String name = "";
    private String modifiers = "";
    private String returnType = "";
    private String signature = "";
    private String exceptions = "";
    private boolean constructor = false;
    
    /**
     * Creates a new instance of MyMethod
     */
    public MyMethod(String className, Method method) {
        name = method.getName();
        if(name.equals("<init>")) {
            if(className.indexOf(".") != -1) {
                name = className.substring(className.lastIndexOf(".") + 1);
            } else {
                name = className;
            }
            constructor = true;
        }
        
        modifiers = Utility.accessToString(method.getAccessFlags());
        returnType = method.getReturnType().toString();
        setSignature(method);
        setExceptions(method);
    }
    
    public String getName() {
        return name;
    }
    
    public String getModifiers() {
        return modifiers;
    }
    
    public String getReturnType() {
        return returnType;
    }
    
    public String getSignature() {
        return signature;
    }
    
    private void setSignature(Method method) {
        String tmp = method.toString();
        signature = tmp.substring(tmp.indexOf('('), tmp.lastIndexOf(')') + 1);
    }
    
    public String getExceptions() {
        return exceptions;
    }
    
    public boolean isConstructor() {
        return constructor;
    }
    
    private void setExceptions(Method method) {
        ExceptionTable e = method.getExceptionTable();
        if (e != null) {
            exceptions = e.toString();
        }
    }
    
    public String dump() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("Name: " + getName() + "\n");
        buffer.append("Modifiers: " + getModifiers() + "\n");
        buffer.append("Signature: " + getSignature() + "\n");
        buffer.append("Exceptions: " + getExceptions() + "\n");
        
        return buffer.toString();
    }
    
}