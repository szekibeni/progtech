package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button adminBtn = new Button("Bejelentkezés állomásfőnökként");
        Button userBtn = new Button("Bejelentkezés utasként");

        adminBtn.setOnAction(e -> {
            new TrainApp(true).start(new Stage());
            primaryStage.close();
        });

        userBtn.setOnAction(e -> {
            new TrainApp(false).start(new Stage());
            primaryStage.close();
        });

        VBox layout = new VBox(10, adminBtn, userBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center");

        primaryStage.setScene(new Scene(layout, 300, 150));
        primaryStage.setTitle("Bejelentkezés");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
