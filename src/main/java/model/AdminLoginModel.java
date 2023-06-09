package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminLoginModel {
    private Connection conn;

    public AdminLoginModel() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/akecommerce", "root", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        try {
            if (password == null || password.isEmpty()) {
                return false;
            }
            if (username == null || username.isEmpty()) {
                return false;
            }
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM admin WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Admin admin = Admin.getInstance();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setEmail(rs.getString("email"));
                admin.setPhone_number(rs.getString("phone_number"));
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
