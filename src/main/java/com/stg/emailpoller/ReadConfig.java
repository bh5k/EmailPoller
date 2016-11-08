package com.stg.emailpoller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read Configuration file.
 *
 * Created by dqromney on 9/26/15.
 */
public class ReadConfig {

    private InputStream inputStream;

    private String propertyFile;

    public ReadConfig(String pPropertyFile) {
        // Default
        String configProps = "config.properties";
        if (pPropertyFile != null) {
            this.propertyFile = pPropertyFile;
        } else {
            pPropertyFile = configProps;
        }
    }

    public Properties getPropValues() throws IOException {
        Properties prop = null;
        try {
            prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(this.propertyFile);
            if(inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + this.propertyFile + "' not found in classpath.");
            }
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return prop;
    }
}
