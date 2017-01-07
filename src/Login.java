/**
 * Created by AllarVendla on 07.01.2017.
 */
public class Login {
    private static String errors;
    private static void setErrors(String err){
        errors = err;
    }
    public static String getErrors(){
        return errors;
    }
    public static String checkUserData(String username, String password){
        setErrors("");
        String conditions = "users WHERE username = '" + username + "' AND password = '" + password + "'";
        Database db = new Database();
        String dbResponse = db.select("username", conditions);
        if (dbResponse.isEmpty()){
            setErrors("Kasutajanimi ja/või parool on vale!");
        } else if (dbResponse.equals(username)){
            System.out.println("Kasutaja sisse logitud!");
            return dbResponse;
        }
        System.out.println("LoginResponse:"+ dbResponse + "..");
        return dbResponse;
    }
}
