package sample.LoggedIn.Medicines;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DatabaseConnection.DatabaseConnection;
import sample.LoggedIn.AppController;
import sample.LoggedIn.LoggedInController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class MedicineController extends LoggedInController implements AppController, Initializable {

    @FXML
    private Button takeButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Medicine> medicineTableView;

    @FXML
    private TableColumn<Medicine, String> name;

    @FXML
    private TableColumn<Medicine, Float> dose;

    @FXML
    private TableColumn<Medicine, Date> useDuration;

    @FXML
    private TableColumn<Medicine, String> purpose;

    @FXML
    private TableColumn<Medicine, String> status;

    private List<Medicine> medicineList;

    public void initialize(URL location, ResourceBundle resources) {

        medicineList = FXCollections.observableArrayList();
        name.setCellValueFactory(new PropertyValueFactory<Medicine, String>("name"));
        useDuration.setCellValueFactory(new PropertyValueFactory<Medicine, Date>("useDuration"));
        dose.setCellValueFactory(new PropertyValueFactory<Medicine, Float>("dose"));
        purpose.setCellValueFactory(new PropertyValueFactory<Medicine, String>("purpose"));
        status.setCellValueFactory(new PropertyValueFactory<Medicine, String>("status"));
        refreshData();
        // refreshData();

    }


    @Override
    public void addButtonOnAction() {
        try {
            Parent root = (Parent) FXMLLoader.load(MedicineController.class.getClassLoader().getResource("sample/LoggedIn/Medicines/add-medicine.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void deleteButtonOnAction() {
        medicineTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Medicine medicine = (Medicine) medicineTableView.getSelectionModel().getSelectedItem();
        String name = medicine.getName();
        String purpose = medicine.getPurpose();
        float dose = medicine.getDose();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            if (medicine == null) {

            } else {
                Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
                String username = scanner.nextLine();
                String deleteQuery = "DELETE FROM user_medicines WHERE title = '" + name + "' AND purpose " +
                        " = '" + purpose + "'" + " AND dose = " + dose + " AND username = '" + username + "'";
                Statement statement = connectDb.createStatement();
                statement.executeUpdate(deleteQuery);
                String deleteDates = "DROP TABLE " + name + "_dates";
                statement.executeUpdate(deleteDates);

                refreshData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshButtonOnAction() {
        refreshData();
    }

    private void refreshData() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();


        try {
            ArrayList<String> nameArrayList = new ArrayList<>();
            medicineList.clear();
            medicineTableView.getItems().clear();
            Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();
            String query = "SELECT * FROM user_medicines WHERE username = '" + username + "'";

            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            String isTaken = "no";

            while (queryResult.next()) {
                medicineList.add(new Medicine(
                        queryResult.getString("title"),
                        queryResult.getFloat("dose"),
                        queryResult.getDate("use_duration"),
                        queryResult.getString("purpose"),
                        isTaken
                ));
            }
            medicineTableView.setItems((ObservableList<Medicine>) medicineList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        checkIfTaken();
    }

    private void checkIfTaken() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();


        for (Medicine medicine : medicineList) {
            try {
                String name = medicine.getName();
                LocalDate actualDate = LocalDate.now();
                Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
                String username = scanner.nextLine();

                String checkIfGivenDateExistsInTable = "SELECT * FROM " + name + "_dates WHERE m_date = '" +
                        actualDate + "' AND username = '" + username + "'";
                Statement statement = connectDb.createStatement();
                ResultSet queryResult = statement.executeQuery(checkIfGivenDateExistsInTable);

                while (queryResult.next()) {
                    if (!queryResult.getString(2).equals(null)) {
                        int index = medicineList.indexOf(medicine);
                        Medicine med = medicineList.get(index);
                        med.setStatus("Yes");
                        medicineList.set(index, med);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void takeMedicine() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            medicineTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            Medicine medicine = (Medicine) medicineTableView.getSelectionModel().getSelectedItem();
            String name = medicine.getName();
            LocalDate actualDate = LocalDate.now();
            Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();

            String insertCurrentDate = "INSERT INTO " + name + "_dates(m_date, username) VALUES('" +
                    actualDate + "', '" + username + "')";
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(insertCurrentDate);

        } catch(Exception e) {
            e.printStackTrace();
        }
        refreshData();
    }
}
