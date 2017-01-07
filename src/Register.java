import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
 * Created by AllarVendla on 11.12.2016.
 */

public class Register {
    /*
    public static void register() {
        System.out.println("Register klassi konstruktor");
    }*/
// Errors on registration form
    private static String errors;
    private static void setErrors(String err){
        errors = err;
    }
    public static String getErrors(){
        return errors;
    }
// Check registration data
    public static void checkData(String username, String password, String passwordConfirm, Integer address_id){
        errors = "";
        if (address_id < 1){
            setErrors("Kontrolli aadressi!");
        } else {
            checkName(username);
            if (errors.isEmpty()) {
                checkPassword(password, passwordConfirm);
            }
            if (errors.isEmpty()) {
                System.out.println("Andmed õiged!");
                Database db = new Database();
                db.insertUser(username, password, address_id);
                System.out.println("Kasutaja loodud!");
            }
        }
    }
// Check inserted username
    private static void checkName(String username){
        if(username.length() < 4 || username.equals(""))
        {
            setErrors("Kasutajanimi liiga lühike!");
        } else {
            ArrayList<String> users = new ArrayList<>();
            Database db = new Database();
            users.addAll(db.selectDistinct("username", "users"));
            for (String user : users){
                System.out.println(user);
                if (username.equals(user)){
                    setErrors("Selline kasutaja juba olemas!");
                }
            }
        }
    }
// Check inserted password
    private static void checkPassword(String password, String passwordConfirm){
        if(password.length() < 6){
            setErrors("Parool liiga lühike!");
        } else if(!password.equals(passwordConfirm)){
            setErrors("Parool ei klapi!");
        }
    }
// Fill ChoiceBoxes with items
    public static ObservableList<String> getData(String selectItem, String condition){
        ArrayList<String> dataList = new ArrayList<>();
        if (!selectItem.isEmpty() && !condition.isEmpty()) {
            Database db = new Database();
            dataList = db.selectDistinct(selectItem, condition);
        }
        dataList.add(0, "Vali.."); // Choicebox default value
        ObservableList<String> selectItems = FXCollections.observableList(dataList);
        return selectItems;
    }
// Get row Id
    public static Integer getId(String selectItem, String condition){
        String id = new String();
        if (!selectItem.isEmpty() && !condition.isEmpty()) {
            Database db = new Database();
            id = db.select(selectItem, condition);
        }
        System.out.println("Register row id" + id);
        return Integer.parseInt(id);
}

}