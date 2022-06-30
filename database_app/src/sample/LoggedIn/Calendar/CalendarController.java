package sample.LoggedIn.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DatabaseConnection.DatabaseConnection;
import sample.LoggedIn.AppController;
import sample.LoggedIn.Diary.DiaryController;
import sample.LoggedIn.LoggedInController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class CalendarController extends LoggedInController implements AppController, Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<CalendarEvent> eventsTableView;

    @FXML
    private TableColumn<CalendarEvent, Date> date;

    @FXML
    private TableColumn<CalendarEvent, String> importance;

    @FXML
    private TableColumn<CalendarEvent, Integer> hour;

    @FXML
    private TableColumn<CalendarEvent, Integer> minutes;

    @FXML
    private TableColumn<CalendarEvent, String> type;

    @FXML
    private TableColumn<CalendarEvent, String> title;

    @FXML
    private TableColumn<CalendarEvent, String> details;

    private List<CalendarEvent> eventList;


    public void initialize(URL location, ResourceBundle resources) {

        eventList = FXCollections.observableArrayList();
        date.setCellValueFactory(new PropertyValueFactory<CalendarEvent, Date>("date"));
        importance.setCellValueFactory(new PropertyValueFactory<CalendarEvent, String>("importance"));
        hour.setCellValueFactory(new PropertyValueFactory<CalendarEvent, Integer>("hour"));
        minutes.setCellValueFactory(new PropertyValueFactory<CalendarEvent, Integer>("minutes"));
        type.setCellValueFactory(new PropertyValueFactory<CalendarEvent, String>("type"));
        title.setCellValueFactory(new PropertyValueFactory<CalendarEvent, String>("title"));
        details.setCellValueFactory(new PropertyValueFactory<CalendarEvent, String>("details"));






        refreshData();
        //noteListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //refreshData();
    }


    @Override
    public void addButtonOnAction() {
        try {
            Parent root = (Parent) FXMLLoader.load(DiaryController.class.getClassLoader().getResource("sample/LoggedIn/Calendar/add-event.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void deleteButtonOnAction() {
        eventsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CalendarEvent calendarEvent = (CalendarEvent) eventsTableView.getSelectionModel().getSelectedItem();
        String title = calendarEvent.getTitle();
        String details = calendarEvent.getDetails();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        try {
            String deleteQuery = "DELETE FROM user_events WHERE title = '" + title + "' AND details " +
                    " = '" + details + "'";
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(deleteQuery);
            refreshData();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshButtonOnAction() {
        refreshData();
    }

    private void refreshData()  {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();


        try {
            eventList.clear();
            eventsTableView.getItems().clear();
            Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();
            String query = "SELECT * FROM user_events WHERE username = '" + username + "'";

            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                eventList.add(new CalendarEvent(
                        queryResult.getString("importance"),
                        queryResult.getString("event_type"),
                        queryResult.getString("title"),
                        queryResult.getString("details"),
                        queryResult.getDate("event_date"),
                        queryResult.getInt("event_hour"),
                        queryResult.getInt("event_minutes")
                ));
            }
            eventsTableView.setItems((ObservableList<CalendarEvent>) eventList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
