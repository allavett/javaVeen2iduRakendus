/**
 * Created by AllarVendla on 20.11.2016.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class View extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Veenäidu teatamise rakendus");
// Buttons
        Button btnRegister = new Button("Registreeri");
        Button btnLogin = new Button("Logi sisse");
        Button btnLogout = new Button("Logi välja");
        Button btnNewCounter = new Button("Saada uus näit");
        Button btnCounterHistory = new Button("Vaata ajalugu");
        Button btnSubmit = new Button("Saada");
        Button btnBack = new Button("Tagasi");
// Fields
        TextField fieldFirstName = new TextField();
        fieldFirstName.setPromptText("Kasutaja");
        TextField fieldLastName = new TextField();
        fieldLastName.setPromptText("Parool");
        System.out.println(TextField.getClassCssMetaData());
// ChoiceBoxes
        ChoiceBox choiceCounty = new ChoiceBox();
// Layouts
        VBox layoutMain = new VBox();
        VBox layoutRegister = new VBox();
        layoutMain.getChildren().addAll(btnRegister,btnLogin,btnLogout,btnNewCounter,btnCounterHistory);
        layoutRegister.getChildren().addAll(btnBack,fieldFirstName,fieldLastName,btnSubmit);
// Scenes
        Scene sceneMain = new Scene(layoutMain, 600, 300);
        Scene sceneRegister = new Scene(layoutRegister, 600, 300);

// Stage actions
        primaryStage.setScene(sceneMain);
        primaryStage.show();

        btnRegister.setOnAction(event -> {
            System.out.println("Registreeri");
            primaryStage.setScene(sceneRegister);
        });
        btnSubmit.setOnAction(event -> {
            System.out.println("Saada");
            System.out.println(Register.checkData(fieldFirstName.getText(), fieldLastName.getText()));
            Database.databaseActions();

        });
        btnBack.setOnAction(event -> {
            System.out.println("Tagasi");
            primaryStage.setScene(sceneMain);
        });
    }
}
