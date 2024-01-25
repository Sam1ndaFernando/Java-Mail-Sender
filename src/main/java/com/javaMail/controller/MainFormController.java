package com.javaMail.controller;

import com.jfoenix.controls.JFXButton;
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
