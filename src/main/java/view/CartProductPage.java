package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class CartProductPage extends JFrame {

    private JPanel mainPanel;
    private JPanel productPanel;

    public CartProductPage(List<Product> products) {
        super("Cart Product Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        pack();
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        // Create a separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(separator.getPreferredSize().width, 2));

        // Add products to the product panel
        for (Product product : products) {
            JPanel productItemPanel = createProductItemPanel(product);
            productPanel.add(productItemPanel);
            productPanel.add(separator);
        }

        JScrollPane scrollPane = new JScrollPane(productPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createProductItemPanel(Product product) {
        JPanel productItemPanel = new JPanel(new BorderLayout());
        productItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create labels for product details
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel priceLabel = new JLabel("$" + product.getPrice());
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Create a panel for product details
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        detailsPanel.add(nameLabel);
        detailsPanel.add(priceLabel);

        // Add product details panel to the item panel
        productItemPanel.add(detailsPanel, BorderLayout.CENTER);

        return productItemPanel;
    }

    public static void main(String[] args) {
        // Sample usage
        List<Product> products = getCartProductsFromDatabase(); // Replace with your implementation to fetch cart products
        CartProductPage cartProductPage = new CartProductPage(products);
    }

    // Sample Product class
    private static class Product {
        private String name;
        private double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    // Sample method to fetch cart products from the database
    private static List<Product> getCartProductsFromDatabase() {
        // Replace with your implementation to fetch cart products from the database
        // Example:
        // Perform database queries to retrieve the cart products
        // Convert the retrieved data into Product objects
        // Return the list of Product objects
        return List.of(
                new Product("Product 1", 19.99),
                new Product("Product 2", 29.99),
                new Product("Product 3", 9.99)
        );
    }
}

