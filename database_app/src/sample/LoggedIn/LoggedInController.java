package sample.LoggedIn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    private static final String HOVERED_BUTTON_STYLE =  "-fx-background-color:#383838;";

    @FXML
    private Button homePageButton;

    @FXML
    private Button calendarButton;

    @FXML
    private Button medicinesButton;

    @FXML
    private Button diaryButton;

    @FXML
    private Button logoutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void logoutButtonOnAction(ActionEvent event) {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/sample/Login/login.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void homePageButtonOnAction(ActionEvent event) {
        getClickedScene("sample/LoggedIn/HomePage/homepage.fxml");
    }

    public void medicinesButtonOnAction(ActionEvent event) {
        getClickedScene("sample/LoggedIn/Medicines/medicines.fxml");
    }

    public void calendarButtonOnAction(ActionEvent event) {
        getClickedScene("sample/LoggedIn/Calendar/calendar.fxml");
    }


    public void diaryButtonOnAction(ActionEvent event) {
        getClickedScene("sample/LoggedIn/Diary/diary.fxml");
    }

    private void getClickedScene(String fxmlPath) {
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Parent root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource(fxmlPath));
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
