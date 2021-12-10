package util;

import java.io.*;
import java.math.*;
import java.util.*;
 
import org.apache.commons.logging.*;
import org.openxava.util.*;

public class ProgrammaBiennalePreferences {
	private final static String FILE_PROPERTIES="application.properties";
    private static Log log = LogFactory.getLog(ProgrammaBiennalePreferences.class);
    private static Properties properties; // We store the properties here
 
    private static Properties getProperties() {
        if (properties == null) { // We use lazy initialization
            PropertiesReader reader = // PropertiesReader is a utility class from OpenXava
                new PropertiesReader( ProgrammaBiennalePreferences.class, FILE_PROPERTIES);
            try {
                properties = reader.get();
            }
            catch (IOException ex) {
                log.error( XavaResources.getString( // To read a i18n message
                    "properties_file_error", FILE_PROPERTIES), ex);
                properties = new Properties();
            }
        }
        return properties;
    }
 
    public static BigDecimal getDefaultGdlPercentage() { 
        return new BigDecimal(getProperties().getProperty("defaultGdlPercentage"));
    }
    
    public static String getDefaultUsername() {
        return new String(getProperties().getProperty("username"));
    }
    
    public static String getDefaultPassword() { 
        return new String(getProperties().getProperty("password"));
    }
    
    public static String getDefaultClientKey() { 
        return new String(getProperties().getProperty("clientKey"));
    }
    
    public static String getDefaultClientId() { 
        return new String(getProperties().getProperty("clientId"));
    }
    
    public static String getDefaultUsernameProduzione() {
        return new String(getProperties().getProperty("usernameProduzione"));
    }
    
    public static String getDefaultPasswordProduzione() { 
        return new String(getProperties().getProperty("passwordProduzione"));
    }
    
    public static String getDefaultClientKeyProduzione() { 
        return new String(getProperties().getProperty("clientKeyProduzione"));
    }
    
    public static String getDefaultClientIdProduzione() { 
        return new String(getProperties().getProperty("clientIdProduzione"));
    }
    
    public static String getDefaultCfSA() {
    	return new String(getProperties().getProperty("codiceFiscaleSA"));
    }
    
    public static String getDefaultDescrizionePubblicazione() {
    	return new String(getProperties().getProperty("descrizionePubblicazione"));
    }
    
    public static String getDefaultDescrizionePubblicazioneAggiornamento() {
    	return new String(getProperties().getProperty("descrizionePubblicazioneAggiornamento"));
    }
}
