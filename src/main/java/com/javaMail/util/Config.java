package com.javaMail.util;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Config instance;

    @Getter
    private final String password;

    private Config() throws IOException {

        Properties properties = new Properties();

        // Use a try-with-resources to automatically close the FileInputStream
        try (FileInputStream fileInputStream = new FileInputStream("password.properties")) {
            properties.load(fileInputStream);
        }
        password = properties.getProperty("password");
    }

    public static synchronized Config getInstance() throws IOException {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}
