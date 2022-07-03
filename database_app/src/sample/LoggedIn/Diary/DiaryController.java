package sample.LoggedIn.Diary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DatabaseConnection.DatabaseConnection;
import sample.LoggedIn.AppController;
import sample.LoggedIn.LoggedInController;
import sample.Register.RegisterController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DiaryController extends LoggedInController implements AppController {
    private List<Note> noteList;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<Note> noteListView;

    @FXML
    private TextArea detailsTextArea;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;




    public void initialize(URL location, ResourceBundle resources) {
        noteList = new ArrayList<>();
        noteListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        refreshData();
    }

    @FXML
    public void listViewOnAction() {
        try {
            Note note = (Note) noteListView.getSelectionModel().getSelectedItem();
            detailsTextArea.setText(note.getDetails());
        }
        catch(Exception e) {

        }



    }

    @FXML
    public void addButtonOnAction() {
        try {
            Parent root = (Parent) FXMLLoader.load(DiaryController.class.getClassLoader().getResource("sample/LoggedIn/Diary/add-note.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void deleteButtonOnAction() {
        try {

            noteListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            Note note = (Note) noteListView.getSelectionModel().getSelectedItem();
            if (note == null) {

            } else {
                String title = note.getTitle();
                String shortDescription = note.getShortDescription();

                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDb = connectNow.getConnection();

                String deleteQuery = "DELETE FROM user_notes WHERE title = '" + title + "' AND short_description " +
                        " = '" + shortDescription + "'";
                Statement statement = connectDb.createStatement();
                statement.executeUpdate(deleteQuery);
                refreshData();
            }


        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        detailsTextArea.setText("");
    }



    public void refreshButtonOnAction()  {
        refreshData();
    }

    private void refreshData()  {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();


        try {
            noteList.clear();
            noteListView.getItems().clear();
            Scanner scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();
            String query = "SELECT * FROM user_notes WHERE username = '" + username + "'";

            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                noteList.add(new Note(
                        queryResult.getString("title"),
                        queryResult.getString("short_description"),
                        queryResult.getString("details")
                ));
            }
            noteListView.getItems().addAll(noteList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
