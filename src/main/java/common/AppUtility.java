/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class AppUtility {
//    private static final Logger LOG = Logger.getLogger(AppUtility.class.getName());
    private String stCurDir;
    private String stPropFile;
    private Properties properties = new Properties();


    public AppUtility() {
        if (this.stCurDir == null) {
            this.stCurDir = System.getProperty("catalina.home");
        }
        Locale.setDefault(new Locale("uk", "UA"));
        Locale curLocale = Locale.getDefault();

        String propfile = getPropFile();
        try {
            properties.load(new FileInputStream(propfile));
        } catch (IOException e) {
            System.out.println("Can't read property file" + propfile);
        }
    }

    public void setHomeDir(String newVal) {
        this.stCurDir = newVal;
    }

    public String getHomeDir() {
        return this.stCurDir;
    }

    private String getPropFile() {
        String separator = System.getProperty("file.separator");
        this.stCurDir = this.stCurDir.replace(separator.charAt(0), '/');
        this.stPropFile = this.stCurDir + "/addrbook.ini";
        return stPropFile;
    }

    public void setPropFile(String newVal) {
        this.stPropFile = newVal;
    }

    public String getProp(String PropName) {
        return properties.getProperty(PropName);
    }
    
}
