import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Created by AllarVendla on 21.12.2016.
 */
public class Database {
    public static void connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:data/waterCounter.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed!");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

}
