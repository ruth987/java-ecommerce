package view;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            ProductList productList = new ProductList();
            setVisible(false);
            productList.setVisible(true);
        });

        clearCartButton = new JButton("Clear Cart");
        clearCartButton.addActionListener(e -> {
            // Implement the logic to clear the cart
            // For example: cartService.clearCart(user.getUserId());
            // Then refresh the cart table
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
            ProductList productList = new ProductList();
            setVisible(false);
            productList.setVisible(true);
        });

        // Add components to the main panel
        mainPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
        // Create the table model
        String[] columnNames = {"Product ID", "Product Name", "Quantity", "Price", "Delete"};
        Object[][] data = new Object[cartItems.size()][5];

        for (int i = 0; i < cartItems.size(); i++) {
            Cart cartItem = cartItems.get(i);
            Product product = getProduct(cartItem.getProductId());

            if (product != null) {
                data[i][0] = product.getId();
                data[i][1] = product.getName();
                data[i][2] = cartItem.getQuantity();
                data[i][3] = product.getPrice();

                // Add a delete button for each row
                JButton deleteButton = new JButton("Delete");
                int rowIndex = i; // Store the index in a final variable for use in the ActionListener
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Implement the logic to delete the cart item
                        // For example: cartService.deleteCartItem(cartItem.getId());
                        // Then refresh the cart table
                        // made remove item from cart static
                        try {
                            CartService.removeItemFromCart(cartItem);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        refreshCartTable();
                    }
                });
                data[i][4] = deleteButton;
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
        cartTable.getColumnModel().getColumn(4).setPreferredWidth(100);

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
        cartTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

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
