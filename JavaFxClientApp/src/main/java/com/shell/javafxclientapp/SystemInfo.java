package com.shell.javafxclientapp;

/**
 *
 * @author fratiaxd
 */

/**
 * @brief SystemInfo class gets the information about the system setup
 */

public class SystemInfo {
    
    /**
    * Get java version
    * @return current java version
    */

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
    * Get javaFx version
    * @return current javaFx version
    */
    
    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}