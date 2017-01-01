import java.sql.*;
import java.util.ArrayList;

/**
 * Created by AllarVendla on 21.12.2016.
 */
public class Database {
    private Connection conn = null;
    // Connect with DB
    private void connect() {
        String url = "jdbc:sqlite:data/waterCounter.db";
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Disconnect DB
    private void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Query from DB
    public ArrayList<String> selectDistinct(String selectItem, String conditions){
        connect();
        ArrayList<String> dataList = new ArrayList<>();
        String sql = "SELECT DISTINCT " + selectItem + " FROM addresses " + conditions;
        System.out.println(sql);
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dataList.add(rs.getString(selectItem));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        return dataList;
    }
    // Insert into DB
    public void insert(String username, String password, Integer address_id) {
        String sql = "INSERT INTO users(username, password, address_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, address_id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
