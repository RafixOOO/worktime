package org.example.worktime;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;

public class Main extends Application {

    private DatabaseConnector dbConnector = new DatabaseConnector();

    @Override
    public void start(Stage primaryStage) {
        dbConnector.finishtime();
        Label instructionLabel = new Label("Zeskanuj swoją kartę");
        instructionLabel.setFont(new Font("Arial", 24));
        instructionLabel.setTextFill(Color.DARKBLUE);
        instructionLabel.setStyle("-fx-padding: 20px; -fx-background-color: lightgray;");

        TextField numberInput = new TextField();
        numberInput.setMaxWidth(400);
        numberInput.setFont(new Font("Arial", 18));
        numberInput.setStyle("-fx-padding: 10px; -fx-border-color: darkblue; -fx-border-width: 2px;");

        numberInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 10 && newValue.matches("\\d+")) {
                dbConnector.saveNumber(newValue);
                primaryStage.hide();
            }
        });

        VBox inputBox = new VBox(20, instructionLabel, numberInput);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-background-color: white; -fx-padding: 30px;");

        BorderPane root = new BorderPane();
        root.setCenter(inputBox);
        root.setStyle("-fx-background-color: lightblue;");

        Scene scene = new Scene(root, 800, 600);

        // Blokowanie klawisza ESC
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                event.consume(); // Blokuje domyślną akcję klawisza ESC
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





