/**
 * Created by AllarVendla on 20.11.2016.
 */

import javafx.application.Application;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionModel;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class View extends Application {

    private ChoiceBox<String> selectCounty;
    private ChoiceBox<String> selectCity;

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

        initSelectCounty();
        initSelectCity();


        ChoiceBox selectStreet = new ChoiceBox<>(Register.getData(""));
        resetToDefault(selectStreet);
        setState(selectStreet,true);
        ChoiceBox selectApartment = new ChoiceBox<>(Register.getData(""));
        resetToDefault(selectApartment);
        setState(selectApartment,true);
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

        selectCity.setOnAction(event -> {
            System.out.println("vajutus?");
            SelectionModel selection = selectCity.getSelectionModel();
            if (selection.getSelectedIndex() != 0) {
                Register.setConditions("city = '" + selection.getSelectedItem() + "'");
                selectStreet.setItems(Register.getData("street"));
                setState(selectStreet,false);
                resetToDefault(selectStreet);
            } else {
                Register.clearConditions();
                resetToDefault(selectStreet);
                resetToDefault(selectApartment);
                setState(selectStreet, true);
                setState(selectApartment, true);
            }
        });
    }

    private void initSelectCounty(){
        selectCounty = new ChoiceBox<>(Register.getData("county"));
        resetToDefault(selectCounty);
        selectCounty.setOnAction(event -> {
            SelectionModel selection = selectCounty.getSelectionModel();
            if (selection.getSelectedIndex() != 0) {
                Register.setConditions("county = '" + selection.getSelectedItem() + "'");
                selectCity.setItems(Register.getData("city"));
                setState(selectCity,false);
                resetToDefault(selectCity);
            } else {
                Register.clearConditions();
                resetToDefault(selectCity);
                resetToDefault(selectStreet);
                resetToDefault(selectApartment);
                setState(selectCity, true);
                setState(selectStreet,true);
                setState(selectApartment, true);
            }
        });

    }

    private void initSelectCity(){
        selectCity = new ChoiceBox<>(Register.getData(""));
        resetToDefault(selectCity);
        setState(selectCity,true);
    }

    private void setState(ChoiceBox<String> selectCity, boolean state) {
        selectCity.setDisable(state);
    }

    private void resetToDefault(ChoiceBox<String> selectCity) {
        selectCity.getSelectionModel().select(0);
    }
}
