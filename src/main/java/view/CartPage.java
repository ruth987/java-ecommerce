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
    private JButton deleteSelectedButton;
    private JLabel totalPriceLabel;

    private Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/akecommerce";
        String username = "root";
        String password = "";

        return DriverManager.getConnection(url, username, password);
    }

    public CartPage() {
        super("Cart");

        cartService = new CartService();

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
            JOptionPane.showMessageDialog(this, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        continueShoppingButton = new JButton("Continue");
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

        deleteSelectedButton = new JButton("Delete Selected");
        deleteSelectedButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                Cart cartItem = cartItems.get(selectedRow);

                int newQuantity = cartItem.getQuantity() - 1;
                if (newQuantity <= 0) {
                    try {
                        CartService.removeItemFromCart(cartItem);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    CartService.updateCartItemQuantity(cartItem, newQuantity);
                }

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

        // Create the total price label
        totalPriceLabel = new JLabel();
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        updateTotalPriceLabel();

        // Add components to the main panel
        mainPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteSelectedButton);
        buttonPanel.add(clearCartButton);
        buttonPanel.add(buyButton);
        buttonPanel.add(continueShoppingButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalPriceLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);

        mainPanel.add(backPanel, BorderLayout.NORTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void createCartTable() {
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
                return false;
            }
        };

        cartTable = new JTable(tableModel);
        cartTable.getTableHeader().setReorderingAllowed(false);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.setRowHeight(25);

        cartTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        cartTable.setFont(new Font("Arial", Font.PLAIN, 14));
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        cartTable.setGridColor(Color.LIGHT_GRAY);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        cartTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        cartTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        cartTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        cartTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    component.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                }
                return component;
            }
        });

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        mainPanel.remove(cartTable);
        createCartTable();
        mainPanel.revalidate();
        mainPanel.repaint();
        updateTotalPriceLabel();
    }

    private void updateTotalPriceLabel() {
        double totalPrice = 0;
        for (Cart cartItem : cartItems) {
            Product product = getProduct(cartItem.getProductId());
            if (product != null) {
                totalPrice += product.getPrice() * cartItem.getQuantity();
            }
        }
        totalPriceLabel.setText("Total Price: $" + totalPrice);
    }
}
