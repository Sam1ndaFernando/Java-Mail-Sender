package com.javaMail.util;

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
import java.util.Properties;

public class SendMail extends Thread {
    private final String senderEmail;
    private final String recipientEmail;
    private final String subject;
    private final String messageText;
    private final File selectedFile;
    private final String password;

    public SendMail(String recipientEmail, String subject, String messageText, File selectedFile) throws IOException {
        this.senderEmail = "chatroom137@gmail.com";
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.messageText = messageText;
        this.selectedFile = selectedFile;
        this.password = Config.getInstance().getPassword();
    }

    @Override
    public void run() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server
        properties.put("mail.smtp.port", "587"); // Replace with your SMTP server's port
        properties.put("mail.smtp.auth", "true"); // Enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS encryption

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("chatroom137@gmail.com", password); // Replace with your email and password
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            // Email text part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageText);
            multipart.addBodyPart(messageBodyPart);

            // Attachment part
            if (selectedFile != null) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(selectedFile);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(selectedFile.getName());
                multipart.addBodyPart(messageBodyPart);
            }

            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            Platform.runLater(() -> {
                Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION, "Email sent successfully!", ButtonType.OK, ButtonType.CANCEL).showAndWait();
                if (choice.isPresent() && choice.get() == ButtonType.OK) {
                    // Handle OK button click if needed
                }
                System.out.println("Email sent successfully!");
            });

        } catch (MessagingException e) {
            handleMessagingException(e);
        }
    }

    private void handleMessagingException(MessagingException e) {
        e.printStackTrace();
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error sending email: " + e.getMessage());
            alert.showAndWait();
        });
    }
}
