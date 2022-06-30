package sample.LoggedIn.Diary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DatabaseConnection.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class AddNoteController {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField shortDescriptionTextField;

    @FXML
    private TextArea detailsTextArea;

    @FXML
    private Label errorLabel;

    public void addButtonOnAction() {
        boolean isAnyFieldEmpty = isAnyFieldEmpty();
        if(isAnyFieldEmpty) {
            errorLabel.setText("Any field cannot be empty!");
        } else {
            addNote();
            createAlertAndCloseScene();
        }
    }

    public void cancelButtonOnAction() {
        try {
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addNote() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String title = titleTextField.getText();
        String shortDescription = shortDescriptionTextField.getText();
        String details = detailsTextArea.getText();

        try {
            Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();
            String insertFields = "INSERT INTO user_notes(title, short_description, details, username) VALUES ('";
            String insertValues = title + "', '" + shortDescription + "', '" + details + "', '" + username + "')";
            String query = insertFields + insertValues;
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean isAnyFieldEmpty() {
        if(titleTextField.getText().trim().isEmpty() ||
                shortDescriptionTextField.getText().trim().isEmpty() ||
                detailsTextArea.getText().trim().isEmpty()) {
            return true;
        }
        return false;
    }

    private void createAlertAndCloseScene() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You have added a note!");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.show();
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void changeScene() throws IOException {
        Parent root = (Parent) FXMLLoader.load(AddNoteController.class.getClassLoader().getResource("sample/LoggedIn/Diary/diary.fxml"));
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
