import com.allar.kodune.Database;

/**
 * Created by AllarVendla on 07.01.2017.
 */
public class NewCounter {
    public static Error error = new Error();
    public static void insertCounter(String username, int newCounter){
        error.setError("");
        System.out.println("Uue counteri sisestamine");
        if (newCounter <= 0) {
            error.setError("Sisestage õige näit");
        } else {
            String conditions = "counters WHERE user_id=" + getUserId(username) + " ORDER BY date DESC LIMIT 1";
            Database db = new Database();
            String previousCounter = db.selectOLD("counter", conditions);
            if (previousCounter.equals("")) { previousCounter = "0"; }
            System.out.println(previousCounter);
            //Integer previousCounter = Integer.parseInt();
            if (newCounter > Integer.parseInt(previousCounter)) {
                db.insertCounter(newCounter, getUserId(username), getAddressId(username));
                error.setError("");
            }else{
                error.setError("Eelmine näit on suurem!");
            }
        }
    }
    private static int getUserId(String username){
        String conditions = "users WHERE username = '" + username + "'";
        Database db = new Database();
        int user_id = Integer.parseInt(db.selectOLD("user_id", conditions));
        return user_id;
    }
    private static int getAddressId(String username){
        String conditions = "users WHERE username = '" + username + "'";
        Database db = new Database();
        int address_id = Integer.parseInt(db.selectOLD("address_id", conditions));
        return address_id;
    }

}
