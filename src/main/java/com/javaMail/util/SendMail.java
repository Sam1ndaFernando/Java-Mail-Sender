package com.javaMail.util;

import com.sun.javafx.scene.control.Properties;

import java.io.File;
import java.io.IOException;

public class SendMail {
    private final String senderEmail;
    private final String recipientEmail;
    private final String subject;
    private final String messageText;
    private final File selectedFile;
    private final String password;
    Properties properties = new Properties();
    public SendMail(String recipientEmail, String subject, String messageText, File selectedFile) throws IOException {
        this.senderEmail = "ichat925@gmail.com";
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.messageText = messageText;
        this.selectedFile = selectedFile;
        this.password = Config.getInstance().getPassword();
    }
}
