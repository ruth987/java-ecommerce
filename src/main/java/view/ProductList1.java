package view;

import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ProductList1 extends JFrame {
    User user = User.getInstance();

    private CartService cartService;
    private Connection connection;

    private List<Product> productList;
    private List<JPanel> cardList;
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JScrollPane scrollPane;
    private MaterialButton cartButton;
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


    public ProductList1() {

        super("Product List");
        user.setUserId(1);
        user.setUsername("ruthwosen");
        user.setPassword("rwrwrwrw");
        user.setEmail("ruthwossen@gmail.com");
        cartService = new CartService(); // Replace 'connection' with your actual database connection
        setLayout(new BorderLayout());
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
        
           JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(33, 150, 243));
    JLabel userLabel = new JLabel(user.getUsername().substring(0, 2).toUpperCase());
    userLabel.setFont(new Font("Roboto", Font.BOLD, 16));
    userLabel.setForeground(Color.WHITE);
    headerPanel.add(userLabel, BorderLayout.EAST);
    JLabel productLabel = new JLabel("Ecommerce");
    productLabel.setFont(new Font("Roboto", Font.BOLD, 24));
    productLabel.setForeground(Color.WHITE);
    headerPanel.add(productLabel, BorderLayout.WEST);
    int paddingSize = 10;
Border paddingBorder = BorderFactory.createEmptyBorder(paddingSize, paddingSize, paddingSize, paddingSize);
headerPanel.setBorder(paddingBorder);
   
        cardList = new ArrayList<>();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel,BorderLayout.NORTH);
        cardPanel = new JPanel(new GridLayout(productList.size() / 3 + 1, 3, 10, 10));

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
        cartButton = new MaterialButton("Cart");
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
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    card.setPreferredSize(new Dimension(200, 300));
    card.setMaximumSize(new Dimension(200, 300));
    card.setMinimumSize(new Dimension(200, 300));
    card.setCursor(new Cursor(Cursor.HAND_CURSOR));
    card.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Show the product details dialog

        }
    });

    // Create a split pane for the image and text
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setDividerLocation(150);
    splitPane.setDividerSize(0);
    splitPane.setEnabled(false);
    card.add(splitPane, BorderLayout.CENTER);

    // Add the image to the top of the split pane
    JLabel imageLabel = new JLabel(new ImageIcon(product.getImage()));
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    splitPane.setTopComponent(imageLabel);

    // Add a panel with the text to the bottom of the split pane
    JPanel textPanel = new JPanel(new BorderLayout());
    textPanel.setBackground(Color.WHITE);
    textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    splitPane.setBottomComponent(textPanel);

    // Add the product name to the top of the text panel
    JLabel nameLabel = new JLabel(product.getName());
    nameLabel.setFont(new Font("Roboto", Font.BOLD, 14));
    textPanel.add(nameLabel, BorderLayout.NORTH);

    // Add the product price and description to the center of the text panel
    JLabel priceLabel = new JLabel("$" + product.getPrice());
    priceLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
    textPanel.add(priceLabel, BorderLayout.WEST);
    JTextArea descriptionArea = new JTextArea(product.getDescription());
    descriptionArea.setFont(new Font("Roboto", Font.PLAIN, 12));
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setEditable(false);
    descriptionArea.setFocusable(false);
    textPanel.add(descriptionArea, BorderLayout.CENTER);

    // Add a shadow border to the card
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    return card;
}

    public static void main(String[] args) {
        var product = new ProductList1();
        product.setVisible(true);
    }
}
class
MaterialButton extends JButton {

    public MaterialButton(String text) {
        super(text);
        setOpaque(false);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setForeground(Color.WHITE);
        setBackground(new Color(33, 150, 243));
        setFont(new Font("Roboto", Font.PLAIN, 12));
         // smaller preferred size
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
        g2.dispose();
    }
}