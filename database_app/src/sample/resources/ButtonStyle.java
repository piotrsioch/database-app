package sample.resources;

import javafx.scene.control.Button;

public class ButtonStyle {
    public static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    public static final String HOVERED_BUTTON_STYLE_STANDARD_MOUSE_OFF =  "-fx-background-color:#243257;";
    public static final String HOVERED_BUTTON_STYLE_STANDARD_MOUSE_ON ="-fx-background-color:#3b4d80";
    public static final String HOVERED_BUTTON_STYLE_LOGGED_IN =  "-fx-background-color:#383838;";
    public static final String WHITE_COLOR = "-fx-background-color:#e0e0e0";
    public static final String EXIT_MOUSE_ON = "-fx-background-color:#6e0119";
    public static final String EXIT_MOUSE_OFF = "-fx-background-color:#c9002c";


    public static void changeBackgroundOfTheTransparentButton(Button button, String color) {
        button.setStyle(IDLE_BUTTON_STYLE);
        button.setOnMouseEntered(e -> button.setStyle(color));
        button.setOnMouseExited(e -> button.setStyle(IDLE_BUTTON_STYLE));
    }

    public static void changeButton(Button button, String colorWhenMouseExited, String colorWhenMouseEntered) {
        button.setOnMouseExited(e -> button.setStyle(colorWhenMouseExited));
        button.setOnMouseEntered(e -> button.setStyle(colorWhenMouseEntered));
    }

}
