package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
public class ProductList extends JFrame {
    private JLabel titleLabel, cartLabel;
    private JPanel mainPanel;
    private JButton cartButton;
    private User user;
    private CartService cartService;

    public ProductList(User user) throws SQLException {
        this.user = user;
        this.cartService = new CartService();

        // Set the title of the frame
        setTitle("Product List");
        // Set the size of the frame
        setSize(600, 600);
        // Set the layout to border layout
        setLayout(new BorderLayout());
        // Set the background color of the frame
        setBackground(Color.LIGHT_GRAY);

        // Create and set the title label
        titleLabel = new JLabel("Product List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Create the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Create the cart panel
        createCartPanel();

        // Load the products and create the cards
        loadProducts();

        // Create the navbar panel
        createNavBarPanel();
    }

    private void createCartPanel() {
        // Create a panel for the cart button and label
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.setBackground(Color.WHITE);
        cartButton = new JButton("Cart");
        cartButton.setPreferredSize(new Dimension(100, 30));
        cartButton.setBackground(new Color(0, 122, 255));
        cartButton.setForeground(Color.WHITE);
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the cart or show a message if the cart is empty
                int cartCount = cartService.getCartItemCount(user.getUserId());
                System.out.println(cartCount);
                if(cartCount > 0) {
                    CartPage cartPage = new CartPage();
                    setVisible(false);
                    cartPage.setVisible(true);
                } else {
                    cartLabel.setText("Your cart is empty");
                }
            }
        });
        cartLabel = new JLabel(String.valueOf(cartService.getCartItemCount(user.getUserId())));
        cartLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cartPanel.add(cartButton);
        cartPanel.add(cartLabel);
        add(cartPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() throws SQLException {
        ProductService productService = new ProductService();
        List<Product> products = productService.displayAllProducts();
        for(Product product : products) {
            JPanel card = createCard(product);
            mainPanel.add(card);
        }
    }

    private JPanel createCard(Product product) {
        // Create a panel for the card
        JPanel card = new JPanel();
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Create and set the product image label
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(product.getImage());
        imageLabel.setIcon(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setPreferredSize(new Dimension(400, 200));
        imageLabel.setMaximumSize(new Dimension(400, 200));
        card.add(Box.createVerticalStrut(10));
        card.add(imageLabel);

        // Create and set the product name label
        JLabel nameLabel = new JLabel(product.getProductName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(nameLabel);

        // Create and set the product description label
        JLabel descriptionLabel = new JLabel(product.getProductDescription());
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(descriptionLabel);

        // Create and set the product price label
        JLabel priceLabel = new JLabel("$" + product.getProductPrice());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(priceLabel);

        // Create a button to add the product to the cart and add it to the card
        JButton addButton = new JButton("Add to Cart");
        addButton.setBackground(new Color(0, 122, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cart cartItem = new Cart(0, user.getUserId(), (Integer)product.getProductId(), 1);
                try {
                    cartService.addItemToCart(cartItem);
                    int cartCount = cartService.getCartItemCount(user.getUserId());
                    cartLabel.setText(String.valueOf(cartCount));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle the exception
                }
            }
        });
        card.add(Box.createVerticalStrut(5));
        card.add(addButton);

        return card;
    }

    private void createNavBarPanel() {
        // Create a panel for the navbar
        JPanel navbarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navbarPanel.setBackground(Color.WHITE);

        // Create a button to go to the cart page and add it to the navbar panel
        JButton cartButton = new JButton("Logout");

        cartButton.setPreferredSize(new Dimension(100, 30));
        cartButton.setBackground(new Color(0, 122, 255));
        cartButton.setForeground(Color.WHITE);
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                setVisible(false);
                loginPage.setVisible(true);
            }
        });
        navbarPanel.add(cartButton);

        add(navbarPanel, BorderLayout.NORTH);
    }
}