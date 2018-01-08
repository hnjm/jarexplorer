/*
 * Preference.java
 *
 * Created on March 15, 2007, 11:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jarexplorer.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Marco
 */
public class Preference {
    
    String file;
    String directory;
    
    Properties properties;
    
    private static Preference preference = null;
    
    /** Creates a new instance of Preference */
    private Preference() {
        directory = System.getProperty("user.home") + File.separator + "." + "jarExplorer";
        file = System.getProperty("user.home") + File.separator + "." + "jarExplorer" + File.separator + "preference.properties";
        properties = new Properties();
        
        try {
            File preferenceFile = new File(file);
            properties.load(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            createPreferenceFile(directory, file);
        } catch (IOException ex) {
            System.err.println("Error creating preference file: " + ex.getMessage());
        }
    }
    
    public static Preference getPreference() {
        if (preference == null) {
            preference = new Preference();
        }
        return preference;
    }
    
    private void createPreferenceFile(String dir, String file) {
        File tmpFile = new File(dir);
        tmpFile.mkdir();
        
        File preferenceFile = new File(file);
        try {
            preferenceFile.createNewFile();
        } catch (IOException ex) {
            System.err.println("Error creating preference file: " + ex.getMessage());
        }
    }
    
    public void setAlwaysOnTop(boolean b) {
        properties.setProperty("alwaysOnTop", new Boolean(b).toString());
        try {
            properties.store(new FileOutputStream(file), "JarExplorer preference");
        } catch (FileNotFoundException ex) {
            System.err.println("Error creating preference file: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error creating preference file: " + ex.getMessage());
        }
    }
    
    public boolean isAlwaysOnTop() {
        return new Boolean(properties.getProperty("alwaysOnTop"));
    }
    
    public void setShowMeUpdateMessage(boolean b) {
        properties.setProperty("showMeUpdateMessage", new Boolean(b).toString());
        try {
            properties.store(new FileOutputStream(file), "JarExplorer preference");
        } catch (FileNotFoundException ex) {
            System.err.println("Error creating preference file: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error creating preference file: " + ex.getMessage());
        }
    }
    
    public boolean isShowMeUpdateMessage() {
            return new Boolean(properties.getProperty("showMeUpdateMessage"));
    }
    
}