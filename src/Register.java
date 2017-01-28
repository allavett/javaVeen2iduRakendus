import com.allar.kodune.ChoiceBoxCustom;
import com.allar.kodune.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
 * Created by AllarVendla on 11.12.2016.
 */

public class Register {

// Check registration data

    public static Error error = new Error();

    public static int getAddressId(ChoiceBoxCustom apartment){
        int result = -1;
        if (apartment != null && apartment.getSelectionModel().getSelectedIndex() > 0 && apartment.getSqlQueryCondition() != null && !apartment.getSqlQueryCondition().contains("null")) {
            String sqlCondition = "addresses " + apartment.getSqlQueryCondition();
            Database db = new Database();
            result = Integer.parseInt(db.selectOLD("address_id", sqlCondition));
        }
        System.out.println(result);
        return result;
    }
    public static void checkData(String username, String password, String passwordConfirm, Integer address_id){
        error.setError("");
        if (address_id < 1){
            error.setError("Kontrolli aadressi!");
        } else {

            checkName(username);
            if (error.getError().isEmpty()) {
                checkPassword(password, passwordConfirm);
            }
            if (error.getError().isEmpty()) {
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
            error.setError("Kasutajanimi liiga lühike!");
        } else {
            ArrayList<String> users = new ArrayList<>();
            Database db = new Database();
            users.addAll(db.selectDistinct("username", "users"));
            for (String user : users){
                System.out.println(user);
                if (username.equals(user)){
                    error.setError("Selline kasutaja juba olemas!");
                }
            }
        }
    }
// Check inserted password
    private static void checkPassword(String password, String passwordConfirm){
        if(password.length() < 6){
            error.setError("Parool liiga lühike!");
        } else if(!password.equals(passwordConfirm)){
            error.setError("Parool ei klapi!");
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
            id = db.selectOLD(selectItem, condition);
        }
        System.out.println("Register row id" + id);
        return Integer.parseInt(id);
}

}