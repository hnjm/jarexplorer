/*
 * MyField.java
 *
 * Created on 20 ottobre 2006, 11.10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.parser;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.Utility;

/**
 *
 * @author mmilon
 */
public class MyField {
    
    private String name = "";    
    private String modifiers = "";
    private String signature = "";
    private String value = "";
    
    /**
     * Creates a new instance of MyField
     */
    public MyField(Field field) {
        modifiers = Utility.accessToString(field.getAccessFlags());
        signature = Utility.signatureToString(field.getSignature());
        name = field.getName();
        setValue(field);
    }
    
    public String getModifiers() {
        return modifiers;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    
    private void setValue(Field field) {
        StringBuffer buffer = new StringBuffer();
        ConstantValue cv  = field.getConstantValue();
        Attribute[] attributes = field.getAttributes();
        int attributes_count = field.getAttributes().length;
        
        if(cv != null) {
            buffer.append(cv);
            //buffer.append(" = " + cv);
            
            for(int i=0; i < attributes_count; i++) {
                Attribute a = attributes[i];
                
                if(!(a instanceof ConstantValue))
                    buffer.append(" [" + a.toString() + "]");
            }
        }
        
        value = buffer.toString().replace("\"", "");
    }
    
    public String dump() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("Name: " + getName() + "\n");
        buffer.append("Modifiers: " + getModifiers() + "\n");
        buffer.append("Signature: " + getSignature() + "\n");
        buffer.append("Value: " + getValue() + "\n");
        
        return buffer.toString();
    }
  
}
