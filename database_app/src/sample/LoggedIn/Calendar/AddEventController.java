package sample.LoggedIn.Calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DatabaseConnection.DatabaseConnection;
import sample.LoggedIn.Diary.AddNoteController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class AddEventController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> importanceComboBox;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField detailsTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private ComboBox<Integer> hoursComboBox;

    @FXML
    private ComboBox<Integer> minutesComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorLabel;

    private List<TextField> textFields = new ArrayList<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeHoursComboBox();
        initializeMinutesComboBox();
        initializeImportanceComboBox();
        addTextFieldsToList();
    }

    public void cancelButtonOnAction() {
        try {
            switchScene();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addButtonOnAction() {
        boolean isAnyFieldEmpty = isAnyFieldEmpty();
        if(isAnyFieldEmpty) {
            errorLabel.setText("Any field cannot be empty!"); }
        else {
            addEvent();
            createAlertAndCloseScene();
        }
    }

    private void addEvent() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();


        String importance = importanceComboBox.getValue();
        String type = typeTextField.getText();
        String title = titleTextField.getText();
        String details = detailsTextField.getText();
        LocalDate date = datePicker.getValue();
        Integer hour = hoursComboBox.getValue();
        Integer minutes = minutesComboBox.getValue();



        try {
            Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();


            String insertFields = "INSERT INTO user_events(importance, event_type, title, details, event_date" +
                    ", event_hour, event_minutes, username) VALUES ('";
            String insertValues = importance + "', '" + type + "', '" + title + "', '" + details + "', '" + date +
                    "', " + hour + "," + minutes + ",'" + username +  "')";

            String query = insertFields + insertValues;
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void createAlertAndCloseScene() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You have added an event!");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.show();
            switchScene();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isAnyFieldEmpty() {
        boolean isAnyFieldEmpty = false;

        for (TextField textField : textFields) {
            if (textField.getText().trim().isEmpty()) {
                isAnyFieldEmpty = true;
            }

            if(importanceComboBox.getItems().isEmpty() || minutesComboBox.getItems().isEmpty() || hoursComboBox.getItems().isEmpty()
                    || hoursComboBox.getItems().isEmpty() || (datePicker.getValue() == null)) {
                isAnyFieldEmpty = true;
            }

        }
        return isAnyFieldEmpty;
    }



    private void initializeHoursComboBox() {
        ArrayList<Integer> hoursArrayList = new ArrayList<>();
        for (int i=0; i<24; i++) {
            hoursArrayList.add(i);
        }
        hoursComboBox.getItems().addAll(hoursArrayList);
    }

    private void initializeMinutesComboBox() {
        ArrayList<Integer> minutesArrayList = new ArrayList<>();
        for (int i = 0; i<60; i+=5) {
            minutesArrayList.add(i);
        }
        minutesComboBox.getItems().addAll(minutesArrayList);
    }

    private void initializeImportanceComboBox() {
        importanceComboBox.getItems().addAll("Very important",
                "Important", "Medium important", "Not important");
    }

    private void addTextFieldsToList() {
        textFields.add(typeTextField);
        textFields.add(detailsTextField);
        textFields.add(titleTextField);
    }

    private void switchScene() throws IOException {
        Parent root = (Parent) FXMLLoader.load(AddNoteController.class.getClassLoader().getResource("sample/LoggedIn/Calendar/calendar.fxml"));
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
