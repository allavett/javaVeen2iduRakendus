/**
 * Created by AllarVendla on 20.11.2016.
 */

import com.allar.kodune.ChoiceBoxCustom;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public class View extends Application {

    private String loggedInUser;
    private ChoiceBoxCustom selectCounty;
    private ChoiceBoxCustom selectCity;
    private ChoiceBoxCustom selectStreet;
    private ChoiceBoxCustom selectHouseNr;
    private ChoiceBoxCustom selectApartment;
    private int selectedAddressId;
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
        Label lblLoggedInUser = new Label();
        Label lblCounterNew = new Label("Sisesta uus veenäit:");
// ChoiceBoxes
        initTestChoiceBoxes();
// Layouts
        VBox layoutMain = new VBox();
        layoutMain.getChildren().addAll(btnRegister,btnLogin);
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
            selectedAddressId = Register.getAddressId(selectApartment);
            Register.checkData(fieldUsername.getText(), fieldPassword.getText(), fieldPasswordConfirm.getText(),
                    selectedAddressId);
            String error = Register.error.getError();
            if (error.isEmpty()){
                //resetChoiceBoxValueAndState(ChoiceBoxCases.init);
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
                    lblLoggedInUser.setText("Tere, " + loggedInUser + " !");
                    layoutMain.getChildren().clear();
                    layoutMain.getChildren().addAll(lblLoggedInUser,btnLogout,btnCounterNew,btnCounterHistory);
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
            lblLoggedInUser.setText(loggedInUser);
            layoutMain.getChildren().clear();
            layoutMain.getChildren().addAll(btnRegister,btnLogin);
            System.out.println("Logiti välja!");
            primaryStage.setScene(sceneMain);
        });
        btnBack.setOnAction(event -> {
            System.out.println("TESTTTTT:" +lblLoggedInUser.getText()+".");
            if (lblLoggedInUser.getText().equals("")){

                lblError.setText("");
                layoutMain.getChildren().clear();
                layoutMain.getChildren().addAll(btnRegister,btnLogin);
                System.out.println("Tagasi");
                primaryStage.setScene(sceneMain);
            }else{
                System.out.println("Tagasi");
                layoutMain.getChildren().clear();
                layoutMain.getChildren().addAll(lblLoggedInUser,btnLogout,btnCounterNew,btnCounterHistory);
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
                layoutCounterNew.getChildren().addAll(lblLoggedInUser,btnBack, lblCounterNew, fieldCounterNew, btnCounterNew, lblError);
                System.out.println("Saada uus näit!");
                primaryStage.setScene(sceneCounterNew);
            }
        });
    }
// Initialize ChoiceBoxes
    private void  initTestChoiceBoxes(){
        selectApartment = new ChoiceBoxCustom("apartment",null,"addresses",true);
        selectHouseNr = new ChoiceBoxCustom("house_nr", selectApartment,"addresses", true);
        selectStreet = new ChoiceBoxCustom("street",selectHouseNr, "addresses", true);
        selectCity = new ChoiceBoxCustom("city", selectStreet, "addresses", true);
        selectCounty = new ChoiceBoxCustom("county", selectCity, "addresses", false);
    }
}
