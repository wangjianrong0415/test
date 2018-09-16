package util;

import exception.JaceRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Iiro Hietala on 17.5.2014.
 */
public class PropertiesUtil {

    /**
     * Get a property and convert it into an integer.
     * @param key  Key to look up the property for
     * @return Returns the converted property or null if property is an empty string "" or the property is not found.
     * @throws NumberFormatException If integer conversion fails a NumberFormatException is thrown
     */
    public static Integer getIntProperty(String key) {
        String property = getProperty(key);
        if (!StringUtils.isEmpty(property)) {
            return Integer.parseInt(property);
        }
        return null;
    }

    /**
     * Get a property as a string specified by key
     * @param key Key to look up the property for
     * @return Returns the given property or null if propery is not found.
     */
    public static String getProperty(String key) {
        return (String) readProperty(key);
    }

    private static Object readProperty(String key) {
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("test.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            is.close();
        } catch (IOException e) {
            throw new JaceRuntimeException("Couldn't read properties file.", e);
        }
        return p.getProperty(key);
    }
}
