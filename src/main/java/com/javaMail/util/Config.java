package com.javaMail.util;

import com.sun.javafx.scene.control.Properties;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;

public class Config {
    private static Config instance;

    @Getter
    private final String password;

    private Config() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("password.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            password = properties.getProperty("password");
        } catch (IOException e) {
            // Handle IOException appropriately (e.g., log or throw a custom exception)
            e.printStackTrace();
            throw e;
        }
    }

    public static Config getInstance() throws IOException {
        return instance == null ? instance = new Config() : instance;
    }
}