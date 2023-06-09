package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
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
        String query = "INSERT INTO product(name, description, price, quantity, image, type, adminId) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImage());
        ps.setString(6, product.getType());
        ps.setInt(7, product.getAdminId());
        ps.executeUpdate();

        // Retrieve the product's id using other unique attributes
        String retrieveQuery = "SELECT id FROM product WHERE name = ? AND description = ? AND price = ?";
        PreparedStatement retrievePs = conn.prepareStatement(retrieveQuery);
        retrievePs.setString(1, product.getName());
        retrievePs.setString(2, product.getDescription());
        retrievePs.setDouble(3, product.getPrice());
        ResultSet resultSet = retrievePs.executeQuery();
        if (resultSet.next()) {
            int generatedId = resultSet.getInt("id");
            product.setId(generatedId);
        }
    }


    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, image = ?, type = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImage());
        ps.setString(6, product.getType());
        ps.setInt(7, product.getId());

        ps.executeUpdate();
    }

    public boolean deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM product WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, productId);
        ps.executeUpdate();
        return true;
    }

    public List<Product> displayAllProducts() throws SQLException {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String image = resultSet.getString("image");
                String type = resultSet.getString("type");
                int adminId = resultSet.getInt("adminId");

                Product product = new Product(name, description, price, quantity, image, type, adminId);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static void main(String[] args) throws SQLException {
        ProductService productService = new ProductService();
        List<Product> productList = productService.displayAllProducts();
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    public List<Product> getProductsByAdminId(int adminId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE adminId = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, adminId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Product product = new Product(
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("image"),
                    rs.getString("type"),
                    rs.getInt("adminId")) ;
            products.add(product);
        }
        return products;
    }
    public Product getProductById(String id, int adminId) {
        try {
            // Assuming you have a database connection or DAO class to fetch the product data
            // You can modify this code according to your database structure and implementation

            // Prepare your database query
            String query = "SELECT * FROM product WHERE id = ? AND adminId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, id);
            statement.setInt(2, adminId);

            // Execute the query and retrieve the result
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Retrieve the product details from the result set
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String image = resultSet.getString("image");
                String type = resultSet.getString("type");

                // Create a new Product object and populate it with the retrieved data
                Product product = new Product(name, description, price, quantity, image, type, adminId);
                product.setId(Integer.parseInt(id));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no product is found with the given ID and admin ID
    }
}
