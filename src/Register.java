import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
 * Created by AllarVendla on 11.12.2016.
 */

public class Register {
    public static void Register() {
        //View;
        System.out.println("Register klassi konstruktor");
    }

    public static String checkData(String fName, String lName){
        String errorText = "";
        errorText = checkName(fName) + checkName(lName);
        return errorText;
    }
    private static String checkName(String name){
        String errorText = "";
        if(name.length() < 2 || name.equals(""))
        {
            errorText = "Probleem nimega: " + name;
        }
        return errorText;
    }
    public static ObservableList<String> getData(String selectItem){
        Database db = new Database();
        ArrayList<String> dataList = new ArrayList<>(db.selectDistinct(selectItem, conditions));
        dataList.add(0, "Vali..");
        ObservableList<String> selectItems = FXCollections.observableList(dataList);
        return selectItems;
    }
    public static String conditions = "";
    public static void setConditions(String newCondition){
        if (conditions == "") {
            conditions = "WHERE " + newCondition;
        } else {
            conditions = conditions + " AND " + newCondition;
        }
    }
    public static void clearConditions() {
        conditions = "";
    }
}
