package model;

import java.sql.*;

public class LoginModel {
    private Connection conn;

    public LoginModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/akecommerce", "root", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet rs = ((Statement) stmt).executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}