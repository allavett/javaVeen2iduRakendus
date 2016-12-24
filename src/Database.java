import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Created by AllarVendla on 21.12.2016.
 */
public class Database {
    // Connect with DB
    private Connection connect() {
        Connection conn = null;
        String url = "jdbc:sqlite:data/waterCounter.db";
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    // Disconnect DB
    public void disconnect() {
        try (Connection conn = this.connect()){
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Query from DB
    public void selectCounty(){
        String sql = "SELECT DISTINCT county FROM addresses";
        try (Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getString("county"));
                selectCounty();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Insert into DB
    public void insert(String username, String password, Integer address_id) {
        String sql = "INSERT INTO users(username, password, address_id) VALUES (?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, address_id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void databaseActions() {
        Database db = new Database();
        db.selectCounty();
    }
}
