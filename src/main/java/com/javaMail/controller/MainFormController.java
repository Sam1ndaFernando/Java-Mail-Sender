package com.javaMail.controller;

import com.javaMail.util.SendMail;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @FXML
    private TextField txtSenderMail;

    @FXML
    private TextField txtReceiverMail;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextArea txtMessage;

    @FXML
    private JFXButton btnBack;

    @FXML
    private Label lblAttachedFileLabel;

    @FXML
    private TextField txtAttachedFile;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnAction(event -> Platform.exit());

        lblAttachedFileLabel.setVisible(false);
        txtAttachedFile.setVisible(false);

        txtMessage.setStyle("-fx-control-inner-background:  #393646; -fx-text-fill:  #f4eee0; -fx-border-width: 0;");
        txtReceiverMail.setStyle("-fx-control-inner-background:  #393646; -fx-text-fill:  #f4eee0; ");
        txtSenderMail.setStyle("-fx-control-inner-background:  #393646; -fx-text-fill:  #f4eee0; ");
        txtSubject.setStyle("-fx-control-inner-background:  #393646; -fx-text-fill:  #f4eee0; ");
        txtAttachedFile.setStyle("-fx-control-inner-background:  #393646; -fx-text-fill:  #f4eee0;");

        txtSenderMail.setText("ichat925@gmail.com");
    }

    @FXML
    void btnAttachmentOnAction(ActionEvent event) {
        lblAttachedFileLabel.setVisible(true);
        txtAttachedFile.setVisible(true);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File to Attach");

        // Uncomment the following lines if you want to add filters for PDF and all files
    /*
    fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("PDF Files", "*.pdf"),
            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
            new ExtensionFilter("All Files", "*.*")
    );
    */

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

            if ("png".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension) || "gif".equalsIgnoreCase(fileExtension)) {
                txtAttachedFile.setText(selectedFile.getName());
            } else {
                System.out.println("Selected file is not an image.");
                // Optionally, you may want to clear the text field if the selected file is not an image
                txtAttachedFile.clear();
            }
        } else {
            System.out.println("File selection canceled.");
            // Optionally, you may want to clear the text field if the user cancels the file selection
            txtAttachedFile.clear();
        }
    }


    @FXML
    void btnSendOnAction(ActionEvent event) {
        String recipientEmail = txtReceiverMail.getText();
        String subject = txtSubject.getText();
        String messageText = txtMessage.getText();

        // Use Task for better JavaFX integration
        Task<Void> sendMailTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Execute your email sending logic here
                SendMail emailSender = new SendMail(recipientEmail, subject, messageText, selectedFile);
                emailSender.send(); // Assuming you have a send() method in your SendMail class
                return null;
            }
        };

        // Set up event handlers for task completion (success or failure)
        sendMailTask.setOnSucceeded(workerStateEvent -> {
            clearFields();
            // Show success message or perform other UI-related actions
            showAlert("Email sent successfully!", Alert.AlertType.CONFIRMATION);
        });

        sendMailTask.setOnFailed(workerStateEvent -> {
            // Handle failure, show error message, log the exception, etc.
            showAlert("Error sending email: " + sendMailTask.getException().getMessage(), Alert.AlertType.ERROR);
        });

        // Run the task on a background thread
        new Thread(sendMailTask).start();
    }

    // Utility method to show alerts on the JavaFX Application Thread
    private void showAlert(String message, Alert.AlertType alertType) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle("Email Status");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @FXML
    void txtMessageOnAction(MouseEvent event) {

    }

    @FXML
    void txtReceiverMailOnAction(ActionEvent event) {

    }

    @FXML
    void txtSenderMailOnAction(ActionEvent event) {

    }

    @FXML
    void txtSubjectOnAction(ActionEvent event) {

    }

    private void clearFields() {
        txtReceiverMail.clear();
        txtSubject.clear();
        txtMessage.clear();
        txtAttachedFile.clear();
        txtAttachedFile.setVisible(false);
        lblAttachedFileLabel.setVisible(false);
    }

}
