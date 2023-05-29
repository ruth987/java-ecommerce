package view;

import model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class ProductList extends JFrame {
    User user = User.getInstance();


    private CartService cartService;
    private Connection connection;

    private List<Product> productList;
    private List<JPanel> cardList;
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JScrollPane scrollPane;
    private JButton cartButton;
    private JLabel cartLabel;
    private int cartCount;
    private int updateCartLabel() {
        int cartCount = 0;
        try {
            List<Cart> cartItems = cartService.getCartItems(user.getUserId()); // Replace 'userId' with the actual user ID
            cartCount = 0;
            for (Cart cartItem : cartItems) {
                cartCount += cartItem.getQuantity();
            }
            cartLabel.setText(Integer.toString(cartCount));
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception
        }
        return cartCount;
    }


    public ProductList() {

        super("Product List");
        user.setUserId(1);
        user.setUsername("ruthwosen");
        user.setPassword("rwrwrwrw");
        user.setEmail("ruthwossen@gmail.com");
        cartService = new CartService(); // Replace 'connection' with your actual database connection

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 500));
        pack();
        setLocationRelativeTo(null);
        ProductService productService = new ProductService();

        // Retrieve the products from the database
        try {
            productList = productService.displayAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
            //Handle the exception here
        }

        cardList = new ArrayList<>();
        mainPanel = new JPanel(new BorderLayout());
        cardPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        // Create a card for each product and add it to the card panel
        for (Product product : productList) {
            JPanel card = createCard(product);
            cardList.add(card);
            cardPanel.add(card);
        }

        // Add the card panel to a scroll pane
        scrollPane = new JScrollPane(cardPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the cart button and label
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.setBackground(Color.WHITE);
        cartButton = new JButton("Cart");
        cartButton.setPreferredSize(new Dimension(100, 30));
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the cart or show a message if the cart is empty
                int cartC = updateCartLabel();
                System.out.println(cartC);
                if(cartC > 0) {
                    CartPage cartPage = new CartPage();
                    setVisible(false);
                    cartPage.setVisible(true);
                } else {
                    cartLabel.setText("Your cart is empty");
                }
            }
        });
        cartLabel = new JLabel("0");
        updateCartLabel();
        cartLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cartPanel.add(cartButton);
        cartPanel.add(cartLabel);
        mainPanel.add(cartPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createCard(Product product) {
        // Create a panel for the card
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        card.setBorder(border);

        // Create a panel for the image
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(Color.WHITE);
        JLabel imageLabel = new JLabel(product.getImage());
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imagePanel.add(imageLabel);
        card.add(imagePanel, BorderLayout.NORTH);

        // Create a panel for the name, description, price and add to cart button
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 10);
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(nameLabel, gbc);
        gbc.gridy++;
        JLabel descLabel = new JLabel(product.getDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(descLabel, gbc);
        gbc.gridy++;
        JLabel priceLabel = new JLabel("$" + product.getPrice());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(priceLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;

        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int productId = product.getId();

                boolean productExistsInCart = false;
                List<Cart> cartItems;
                try {
                    cartItems = cartService.getCartItems(user.getUserId());
                    for (Cart cartItem : cartItems) {
                        if (cartItem.getProductId() == productId) {
                            productExistsInCart = true;
                            cartItem.setQuantity(cartItem.getQuantity() + 1);
                            cartService.updateItemQuantity(user.getUserId(), productId, cartItem.getQuantity());
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle the exception
                }

                if (!productExistsInCart) {
                    Cart cartItem = new Cart(0, user.getUserId(), productId, 1);
                    try {
                        cartService.addItemToCart(cartItem);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Handle the exception
                    }
                }

                updateCartLabel();
            }
        });


        infoPanel.add(addButton, gbc);
        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

}