package sample.Login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.resources.ButtonStyle;
import sample.DatabaseConnection.DatabaseConnection;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button quitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button signInButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField enterPasswordField;

    private ArrayList<Button> buttonList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String colorWhenEntered = ButtonStyle.HOVERED_BUTTON_STYLE_STANDARD_MOUSE_ON;
        String colorWhenExited = ButtonStyle.HOVERED_BUTTON_STYLE_STANDARD_MOUSE_OFF;
        ButtonStyle.changeButton(loginButton, colorWhenEntered, colorWhenExited);
        ButtonStyle.changeButton(quitButton, ButtonStyle.EXIT_MOUSE_OFF, ButtonStyle.EXIT_MOUSE_ON);
        ButtonStyle.changeBackgroundOfTheTransparentButton(signInButton,  ButtonStyle.WHITE_COLOR);
    }

    public void loginButtonOnAction(ActionEvent event) {
        if (!usernameTextField.getText().isEmpty()  && !enterPasswordField.getText().isEmpty()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void quitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    public void singinButtonOnAction(ActionEvent event) {
        createAccountForm();
    }

    private void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() +
                "' AND password = '" +  enterPasswordField.getText() + "'";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    saveCredentials();
                    deleteTextFromFields();
                    changeScene();

                } else {
                    loginMessageLabel.setText("Invalid credentials. Please try again.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void createAccountForm() {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/sample/Register/register.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.getScene().setRoot(root);

            // Stage registerStage = new Stage();
            //registerStage.initStyle(StageStyle.UNDECORATED);
            //registerStage.setScene(new Scene(root, 800, 600));
            //registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void saveCredentials() throws IOException {
        FileWriter writer = new FileWriter("src/sample/resources/credentials.txt");
        writer.write(usernameTextField.getText());
        writer.close();
    }

    private void deleteTextFromFields() {
        loginMessageLabel.setText("");
        usernameTextField.setText("");
        enterPasswordField.setText("");
    }

    private void changeScene() throws IOException {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/sample/LoggedIn/HomePage/homepage.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.getScene().setRoot(root);

    }

}
