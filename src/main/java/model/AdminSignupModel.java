package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminSignupModel {
    private Connection conn;

    public AdminSignupModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/akecommerce", "root", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean signup(String username, String password, String email, String phone_number) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM admin WHERE username='" + username + "' AND password='" + password + "'" + "AND email='" + email + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                System.out.println("Admin already exists");
                return false;
            } else {
                PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO admin (username, password, email, phone_number) VALUES (?, ?, ?, ?)");
                stmt1.setString(1, username);
                stmt1.setString(2, password);
                stmt1.setString(3, email);
                stmt1.setString(4, phone_number);
                int rowsInserted = stmt1.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Admin registered successfully");
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
