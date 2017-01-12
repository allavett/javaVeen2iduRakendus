/**
 * Created by AllarVendla on 20.11.2016.
 */

import com.allar.kodune.ChoiceBoxCustom;
import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import javax.xml.crypto.Data;


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
    private String loggedInUser;
    private ChoiceBoxCustom testChoiceBox;
    private ChoiceBoxCustom testChoiceBox2;
    private ChoiceBoxCustom testChoiceBox3;
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
        Button btnCounterNew = new Button("Saada uus näit");
        Button btnCounterHistory = new Button("Vaata ajalugu");
        Button btnRegisterSubmit = new Button("Registreeri");
        Button btnBack = new Button("Tagasi");
// Fields
        TextField fieldUsername = new TextField();
        fieldUsername.setPromptText("Kasutaja");
        PasswordField fieldPassword = new PasswordField();
        fieldPassword.setPromptText("Parool");
        PasswordField fieldPasswordConfirm = new PasswordField();
        fieldPasswordConfirm.setPromptText("Parool uuesti");
        TextField fieldCounterNew = new TextField();
        fieldCounterNew.setPromptText("Uus veenäit:");
// Labels
        Label lblCounty = new Label("Maakond:");
        Label lblCity = new Label("Linn:");
        Label lblStreet = new Label("Tänav:");
        Label lblHouseNr = new Label("Maja number:");
        Label lblApartment = new Label("Korteri number:");
        Label lblUsername = new Label("Kasutajanimi:");
        Label lblPassword = new Label("Parool:");
        Label lblPasswordConfirm = new Label("Parooli kinnitus:");
        Label lblError = new Label();
        Label lblUser = new Label();
        Label lblCounterNew = new Label("Sisesta uus veenäit:");
// ChoiceBoxes
        initTestChoiceBoxes();

        initSelectCounty();
        initSelectCity();
        initSelectStreet();
        initSelectHouseNr();
        initSelectApartment();
        resetChoiceBoxValueAndState(ChoiceBoxCases.init);
// Layouts
        VBox layoutMain = new VBox();
        layoutMain.getChildren().addAll(testChoiceBox, testChoiceBox2,btnRegister,btnLogin);
        VBox layoutRegister = new VBox();
        VBox layoutLogin = new VBox();
        VBox layoutCounterNew = new VBox();

// Scenes
        int sceneWidth = 640;
        int sceneHeight = 500;
        Scene sceneMain = new Scene(layoutMain, sceneWidth, sceneHeight);
        Scene sceneRegister = new Scene(layoutRegister, sceneWidth, sceneHeight);
        Scene sceneLogin = new Scene(layoutLogin, sceneWidth, sceneHeight);
        Scene sceneCounterNew = new Scene(layoutCounterNew, sceneWidth, sceneHeight);

// Stage actions
        primaryStage.setScene(sceneMain);
        primaryStage.show();

        btnRegister.setOnAction(event -> {
            fieldUsername.clear();
            fieldPassword.clear();
            fieldPasswordConfirm.clear();
            layoutRegister.getChildren().clear();
            layoutRegister.getChildren().addAll(btnBack,lblCounty,selectCounty,lblCity,selectCity,lblStreet,selectStreet,
                    lblHouseNr,selectHouseNr,lblApartment,selectApartment,lblUsername,fieldUsername,lblPassword,
                    fieldPassword,lblPasswordConfirm,fieldPasswordConfirm,btnRegisterSubmit,lblError);
            System.out.println("Registreeri");
            primaryStage.setScene(sceneRegister);
        });
        btnRegisterSubmit.setOnAction(event -> {
            System.out.println("Saada");
            Register.checkData(fieldUsername.getText(), fieldPassword.getText(), fieldPasswordConfirm.getText(),
                    selectedAddressId);
            String error = Register.error.getError();
            if (error.isEmpty()){
                resetChoiceBoxValueAndState(ChoiceBoxCases.init);
                fieldUsername.clear();
                fieldPassword.clear();
                fieldPasswordConfirm.clear();
                layoutMain.getChildren().clear();
                layoutMain.getChildren().addAll(btnRegister,btnLogin);
                primaryStage.setScene(sceneMain);
            }
            lblError.setText(error);

        });
        btnLogin.setOnAction(event -> {
            if (primaryStage.getScene().equals(sceneLogin)){
                loggedInUser = Login.checkUserData(fieldUsername.getText(), fieldPassword.getText());
                String error = Login.error.getError();
                if (error.isEmpty()){
                    lblUser.setText("Tere, " + loggedInUser + " !");
                    layoutMain.getChildren().clear();
                    layoutMain.getChildren().addAll(lblUser,btnLogout,btnCounterNew,btnCounterHistory);
                    primaryStage.setScene(sceneMain);
                }
                lblError.setText(error);

            } else {
                fieldUsername.clear();
                fieldPassword.clear();
                layoutLogin.getChildren().clear();
                layoutLogin.getChildren().addAll(btnBack, lblUsername, fieldUsername, lblPassword, fieldPassword, btnLogin, lblError);
                primaryStage.setScene(sceneLogin);
            }
        });
        btnLogout.setOnAction(event -> {
            loggedInUser = "";
            lblUser.setText(loggedInUser);
            layoutMain.getChildren().clear();
            layoutMain.getChildren().addAll(btnRegister,btnLogin);
            System.out.println("Logiti välja!");
            primaryStage.setScene(sceneMain);
        });
        btnBack.setOnAction(event -> {
            System.out.println("TESTTTTT:" +lblUser.getText()+".");
            if (lblUser.getText().equals("")){

                lblError.setText("");
                layoutMain.getChildren().clear();
                layoutMain.getChildren().addAll(btnRegister,btnLogin);
                System.out.println("Tagasi");
                primaryStage.setScene(sceneMain);
            }else{
                System.out.println("Tagasi");
                layoutMain.getChildren().clear();
                layoutMain.getChildren().addAll(lblUser,btnLogout,btnCounterNew,btnCounterHistory);
                primaryStage.setScene(sceneMain);
            }

        });
        btnCounterNew.setOnAction(event -> {
            lblError.setText("");
            if (primaryStage.getScene().equals(sceneCounterNew)){
                String error = NewCounter.error.getError();
                if (!fieldCounterNew.getText().equals("")){
                    NewCounter.insertCounter(loggedInUser, Integer.parseInt(fieldCounterNew.getText()));
                    lblError.setText(error);
                } else {
                    lblError.setText("Sisestage veenäit!");
                }

            } else {
                layoutCounterNew.getChildren().clear();
                layoutCounterNew.getChildren().addAll(lblUser,btnBack, lblCounterNew, fieldCounterNew, btnCounterNew, lblError);
                System.out.println("Saada uus näit!");
                primaryStage.setScene(sceneCounterNew);
            }
        });
    }
// Initialize ChoiceBoxes
    private void  initTestChoiceBoxes(){
        testChoiceBox = new ChoiceBoxCustom("county","city", null, "addresses");
        testChoiceBox2 = new ChoiceBoxCustom("city","street", testChoiceBox, "addresses");
        testChoiceBox3 = new ChoiceBoxCustom("street",null, testChoiceBox2, "addresses");
        testChoiceBox.setSqlQuery();
        testChoiceBox2.setSqlQuery();
            System.out.println(testChoiceBox2.getPrevious().getName());
            System.out.println(testChoiceBox3.getPrevious().getName());

        Database db = new Database();
        testChoiceBox.setSetItemsWithDefaultItemAdded(db.select(testChoiceBox.getName(), testChoiceBox.getSqlQuery()), "Vali..");
        testChoiceBox2.setSetItemsWithDefaultItemAdded(db.select(testChoiceBox2.getName(), testChoiceBox2.getSqlQuery()), "Vali..");
        testChoiceBox.resetSelection();
        testChoiceBox2.resetSelection();

    }
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
