/**
 * Created by AllarVendla on 20.11.2016.
 */

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public class View extends Application {

    private static enum ChoiceBoxCases {init, county, city, street, houseNr, apartment}
    private ChoiceBox<String> selectCounty;
    private ChoiceBox<String> selectCity;
    private ChoiceBox<String> selectStreet;
    private ChoiceBox<String> selectHouseNr;
    private ChoiceBox<String> selectApartment;
    private String selectedCountySQL;
    private String selectedCitySQL;
    private String selectedStreetSQL;
    private String selectedHouseNrSQL;
    private String selectedApartmentSQL;
    private Integer selectedAddressId;

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
        TextField fieldUsername = new TextField();
        fieldUsername.setPromptText("Kasutaja");
        PasswordField fieldPassword = new PasswordField();
        fieldPassword.setPromptText("Parool");
        PasswordField fieldPasswordConfirm = new PasswordField();
        fieldPasswordConfirm.setPromptText("Parool uuesti");
// Labels
        Label lblCounty = new Label("Maakond:");
        Label lblCity = new Label("Linn:");
        Label lblStreet = new Label("Tänav:");
        Label lblHouseNr = new Label("Maja number:");
        Label lblApartment = new Label("Korteri number:");
        Label lblUsername = new Label("Kasutajanimi:");
        Label lblPassword = new Label("Parool:");
        Label lblPasswordConfirm = new Label("Parooli kinnitus:");
        Label lblError = new Label("");
// ChoiceBoxes
        initSelectCounty();
        initSelectCity();
        initSelectStreet();
        initSelectHouseNr();
        initSelectApartment();
        resetChoiceBoxValueAndState(ChoiceBoxCases.init);
// Layouts
        VBox layoutMain = new VBox();
        VBox layoutRegister = new VBox();
        layoutMain.getChildren().addAll(btnRegister,btnLogin,btnLogout,btnNewCounter,btnCounterHistory);
        layoutRegister.getChildren().addAll(btnBack,lblCounty,selectCounty,lblCity,selectCity,lblStreet,selectStreet,
                lblHouseNr,selectHouseNr,lblApartment,selectApartment,lblUsername,fieldUsername,lblPassword,
                fieldPassword,lblPasswordConfirm,fieldPasswordConfirm,btnSubmit,lblError);
// Scenes
        Scene sceneMain = new Scene(layoutMain, 640, 480);
        Scene sceneRegister = new Scene(layoutRegister, 640, 480);

// Stage actions
        primaryStage.setScene(sceneMain);
        primaryStage.show();

        btnRegister.setOnAction(event -> {
            System.out.println("Registreeri");
            primaryStage.setScene(sceneRegister);
        });
        btnSubmit.setOnAction(event -> {
            System.out.println("Saada");
            Register.checkData(fieldUsername.getText(), fieldPassword.getText(), fieldPasswordConfirm.getText(),
                    selectedAddressId);
            if (Register.getErrors().isEmpty()){
                resetChoiceBoxValueAndState(ChoiceBoxCases.init);
                fieldUsername.clear();
                fieldPassword.clear();
                fieldPasswordConfirm.clear();
                primaryStage.setScene(sceneMain);
            }
            lblError.setText(Register.getErrors());
        });
        btnBack.setOnAction(event -> {
            System.out.println("Tagasi");
            primaryStage.setScene(sceneMain);
        });
    }
// Initialize ChoiceBoxes
    private void initSelectCounty(){
        selectCounty = new ChoiceBox<>(Register.getData("county", "addresses"));
        setChoicesOnAction(selectCounty, ChoiceBoxCases.county);
    }
    private void initSelectCity(){
        selectCity = new ChoiceBox<>(Register.getData("", ""));
        setState(selectCity,true);
        setChoicesOnAction(selectCity,ChoiceBoxCases.city);
    }
    private void initSelectStreet(){
        selectStreet = new ChoiceBox<>(Register.getData("", ""));
        setState(selectStreet,true);
        setChoicesOnAction(selectStreet, ChoiceBoxCases.street);
     }
    private void initSelectHouseNr(){
        selectHouseNr = new ChoiceBox<>(Register.getData("", ""));
        setState(selectHouseNr,true);
        setChoicesOnAction(selectHouseNr, ChoiceBoxCases.houseNr);
    }
    private void initSelectApartment(){
        selectApartment = new ChoiceBox<>(Register.getData("", ""));
        resetToDefaultValue(selectApartment);
        setChoicesOnAction(selectApartment,ChoiceBoxCases.apartment);
    }
// Actions on ChoiceBox selection
    private void setChoicesOnAction(ChoiceBox<String> choiceBox, ChoiceBoxCases choiceBoxCase) {
        choiceBox.setOnAction(event -> {
            if (choiceBox.isFocused()) {
                SelectionModel selection = choiceBox.getSelectionModel();
                resetChoiceBoxValueAndState(choiceBoxCase);
                if (selection.getSelectedIndex() != 0){
                    switch (choiceBoxCase){
                        case county:
                            selectedCountySQL = "addresses WHERE county = '" + selection.getSelectedItem() + "' ";
                            selectCity.setItems(Register.getData("city", selectedCountySQL));
                            setState(selectCity,false);
                            resetToDefaultValue(selectCity);
                            break;
                        case city:
                            selectedCitySQL = selectedCountySQL + " AND city = '" + selection.getSelectedItem() + "'";
                            selectStreet.setItems(Register.getData( "street", selectedCitySQL));
                            setState(selectStreet,false);
                            resetToDefaultValue(selectStreet);
                            break;
                        case street:
                            selectedStreetSQL = selectedCitySQL + " AND street = '" + selection.getSelectedItem() + "'";
                            selectHouseNr.setItems(Register.getData( "house_nr", selectedStreetSQL));
                            setState(selectHouseNr,false);
                            resetToDefaultValue(selectHouseNr);
                            break;
                        case houseNr:
                            selectedHouseNrSQL = selectedStreetSQL + " AND house_nr = '" + selection.getSelectedItem() + "'";
                            selectApartment.setItems(Register.getData( "apartment", selectedHouseNrSQL));
                            setState(selectApartment,false);
                            resetToDefaultValue(selectApartment);
                            break;
                        case apartment:
                            selectedApartmentSQL = selectedHouseNrSQL + " AND apartment = '" + selection.getSelectedItem() + "'";
                            selectedAddressId = Register.getId("address_id", selectedApartmentSQL);
                            System.out.println("Vaade "+ selectedAddressId.toString());
                            break;
                        default:
                            System.out.println("How did you get here?!");
                    }
                }
            }
        });
    }
// Reset ChoiceBoxes
    private void resetChoiceBoxValueAndState(ChoiceBoxCases choiceBoxCase) {
        switch (choiceBoxCase){
            case init:
                resetToDefaultValue(selectCounty);
            case county:
                resetToDefaultValue(selectCity);
                setState(selectCity,true);
            case city:
                resetToDefaultValue(selectStreet);
                setState(selectStreet, true);
            case street:
                resetToDefaultValue(selectHouseNr);
                setState(selectHouseNr, true);
            case houseNr:
                resetToDefaultValue(selectApartment);
                setState(selectApartment, true);
                break;
            default:
                System.out.println("How did you get here?!");
                break;
        }
        selectedAddressId = -1;
        System.out.println("selected Adress Id " +selectedAddressId);
        System.out.println(choiceBoxCase.toString());
    }

    private void setState(ChoiceBox<String> choiceBox, boolean state) {
        choiceBox.setDisable(state);
    }

    private void resetToDefaultValue(ChoiceBox<String> choiceBox) {
        choiceBox.getSelectionModel().select(0);
    }

}
