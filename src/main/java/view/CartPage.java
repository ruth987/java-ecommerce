package view;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CartPage extends JFrame {
    private User user = User.getInstance();
    private CartService cartService;
    private List<Cart> cartItems;

    private JPanel mainPanel;
    private JTable cartTable;
    private JButton backButton;
    private JButton clearCartButton;
    private JButton buyButton;
    private JButton continueShoppingButton;
    private JButton deleteSelectedButton; // Add delete button

    private Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/akecommerce";
        String username = "root";
        String password = "";

        return DriverManager.getConnection(url, username, password);
    }

    public CartPage() {
        super("Cart");

        cartService = new CartService(); // Replace 'connection' with your actual database connection

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 500));
        pack();
        setLocationRelativeTo(null);

        try {
            cartItems = cartService.getCartItems(user.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

        mainPanel = new JPanel(new BorderLayout());

        // Create the cart table
        createCartTable();

        // Create the buttons
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            ProductList productList = null;
            try {
                productList = new ProductList(User.getInstance());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setVisible(false);
            productList.setVisible(true);
        });

        clearCartButton = new JButton("Clear Cart");
        clearCartButton.addActionListener(e -> {
            // Implement the logic to clear the cart
            // For example: cartService.clearCart(user.getUserId());
            // Then refresh the cart table

            try {
                Connection conn = createConnection();
                CartService.clearCart(conn, user.getUserId());
                conn.close();
                ProductList productList = new ProductList(User.getInstance());
                setVisible(false);
                productList.setVisible(true);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            refreshCartTable();
        });

        buyButton = new JButton("Shop Now");
        buyButton.addActionListener(e -> {
            // Implement the logic to complete the purchase
            // For example: cartService.purchase(user.getUserId());
            // Then show a confirmation message to the user
            JOptionPane.showMessageDialog(this, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        continueShoppingButton = new JButton("Continue Shopping");
        continueShoppingButton.addActionListener(e -> {
            ProductList productList = null;
            try {
                productList = new ProductList(User.getInstance());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setVisible(false);
            productList.setVisible(true);
        });
        // Add delete button
        deleteSelectedButton = new JButton("Delete Selected");
        deleteSelectedButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                Cart cartItem = cartItems.get(selectedRow);

                // Decrease the quantity of the selected item
                int newQuantity = cartItem.getQuantity() - 1;
                if (newQuantity <= 0) {
                    // If the quantity becomes zero, delete the item from the cart
                    try {
                        CartService.removeItemFromCart(cartItem);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    // Update the quantity of the item in the cart
                    CartService.updateCartItemQuantity(cartItem, newQuantity);
                }

                // Refresh the cart table
                refreshCartTable();
                CartPage cartPage = new CartPage();
                setVisible(false);
                cartPage.setVisible(true);
            }
        });
        // Change button colors to blue
        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        clearCartButton.setBackground(Color.BLUE);
        clearCartButton.setForeground(Color.WHITE);
        buyButton.setBackground(Color.BLUE);
        buyButton.setForeground(Color.WHITE);
        continueShoppingButton.setBackground(Color.BLUE);
        continueShoppingButton.setForeground(Color.WHITE);
        deleteSelectedButton.setBackground(Color.BLUE);
        deleteSelectedButton.setForeground(Color.WHITE);

        // Add components to the main panel
        mainPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteSelectedButton); // Add delete button
        buttonPanel.add(clearCartButton);
        buttonPanel.add(buyButton);
        buttonPanel.add(continueShoppingButton);


        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);

        mainPanel.add(backPanel, BorderLayout.NORTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void createCartTable() {
        // Remove the delete buttons from the table
        String[] columnNames = {"Product ID", "Product Name", "Quantity", "Price"};
        Object[][] data = new Object[cartItems.size()][4];

        for (int i = 0; i < cartItems.size(); i++) {
            Cart cartItem = cartItems.get(i);
            Product product = getProduct(cartItem.getProductId());

            if (product != null) {
                data[i][0] = product.getId();
                data[i][1] = product.getName();
                data[i][2] = cartItem.getQuantity();
                data[i][3] = product.getPrice();
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the table cells non-editable
                return false;
            }
        };

        // Create the table
        cartTable = new JTable(tableModel);
        cartTable.getTableHeader().setReorderingAllowed(false);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.setRowHeight(25);

        // Set column widths
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Set table styling
        cartTable.setFont(new Font("Arial", Font.PLAIN, 14));
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        cartTable.setGridColor(Color.LIGHT_GRAY);

        // Set table alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        cartTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        cartTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        cartTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        // Enable vertical scrolling
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private Product getProduct(int productId) {
        try {
            return cartService.getProduct(productId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
        return null;
    }

    private void refreshCartTable() {
        // Refresh the cart items from the database
        try {
            cartItems = cartService.getCartItems(user.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

        // Create the cart table again
        createCartTable();

        // Repaint the main panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}