package view;
import model.Admin;
import model.Product;
import model.ProductService;

import javax.swing.*;
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
    private ProductService productService;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        this.productService = new ProductService();
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(new Color(33, 97, 140));
        navBarPanel.setLayout(new BorderLayout());
        add(navBarPanel, BorderLayout.NORTH);

        nameLabel = new JLabel(admin.getUsername().substring(0, 2).toUpperCase());
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

        logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(33, 97, 140));
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminLoginPage ad = new AdminLoginPage();
                ad.setVisible(true);
            }
        });
        navBarPanel.add(logoutButton, BorderLayout.EAST);

        optionsPanel = new JPanel();
        optionsPanel.setBackground(new Color(200, 200, 200));
        optionsPanel.setLayout(new GridLayout(4, 1));
        add(optionsPanel, BorderLayout.WEST);

        JButton postsButton = createOptionButton("Posts", e -> showPostsOption());
        optionsPanel.add(postsButton);

        JButton addPostButton = createOptionButton("Add Post", e -> showAddPostOption());
        optionsPanel.add(addPostButton);

        JButton deletePostButton = createOptionButton("Delete Post", e -> showDeletePostOption());
        optionsPanel.add(deletePostButton);

        JButton updatePostButton = createOptionButton("Update Post", e -> showUpdatePostOption());
        optionsPanel.add(updatePostButton);

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        showPostsOption();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JButton createOptionButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 122, 204));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    public void showPostsOption() {
        contentPanel.removeAll();
        List<Product> products = null;
        try {
            products = productService.getProductsByAdminId(admin.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (products.isEmpty()) {
            JLabel noPostsLabel = new JLabel("NO POSTS YET");
            noPostsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(noPostsLabel, BorderLayout.CENTER);
        } else {
            JPanel postsPanel = new JPanel();
            postsPanel.setLayout(new GridLayout(0, 3, 10, 10));

            for (Product product : products) {
                JPanel cardPanel = createProductCard(product);
                postsPanel.add(cardPanel);
            }

            JScrollPane scrollPane = new JScrollPane(postsPanel);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createProductCard(Product product) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel imageLabel = new JLabel(new ImageIcon(product.getImage()));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel priceLabel = new JLabel("Price: $" + product.getPrice());
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel quantityLabel = new JLabel("Quantity: " + product.getQuantity());
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        detailsPanel.add(priceLabel);
        detailsPanel.add(quantityLabel);

        cardPanel.add(nameLabel, BorderLayout.NORTH);
        cardPanel.add(imageLabel, BorderLayout.CENTER);
        cardPanel.add(detailsPanel, BorderLayout.SOUTH);

        return cardPanel;
    }

    public void showAddPostOption() {
        contentPanel.removeAll();

        JPanel addPostPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        addPostPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("ID:");
        JTextField idTextField = new JTextField();
        addPostPanel.add(idLabel);
        addPostPanel.add(idTextField);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField();
        addPostPanel.add(nameLabel);
        addPostPanel.add(nameTextField);

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionTextField = new JTextField();
        addPostPanel.add(descriptionLabel);
        addPostPanel.add(descriptionTextField);

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceTextField = new JTextField();
        addPostPanel.add(priceLabel);
        addPostPanel.add(priceTextField);

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityTextField = new JTextField();
        addPostPanel.add(quantityLabel);
        addPostPanel.add(quantityTextField);

        JLabel imageLabel = new JLabel("Image:");
        JTextField imageTextField = new JTextField();
        addPostPanel.add(imageLabel);
        addPostPanel.add(imageTextField);

        JLabel typeLabel = new JLabel("Type:");
        JTextField typeTextField = new JTextField();
        addPostPanel.add(typeLabel);
        addPostPanel.add(typeTextField);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(0, 122, 204));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                String name = nameTextField.getText();
                String description = descriptionTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());
                String image = imageTextField.getText();
                String type = typeTextField.getText();

                Product newProduct = new Product(name, description, price, quantity, image, type);
                try {
                    productService.addProduct(newProduct);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(AdminDashboard.this, "Product added successfully!");

                // Clear the input fields
                idTextField.setText("");
                nameTextField.setText("");
                descriptionTextField.setText("");
                priceTextField.setText("");
                quantityTextField.setText("");
                imageTextField.setText("");
                typeTextField.setText("");
            }
        });
        addPostPanel.add(addButton);

        contentPanel.add(addPostPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showDeletePostOption() {
        contentPanel.removeAll();

        JPanel deletePostPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        deletePostPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("Product ID:");
        JTextField idTextField = new JTextField();
        deletePostPanel.add(idLabel);
        deletePostPanel.add(idTextField);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(0, 122, 204));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                boolean deleted = false;
                try {
                    deleted = productService.deleteProduct(Integer.parseInt(id));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (deleted) {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "Product deleted successfully!");
                    idTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "No product found with that ID.");
                }
            }
        });
        deletePostPanel.add(deleteButton);

        contentPanel.add(deletePostPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showUpdatePostOption() {
        contentPanel.removeAll();

        JPanel updatePostPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        updatePostPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("Product ID:");
        JTextField idTextField = new JTextField();
        updatePostPanel.add(idLabel);
        updatePostPanel.add(idTextField);

        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(0, 122, 204));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                Product product = productService.getProductById(id, admin.getId());

                if (product == null) {
                    JOptionPane.showMessageDialog(AdminDashboard.this, "No product found with that ID.");
                } else {
                    showUpdateProductForm(product);
                }
            }
        });
        updatePostPanel.add(updateButton);

        contentPanel.add(updatePostPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showUpdateProductForm(Product product) {
        contentPanel.removeAll();

        JPanel updateFormPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        updateFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("ID:");
        JTextField idTextField = new JTextField(product.getId());
        idTextField.setEditable(false);
        updateFormPanel.add(idLabel);
        updateFormPanel.add(idTextField);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField(product.getName());
        updateFormPanel.add(nameLabel);
        updateFormPanel.add(nameTextField);

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionTextField = new JTextField(product.getDescription());
        updateFormPanel.add(descriptionLabel);
        updateFormPanel.add(descriptionTextField);

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceTextField = new JTextField(String.valueOf(product.getPrice()));
        updateFormPanel.add(priceLabel);
        updateFormPanel.add(priceTextField);

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityTextField = new JTextField(String.valueOf(product.getQuantity()));
        updateFormPanel.add(quantityLabel);
        updateFormPanel.add(quantityTextField);

        JLabel imageLabel = new JLabel("Image:");
        JTextField imageTextField = new JTextField(product.getImage());
        updateFormPanel.add(imageLabel);
        updateFormPanel.add(imageTextField);

        JLabel typeLabel = new JLabel("Type:");
        JTextField typeTextField = new JTextField(product.getType());
        updateFormPanel.add(typeLabel);
        updateFormPanel.add(typeTextField);

        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(0, 122, 204));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String description = descriptionTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());
                String image = imageTextField.getText();
                String type = typeTextField.getText();

                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setImage(image);
                product.setType(type);

                try {
                    productService.updateProduct(product);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(AdminDashboard.this, "Product updated successfully!");

                showPostsOption();
            }
        });
        updateFormPanel.add(updateButton);

        contentPanel.add(updateFormPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
