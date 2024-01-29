package com.javaMail.util;

import com.sun.javafx.scene.control.Properties;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SendMail extends Thread{
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

    @Override
    public void run() {
        try {
            // Set up mail server properties
            Properties properties = new Properties();
            // ... (your existing code)

            // Create a session with authentication
            Session session = Session.getInstance(properties, new Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    try {
                        String email = "ichat925@gmail.com";
                        String password = Config.getInstance().getPassword();
                        if (password == null || password.isEmpty()) {
                            throw new RuntimeException("Email password is null or empty.");
                        }
                        return new PasswordAuthentication(email, password);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            // Create and send the email
            try (Transport transport = session.getTransport("smtp")) {
                // ... (your existing code for creating and sending the email)
            }

            // Show success message on JavaFX thread
            Platform.runLater(() -> {
                Optional<ButtonType> choose = new Alert(Alert.AlertType.CONFIRMATION, "Email sent successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                if (choose.isPresent() && choose.get() == ButtonType.OK) {
                    // Handle OK button click if needed
                }
                System.out.println("Email sent successfully!");
            });

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();

            Platform.runLater(() -> {
                // Show an alert on the JavaFX application thread for error handling
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error sending email: " + e.getMessage());
                alert.showAndWait();
            });
        }
    }

}
