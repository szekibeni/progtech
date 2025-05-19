package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label welcomeLabel = new Label("Üdvözöljük utasainkat Eger vasútállomáson!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

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

        VBox layout = new VBox(30, welcomeLabel, adminBtn, userBtn);
        layout.setStyle("-fx-padding: 40; -fx-alignment: center; -fx-background-color: linear-gradient(to bottom, #002147, #003366);");
        layout.setAlignment(Pos.CENTER);

        // Ablak mérete megegyezik a TrainApp-ével
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Bejelentkezés");

        // Itt jön az ikon beállítása
        Image icon = new Image(getClass().getResourceAsStream("/prog.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}