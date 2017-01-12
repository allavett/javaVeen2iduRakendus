/**
 * Created by AllarVendla on 07.01.2017.
 */
public class Login {
    public static Error error = new Error();
    public static String checkUserData(String username, String password){
        error.setError("");
        String conditions = "users WHERE username = '" + username + "' AND password = '" + password + "'";
        Database db = new Database();
        String dbResponse = db.selectOLD("username", conditions);
        if (dbResponse.isEmpty()){
            error.setError("Kasutajanimi ja/või parool on vale!");
        } else if (dbResponse.equals(username) && !dbResponse.isEmpty()){
            System.out.println("Kasutaja sisse logitud!");
            return dbResponse;
        }
        System.out.println("LoginResponse:"+ dbResponse + "..");
        return dbResponse;
    }
}
