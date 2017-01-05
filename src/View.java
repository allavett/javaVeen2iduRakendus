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

    public static enum choiceBoxes {county, city, street, houseNr}
    private ChoiceBox<String> selectCounty;
    private ChoiceBox<String> selectCity;
    private ChoiceBox<String> selectStreet;
    private ChoiceBox<String> selectHouseNr;
    private ChoiceBox<String> selectApartment;
    private String selectedCountySQL;
    private String setSelectedCitySQL;
    private String setSelectedStreetSQL;
    private String setSelectedHouseNrSQL;

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
        initSelectStreet();
        initSelectHouseNr();
        initSelectApartment();
// Layouts
        VBox layoutMain = new VBox();
        VBox layoutRegister = new VBox();
        layoutMain.getChildren().addAll(btnRegister,btnLogin,btnLogout,btnNewCounter,btnCounterHistory);
        layoutRegister.getChildren().addAll(btnBack,selectCounty,selectCity,selectStreet,selectHouseNr,selectApartment,fieldFirstName,fieldLastName,btnSubmit);
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


    }

    private void initSelectCounty(){
        selectCounty = new ChoiceBox<>(Register.getData("county", ""));

        resetToDefault(selectCounty);

        selectCounty.setOnAction(event -> {
            SelectionModel selection = selectCounty.getSelectionModel();
            changeChoiceBoxAttributes(choiceBoxes.county, selection);


            if (selection.getSelectedIndex() != 0) {
                selectedCountySQL = " WHERE county = '" + selection.getSelectedItem() + "' ";
                selectCity.setItems(Register.getData("city", selectedCountySQL));
                setState(selectCity,false);
                resetToDefault(selectCity);
            } else if (selection.getSelectedIndex() == 0) {
                resetToDefault(selectCity);
                resetToDefault(selectStreet);
                resetToDefault(selectHouseNr);
                resetToDefault(selectApartment);
                setState(selectCity, true);
                setState(selectStreet,true);
                setState(selectHouseNr, true);
                setState(selectApartment, true);
            }
        });

    }


    private void changeChoiceBoxAttributes(choiceBoxes a, SelectionModel selection){
        System.out.println(a.toString());
        switch(a){
        case county:
            selectedCountySQL = " WHERE county = '" + selection.getSelectedItem() + "' ";
            selectCity.setItems(Register.getData("city", selectedCountySQL));
            setState(selectCity,false);
            resetToDefault(selectCity);
            break;
        case city:
        case street:

        default :
            break;
        }
        switch (a){

        }
    }

    private void initSelectCity(){
        selectCity = new ChoiceBox<>(Register.getData("", ""));
        resetToDefault(selectCity);
        setState(selectCity,true);
        selectCity.setOnAction(event -> {
            System.out.println("vajutus?");
            SelectionModel selection = selectCity.getSelectionModel();
            if (selection.getSelectedIndex() != 0) {
                setSelectedCitySQL = selectedCountySQL + " AND city = '" + selection.getSelectedItem() + "'";
                selectStreet.setItems(Register.getData( "street", setSelectedCitySQL));
                setState(selectStreet,false);
                resetToDefault(selectStreet);
            } else if (selection.getSelectedIndex() == 0){
                resetToDefault(selectStreet);
                resetToDefault(selectApartment);
                setState(selectStreet, true);
                setState(selectApartment, true);
            }
        });
    }
    private void initSelectStreet(){
        selectStreet = new ChoiceBox<>(Register.getData("", ""));
        resetToDefault(selectStreet);
        setState(selectStreet,true);
        selectStreet.setOnAction(event -> {
            System.out.println("vajutus?");
            SelectionModel selection = selectStreet.getSelectionModel();
            if (selection.getSelectedIndex() != 0) {
                setSelectedStreetSQL = setSelectedCitySQL + " AND street = '" + selection.getSelectedItem() + "'";
                selectHouseNr.setItems(Register.getData( "house_nr", setSelectedStreetSQL));
                setState(selectHouseNr,false);
                resetToDefault(selectHouseNr);
            } else if (selection.getSelectedIndex() == 0){
                setState(selectHouseNr,true);
                resetToDefault(selectHouseNr);
                resetToDefault(selectApartment);
                setState(selectApartment, true);
            }
        });
    }
    private void initSelectHouseNr(){
        selectHouseNr = new ChoiceBox<>(Register.getData("", ""));
        resetToDefault(selectHouseNr);
        setState(selectHouseNr,true);
        selectHouseNr.setOnAction(event -> {
            System.out.println("vajutus?");
            SelectionModel selection = selectHouseNr.getSelectionModel();
            if (selection.getSelectedIndex() != 0) {
                setSelectedHouseNrSQL = setSelectedStreetSQL + " AND house_nr = '" + selection.getSelectedItem() + "'";
                selectApartment.setItems(Register.getData( "apartment", setSelectedHouseNrSQL));
                setState(selectApartment,false);
                resetToDefault(selectApartment);
            } else if (selection.getSelectedIndex() == 0){
                resetToDefault(selectApartment);
                setState(selectApartment, true);
            }
        });

    }
    private void initSelectApartment(){
        selectApartment = new ChoiceBox<>(Register.getData("", ""));
        resetToDefault(selectApartment);
        setState(selectApartment,true);
    }

    private void setState(ChoiceBox<String> selectCity, boolean state) {
        selectCity.setDisable(state);
    }

    private void resetToDefault(ChoiceBox<String> selectCity) {
        selectCity.getSelectionModel().select(0);
    }
}
