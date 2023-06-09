package model;

import model.Admin;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private static Connection conn;
    private static AdminService instance;

    public AdminService() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/akecommerce", "root", "");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static AdminService getInstance() {
        if (instance == null) {
            synchronized (AdminService.class) {
                if (instance == null) {
                    instance = new AdminService();
                }
            }
        }
        return instance;
    }

    public void addAdmin(Admin admin) throws SQLException {
        String query = "INSERT INTO admin (username, password, email, phone_number) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, admin.getUsername());
        ps.setString(2, admin.getPassword());
        ps.setString(3, admin.getEmail());
        ps.setString(4, admin.getPhone_number());
        ps.executeUpdate();
    }

    public void deleteAdmin(int adminId) throws SQLException {
        String query = "DELETE FROM admin WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, adminId);
        ps.executeUpdate();
    }

    public void updateAdmin(Admin admin) throws SQLException {
        String query = "UPDATE admin SET username = ?, password = ?, email = ?, phone_number = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, admin.getUsername());
        ps.setString(2, admin.getPassword());
        ps.setString(3, admin.getEmail());
        ps.setString(4, admin.getPhone_number());
        ps.setInt(5, admin.getId());
        ps.executeUpdate();
    }

    public Admin getAdminById(int adminId) throws SQLException {
        String query = "SELECT * FROM admin WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, adminId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Admin admin = Admin.getInstance();
            admin.setId(rs.getInt("id"));
            admin.setUsername(rs.getString("username"));
            admin.setPassword(rs.getString("password"));
            admin.setEmail(rs.getString("email"));
            admin.setPhone_number(rs.getString("phone_number"));
            return admin;
        }
        return null;
    }

    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admin";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Admin admin = Admin.getInstance();
            admin.setId(rs.getInt("id"));
            admin.setUsername(rs.getString("username"));
            admin.setPassword(rs.getString("password"));
            admin.setEmail(rs.getString("email"));
            admin.setPhone_number(rs.getString("phone_number"));
            admins.add(admin);
        }
        return admins;
    }

    public List<Product> getProductsByAdminId(int adminId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE admin_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, adminId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("image")
            );
            products.add(product);
        }
        return products;
    }

    public void updateProductByAdminId(Product product) throws SQLException {
        String query = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, image = ? WHERE id = ? AND admin_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImage());
        ps.setInt(6, product.getId());
        ps.setInt(7, product.getAdminId());
        ps.executeUpdate();
    }

    public void deleteProductByAdminId(int productId, int adminId) throws SQLException {
        String query = "DELETE FROM product WHERE id = ? AND admin_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, productId);
        ps.setInt(2, adminId);
        ps.executeUpdate();
    }

    public void addProductByAdminId(Product product, int adminId) throws SQLException {
        String query = "INSERT INTO product (name, description, price, quantity, image, admin_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImage());
        ps.setInt(6, adminId);
        ps.executeUpdate();
    }
}
