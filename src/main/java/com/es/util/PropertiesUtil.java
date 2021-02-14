package com.es.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static String getpropetyByfile(String propertyNamem, String fila) {
        Properties props = new Properties();
        String resource = "";
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fila);
        try {
            props.load(in);
            resource = props.getProperty(propertyNamem);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }
}
