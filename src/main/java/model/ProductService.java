package model;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    // handles requests related to products
    private Connection conn;

    public ProductService() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/akecommerce", "root", "");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO product(name, description, price, quantity, image) VALUES(?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImage());
        ps.executeUpdate();
    }
    public void updateProduct(Product product) throws SQLException{
        String query = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, image = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImage());
//        how can i update using an auto generated id ?
//        ps.setInt(6, product.getId());

        ps.executeUpdate();
    }
    public void deleteProduct(int productId) throws SQLException{
        String query = "DELETE FROM product WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, productId);
        ps.executeUpdate();
    }
    public List<Product> displayAllProducts() throws SQLException {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String image =resultSet.getString("image");
                Product product = new Product(name, description, price, quantity, image);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //Handle the exception here
        }

        return productList;
    }

    public static void main(String[] args) throws SQLException {
        ProductService pt = new ProductService();
        var productList = pt.displayAllProducts();
    }

}
