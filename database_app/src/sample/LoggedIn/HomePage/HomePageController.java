package sample.LoggedIn.HomePage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.LoggedIn.LoggedInController;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomePageController extends LoggedInController implements Initializable {
    @FXML
    private Label welcomeLabel;

    public void initialize(URL location, ResourceBundle resources) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/sample/resources/credentials.txt"));
            String username = scanner.nextLine();
            welcomeLabel.setText("Welcome " + username + "!" );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

