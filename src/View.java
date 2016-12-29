/**
 * Created by AllarVendla on 20.11.2016.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionModel;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.*;


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
        ChoiceBox<String> selectCounty = new ChoiceBox<>(Register.getData("county"));
        selectCounty.getSelectionModel().select(0);
        ChoiceBox<String> selectCity = new ChoiceBox<>();
        selectCity.setDisable(true);
        ChoiceBox selectStreet = new ChoiceBox<>();
        selectStreet.setDisable(true);
        ChoiceBox selectApartment = new ChoiceBox<>();
        selectApartment.setDisable(true);
// Layouts
        VBox layoutMain = new VBox();
        VBox layoutRegister = new VBox();
        layoutMain.getChildren().addAll(btnRegister,btnLogin,btnLogout,btnNewCounter,btnCounterHistory);
        layoutRegister.getChildren().addAll(btnBack,selectCounty,selectCity,selectStreet,selectApartment,fieldFirstName,fieldLastName,btnSubmit);
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
        });
        btnBack.setOnAction(event -> {
            System.out.println("Tagasi");
            primaryStage.setScene(sceneMain);
        });
        selectCounty.setOnAction(event -> {
            SelectionModel selection = selectCounty.getSelectionModel();
            if (selection.getSelectedIndex() != 0) {
                Register.setConditions("county = '" + selection.getSelectedItem() + "'");
                selectCity.setItems(Register.getData("city"));
                selectCity.setDisable(false);
                selectCity.getSelectionModel().select(0);


            } else {
                Register.clearConditions();
                selectCity.setDisable(true);
                selectStreet.setDisable(true);
                selectApartment.setDisable(true);
            }
            selectCity.setItems(Register.getData("city"));
        });
    }
}
