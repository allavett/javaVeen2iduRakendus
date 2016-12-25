import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections.*;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AllarVendla on 21.12.2016.
 */
public class Database {
    Connection conn = null;
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
    public ObservableList<String> selectDistinct(String selectItem){

        connect();
        ObservableList<String> dataList = FXCollections.observableArrayList();
        //dataList.add("Vali maakond");
        //List<String> dataList = new ArrayList<String>();

        String sql = "SELECT DISTINCT " + selectItem + " FROM addresses";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("county"));
                dataList.add(rs.getString("county"));
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
