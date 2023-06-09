package view;

import model.Admin;
import model.AdminService;
import model.Product;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


public class AdminDashboard extends JFrame {
    private JLabel nameLabel;
    private JButton logoutButton;
    private JPanel optionsPanel;
    private JPanel contentPanel;
    private Admin admin;

    public AdminDashboard(String adminName) {
        this.admin = admin;
        // Set the title of the frame
        setTitle("Admin Dashboard");
        // Set the size of the frame
        setSize(800, 600);
        // Set the layout to BorderLayout
        setLayout(new BorderLayout());

        // Create the navigation bar panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(new Color(33, 97, 140));
        navBarPanel.setLayout(new BorderLayout());
        add(navBarPanel, BorderLayout.NORTH);

        // Create the name label in circular shape
        nameLabel = new JLabel(adminName.substring(0, 2).toUpperCase());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setPreferredSize(new Dimension(50, 50));
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(33, 97, 140));
        nameLabel.setBorder(BorderFactory.createLineBorder(new Color(33, 97, 140), 2));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nameLabel.setVerticalAlignment(JLabel.CENTER);
        navBarPanel.add(nameLabel, BorderLayout.WEST);

        // Create the logout button on the right side of the nav bar
        logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(33, 97, 140));
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout functionality here
                dispose();
                AdminLoginPage ad = new AdminLoginPage();
                ad.setVisible(true);
            }
        });
        navBarPanel.add(logoutButton, BorderLayout.EAST);

        // Create the options panel on the left side of the dashboard
        optionsPanel = new JPanel();
        optionsPanel.setBackground(new Color(14, 223, 230));
        optionsPanel.setLayout(new GridLayout(4, 1));
        add(optionsPanel, BorderLayout.WEST);

        // Create the "Posts" option button
        JButton postsButton = createOptionButton("Posts", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle "Posts" option functionality here
                // Display all the posts posted by the admin
                // Get the admin's ID
                int adminId = admin.getId();

                // Retrieve the products for the admin from the database
                AdminService adminService = new AdminService();
                List<Product> products = null;
                try {
                    products = adminService.getProductsByAdminId(adminId);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                // Clear the content panel
                clearContentPanel();

                if (products.isEmpty()) {
                    // Display "No Posts" text
                    JLabel noPostsLabel = new JLabel("No Posts");
                    noPostsLabel.setForeground(Color.DARK_GRAY);
                    contentPanel.add(noPostsLabel);
                } else {
                    // Display the products as card components
                    for (Product product : products) {
                        JPanel productCard = createProductCard(product);
                        contentPanel.add(productCard);
                    }
                }

                // Refresh the content panel
                refreshContentPanel();
            }
        });
        optionsPanel.add(postsButton);

        // Create the "Add new post" option button
        JButton addPostButton = createOptionButton("Add Post", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle "Add new post" option functionality here
                // Display a form for the admin to add a new product to the product table
            }
        });
        optionsPanel.add(addPostButton);

        // Create the "Delete post" option button
        JButton deletePostButton = createOptionButton("Delete Post", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle "Delete post" option functionality here
                // Allow the admin to delete a previously posted product
            }
        });
        optionsPanel.add(deletePostButton);

        // Create the "Update post" option button
        JButton updatePostButton = createOptionButton("Update Post", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle "Update post" option functionality here
                // Allow the admin to update a previously posted product
            }
        });
        optionsPanel.add(updatePostButton);

        // Create the main content panel for the dashboard
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridLayout(0, 3));

        // Create a scroll pane for the content panel
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the content panel
        add(scrollPane, BorderLayout.CENTER);

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    private JButton createOptionButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(100, 149, 237)); // Updated background color
        button.setFocusPainted(false);

        // Add radius to the button
        int borderRadius = 15;
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                BorderFactory.createEmptyBorder(borderRadius, borderRadius, borderRadius, borderRadius)
        ));

        button.addActionListener(listener);
        return button;
    }

    private void clearContentPanel() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createProductCard(Product product) {
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        cardPanel.setPreferredSize(new Dimension(200, 250));
        cardPanel.setLayout(new BorderLayout());

        // Add the necessary components and formatting for the product card

        return cardPanel;
    }

    private JPanel getContentPanel() {
        return contentPanel;
    }

    private void refreshContentPanel() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        AdminDashboard adminDashboard = new AdminDashboard("John Doe");
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }
    public Admin getAdmin() {
        return admin;
    }


}
