package sample.Register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.resources.ButtonStyle;
import sample.DatabaseConnection.DatabaseConnection;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Button registerButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label alertLabel;

    private List<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String colorWhenEntered = ButtonStyle.HOVERED_BUTTON_STYLE_STANDARD_MOUSE_ON;
        String colorWhenExited = ButtonStyle.HOVERED_BUTTON_STYLE_STANDARD_MOUSE_OFF;
        ButtonStyle.changeButton(registerButton, colorWhenEntered, colorWhenExited);
        ButtonStyle.changeBackgroundOfTheTransparentButton(goBackButton,  ButtonStyle.WHITE_COLOR);

        addTextFieldsToList();
    }

    public void registerButtonOnAction(ActionEvent event) {
        boolean isAnyFieldEmpty = isAnyFieldEmpty(textFields);
        boolean isAnyFieldTrimmed = isAnyFieldTrimmed(textFields);
        boolean isAnyFieldLessThan5Characters = isAnyFieldLessThan5Characters(textFields);

        if (isAnyFieldEmpty) {
            alertLabel.setText("Any field cannot be empty!");
        } else if (isAnyFieldTrimmed) {
            alertLabel.setText("Any field cannot contain whitespaces!");
        } else if (isAnyFieldLessThan5Characters) {
            alertLabel.setText("Password and username length cannot be less than 5!");
        }
        else if (passwordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
            createAlertAndCloseScene();
        } else {
            alertLabel.setText("Password does not match");
        }
    }

    public void goBackButtonOnAction(ActionEvent event) {
        try {
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        String insertFields = "INSERT INTO user_account(first_name, last_name, username, password) VALUES ('";
        String insertValues = firstname + "', '" + lastname + "', '" + username + "', '" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User has been registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    private boolean isAnyFieldEmpty(List<TextField> textFields) {
        boolean isAnyFieldEmpty = false;

        for (TextField textField : textFields) {
            if (textField.getText().trim().isEmpty()) {
                isAnyFieldEmpty = true;
            }
        }
        return isAnyFieldEmpty;
    }

    private boolean isAnyFieldTrimmed(List<TextField> textFields) {
        boolean isAnyFieldTrimmed = false;
        int lengthBeforeTrimming;
        int lengthAfterTrimming;

        for (TextField textField : textFields) {
            lengthBeforeTrimming = textField.getText().length();
            lengthAfterTrimming = textField.getText().trim().length();
            if (lengthBeforeTrimming != lengthAfterTrimming) {
                isAnyFieldTrimmed = true;
            }
        }
        return isAnyFieldTrimmed;
    }

    private boolean isAnyFieldLessThan5Characters(List<TextField> textFields) {
        boolean isAnyFieldLessThan5Characters = false;
        for (TextField textField : textFields) {
            if(textField.equals(firstnameTextField) || textField.equals(lastnameTextField)) {
                break;
            }
            int length = textField.getText().length();
            if (length < 5) {
                isAnyFieldLessThan5Characters = true;
            }
        }
        return isAnyFieldLessThan5Characters;
    }

    private void createAlertAndCloseScene()  {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You have been successfully registered! Now you can login using entered credentials.");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.show();

            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkIfUsernameExistsInDatabase() {
        boolean usernameExistsInDatabase = false;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        return usernameExistsInDatabase;
    }

    private void addTextFieldsToList() {
        textFields.add(passwordField);
        textFields.add(confirmPasswordField);
        textFields.add(firstnameTextField);
        textFields.add(lastnameTextField);
        textFields.add(usernameTextField);
    }

    private void changeScene() throws IOException {
        Parent root = (Parent) FXMLLoader.load(RegisterController.class.getClassLoader().getResource("sample/Login/login.fxml"));
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

}
