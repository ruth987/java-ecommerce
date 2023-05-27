package model;

import model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private Connection conn;

    public CartService(Connection conn) {
        this.conn = conn;
    }

    public void addItemToCart(Cart cart) throws SQLException {
        String query = "INSERT INTO cart(user_id, product_id, quantity) VALUES(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ps.setInt(3, cart.getQuantity());
        ps.executeUpdate();
    }
    public void removeItemFromCart(Cart cart) throws SQLException {
        String query = "DELETE FROM cart WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cart.getId());
        ps.executeUpdate();
    }
    public void updateItemQuantity(Cart cart) throws SQLException {
        String query = "UPDATE cart SET quantity = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cart.getQuantity());
        ps.setInt(2, cart.getId());
        ps.executeUpdate();
    }

    public List<Cart> getCartItems(int userId) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        String query = "SELECT * FROM cart WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Cart cart = new Cart(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("product_id"), rs.getInt("quantity"));
            cartItems.add(cart);
        }
        return cartItems;

    }
    public void clearCart(int userId) throws SQLException {
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



}
