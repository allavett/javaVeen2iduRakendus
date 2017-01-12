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
    public String selectOLD(String selectItem, String conditions){
        connect();
        String result = new String();
        String sql = "SELECT " + selectItem + " FROM " + conditions;
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            result = rs.getString(selectItem);
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        return result;
    }
    public ArrayList<String> select(String item, String query){
        connect();
        ArrayList<String> result = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()){
                result.add(rs.getString(item));
            }
            System.out.println(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        return result;
    }
    // Query for distinct items from DB
    public ArrayList<String> selectDistinct(String selectItem, String conditions){
        connect();
        ArrayList<String> dataList = new ArrayList<>();
        String sql = "SELECT DISTINCT " + selectItem + " FROM " + conditions;
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
    public void insertUser(String username, String password, int address_id) {
        connect();
        String sql = "INSERT INTO users(username, password, address_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, address_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
    }
    public void insertCounter(int counter, int userId, int addressId){
        connect();
        String sql = "INSERT INTO counters (counter, date, address_id, user_id) VALUES (?, datetime('now', 'localtime'), ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, counter);
            pstmt.setInt(2, addressId);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
    }
}
