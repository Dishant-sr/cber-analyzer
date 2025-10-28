package com.cber.analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final Properties props = new Properties();

    public Config(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Config file not found. Using default settings.");
        }
    }

    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
