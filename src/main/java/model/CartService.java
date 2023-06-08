package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private static Connection conn;
    public CartService() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/akecommerce", "root", "");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void addItemToCart(Cart cart) throws SQLException {
        String query = "SELECT * FROM cart WHERE user_id = ? AND product_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Product already exists in cart, update quantity
            int existingQuantity = rs.getInt("quantity");
            int newQuantity = existingQuantity + cart.getQuantity();
            updateItemQuantity(cart.getUserId(), cart.getProductId(), newQuantity);
        } else {
            // Product does not exist in cart, add new item
            String insertQuery = "INSERT INTO cart(user_id, product_id, quantity) VALUES(?,?,?)";
            PreparedStatement insertPs = conn.prepareStatement(insertQuery);
            System.out.println("cart.getUserId() = " + cart.getUserId());
            System.out.println("cart.getProductId() = " + cart.getProductId());
            insertPs.setInt(1, cart.getUserId());
            insertPs.setInt(2, cart.getProductId());
            insertPs.setInt(3, cart.getQuantity());
            insertPs.executeUpdate();
        }
    }

    public static void removeItemFromCart(Cart cart) throws SQLException {
        String query = "SELECT * FROM cart WHERE user_id = ? AND product_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int existingQuantity = rs.getInt("quantity");
            if (existingQuantity > 1) {
                // If quantity is more than 1, decrease the quantity
                int newQuantity = existingQuantity - 1;
                updateItemQuantity(cart.getUserId(), cart.getProductId(), newQuantity);
            } else {
                // If quantity is 1, remove the item from cart
                String deleteQuery = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
                PreparedStatement deletePs = conn.prepareStatement(deleteQuery);
                deletePs.setInt(1, cart.getUserId());
                deletePs.setInt(2, cart.getProductId());
                deletePs.executeUpdate();
            }
        }
    }

    public static void updateItemQuantity(int userId, int productId, int quantity) throws SQLException {
        String query = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, quantity);
        ps.setInt(2, userId);
        ps.setInt(3, productId);
        ps.executeUpdate();
    }

    public List<Cart> getCartItems(int userId) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Cart cart = new Cart(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("product_id"), rs.getInt("quantity"));
            cartItems.add(cart);
        }
        return cartItems;
    }

    public static void clearCart(Connection conn, int userId) throws SQLException {
        String query = "DELETE FROM cart WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        ps.executeUpdate();
    }

    public double calculateTotal(List<Cart> cartItems) throws SQLException {
        double total = 0.0;
        for (Cart cart : cartItems) {
            String query = "SELECT price FROM product WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, cart.getProductId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double price = rs.getDouble("price");
                total += price * cart.getQuantity();
            }
        }
        return total;
    }
    public Product getProduct(int productId) throws SQLException {
        String query = "SELECT * FROM product WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, productId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Retrieve the product details
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String image = rs.getString("image");
            // Create and return the Product object
            return new Product(id, name, description, price, quantity, image);
        }

        // If no product found, return null or throw an exception
        return null;
    }


    public int getCartItemCount(int userId) {
        int count = 0;
        try {
            String query = "SELECT SUM(quantity) AS count FROM cart WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return count;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT * FROM product";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("image");
                Product product = new Product(id, name, description, price, quantity, image);
                products.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        return products;
    }
}
