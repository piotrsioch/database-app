package sample.LoggedIn.Medicines;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DatabaseConnection.DatabaseConnection;
import sample.LoggedIn.Diary.DiaryController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddMedicineController implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField doseTextField;

    @FXML
    private TextField purposeTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label errorLabel;

    private List<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTextFieldsToList();
    }

    public void addButtonOnAction() {
        boolean isAnyFieldEmpty = isAnyFieldEmpty();
        if(isAnyFieldEmpty) {
            errorLabel.setText("Any field cannot be empty!"); }
        else {
            addMedicine();
            createAlertAndCloseScene();
        }
    }
    private void addMedicine() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String name = nameTextField.getText();
        float dose = Float.valueOf(doseTextField.getText());
        LocalDate useDuration = datePicker.getValue();
        String purpose = purposeTextField.getText();



        try {
            Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();

            String insertFields = "INSERT INTO user_medicines(title, dose, use_duration, purpose, username) VALUES ('";
            String insertValues = name + "', '" + dose + "', '" + useDuration + "', '" + purpose + "', '" + username +  "')";

            String query = insertFields + insertValues;
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            String createNewTable =  "CREATE TABLE " +  name + "_dates ( " +
                    " medicine_id INT, " +
                    " m_date Date NOT NULL," +
                    " username VARCHAR(35) NOT NULL, " +
                    " FOREIGN KEY (username) REFERENCES user_account(username), " +
                    " FOREIGN KEY (medicine_id) REFERENCES user_medicines(medicine_id)) ";
            statement.executeUpdate(createNewTable);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void createAlertAndCloseScene() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You have added a medicine!");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.show();
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        } }

    public void cancelButtonOnAction() {
        try {
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeScene() throws IOException {
        Parent root = (Parent) FXMLLoader.load(AddMedicineController.class.getClassLoader().getResource("sample/LoggedIn/Medicines/medicines.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    private boolean isAnyFieldEmpty() {
        boolean isAnyFieldEmpty = false;

        for (TextField textField : textFields) {
            if (textField.getText().trim().isEmpty()) {
                isAnyFieldEmpty = true;
            }

            if( (datePicker.getValue() == null)) {
                isAnyFieldEmpty = true;
            }

        }
        return isAnyFieldEmpty;
    }

    private void addTextFieldsToList() {
        textFields.add(nameTextField);
        textFields.add(doseTextField);
        textFields.add(purposeTextField);
    }


}
