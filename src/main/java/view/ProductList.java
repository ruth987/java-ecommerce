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
    private JTextField searchField;
    private JButton searchButton;

    public ProductList(User user) throws SQLException {
        this.user = user;
        this.cartService = new CartService();

        // Set the title of the frame
        setTitle("Product List");
        // Set the size of the frame
        setSize(800, 600);
        // Set the layout to border layout
        setLayout(new BorderLayout());
        // Set the background color of the frame
        setBackground(Color.LIGHT_GRAY);

        // Create and set the title label
        titleLabel = new JLabel("Product List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        //add(titleLabel, BorderLayout.NORTH);

        // Create the main panel
        mainPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Create the cart panel
        createCartPanel();

        // Create the search bar


        // Load all products initially
        loadProducts(null);

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

    private JPanel createSearchBar() {
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchBarPanel.setBackground(new Color(255, 255, 255));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(new Color(240, 240, 240)); // Set the background color of the search field

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0, 122, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
                loadProducts(searchQuery);
            }
        });

        searchBarPanel.add(Box.createHorizontalStrut(20));
        searchBarPanel.add(searchField);
        searchBarPanel.add(searchButton);
        searchBarPanel.add(Box.createHorizontalStrut(20));

        return searchBarPanel;
    }

    private void loadProducts(String searchQuery) {
        ProductService productService = new ProductService();
        List<Product> products;
        try {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                products = productService.searchProductsByName(searchQuery);
            } else {
                products = productService.displayAllProducts();
            }

            mainPanel.removeAll();
            mainPanel.revalidate();
            mainPanel.repaint();

            for (Product product : products) {
                JPanel card = createCard(product);
                mainPanel.add(card);
            }

            mainPanel.updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
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
        imageLabel.setPreferredSize(new Dimension(200, 200));
        card.add(Box.createVerticalStrut(10));
        card.add(imageLabel);

        // Create and set the product name label
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(nameLabel);

        // Create and set the product description label
        JLabel descriptionLabel = new JLabel(product.getDescription());
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(descriptionLabel);

        // Create and set the product price label
        JLabel priceLabel = new JLabel("$" + product.getPrice());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
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
                Cart cartItem = new Cart(0, user.getUserId(), product.getId(), 1);
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
        JPanel navbarPanel = new JPanel(new BorderLayout());
        navbarPanel.setBackground(new Color(0, 76, 153)); // Set the background color of the navbar

        // Create a label to display the first two letters of the username
        JLabel usernameLabel = new JLabel(user.getUserName().substring(0, 2));
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        navbarPanel.add(usernameLabel, BorderLayout.WEST);
        var searchBarPanel = createSearchBar();
        navbarPanel.add(searchBarPanel, BorderLayout.CENTER);
        // Create a button to go to the cart page and add it to the navbar panel
        JButton cartButton = new JButton("Logout");

        cartButton.setPreferredSize(new Dimension(100, 30));
        cartButton.setBackground(new Color(0, 122, 255));
        cartButton.setForeground(Color.WHITE);
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage homePage = new HomePage();
                setVisible(false);
                homePage.setVisible(true);
            }
        });
        navbarPanel.add(cartButton, BorderLayout.EAST);

        add(navbarPanel, BorderLayout.NORTH);
    }
}
