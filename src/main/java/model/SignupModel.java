package model;

import java.sql.*;

public class SignupModel {
    private Connection conn;

    public SignupModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/akecommerce", "root", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean signup(String username, String password, String email) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE username='" + username + "' AND password='" + password + "'" + "AND email='" + email + "'";
            ResultSet rs = ((Statement) stmt).executeQuery(sql);
            if (rs.next()) {
                System.out.println("User already exists");
                return false;
            } else {
                PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES (?, ?, ?)");
                stmt1.setString(1, username);
                stmt1.setString(2, password);
                stmt1.setString(3, email);
                int rowsInserted = stmt1.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("User registered successfully");
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }
}
