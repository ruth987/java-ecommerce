package Services;

import Data.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductService {
    // handles requests related to products
    private Connection conn;

    public ProductService(Connection conn) {
        this.conn = conn;
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

}
