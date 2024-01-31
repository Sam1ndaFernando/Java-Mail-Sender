package com.javaMail.controller;

import com.javaMail.util.SendMail;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
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
import java.io.IOException;
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
    private File selectedFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Event handling for the exit button
        btnBack.setOnAction(event -> Platform.exit());

        // Set initial visibility and styles for UI components
        lblAttachedFileLabel.setVisible(false);
        txtAttachedFile.setVisible(false);
        txtMessage.setStyle("-fx-control-inner-background: #393646; -fx-text-fill: #f4eee0; -fx-border-width: 0;");
        txtReceiverMail.setStyle("-fx-control-inner-background: #393646; -fx-text-fill: #f4eee0; ");
        txtSenderMail.setStyle("-fx-control-inner-background: #393646; -fx-text-fill: #f4eee0; ");
        txtSubject.setStyle("-fx-control-inner-background: #393646; -fx-text-fill: #f4eee0; ");
        txtAttachedFile.setStyle("-fx-control-inner-background: #393646; -fx-text-fill: #f4eee0;");

        // Set default sender email
        txtSenderMail.setText("chatroom137@gmail.com");
    }
    @FXML
    void btnAttachmentOnAction(ActionEvent event) {
        // Show file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File to Attach");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Display selected file name
            txtAttachedFile.setText(selectedFile.getName());
            lblAttachedFileLabel.setVisible(true);
            txtAttachedFile.setVisible(true);
        } else {
            // If no file is selected, hide the file information
            lblAttachedFileLabel.setVisible(false);
            txtAttachedFile.setVisible(false);
        }
    }

    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        // Get the email sender, recipient, subject, and message text from the text fields
        String recipientEmail = txtReceiverMail.getText();
        String subject = txtSubject.getText();
        String messageText = txtMessage.getText();

        // Create a new EmailSenderThread
        SendMail emailSenderThread = new SendMail(recipientEmail, subject, messageText, selectedFile);

        // Start the thread to send the email
        emailSenderThread.start();

        // Clear input fields
        clearFields();
    }

    private void clearFields() {
        txtReceiverMail.clear();
        txtSubject.clear();
        txtMessage.clear();
        txtAttachedFile.clear();
        lblAttachedFileLabel.setVisible(false);
        txtAttachedFile.setVisible(false);
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


}
