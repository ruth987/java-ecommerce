package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {
    private Connection conn;

    public UserService(Connection conn) {
        this.conn = conn;
    }
    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO user(username, password, email) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.PreparedStatement(query);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());
        ps.executeUpdate();

    }
    public void updateUser(User user) throws SQLException {
        String query = "UPDATE user SET password = ?, email = ? WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, user.getPassword());
        ps.setString(2, user.getEmail);
        ps.setString(3, user.getUsername);
        ps.executeUpdate();

    }
    public void deleteUser(String username) throws SQLException{
        String query = "DELETE FROM user WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.executeUpdate();

    }
}
