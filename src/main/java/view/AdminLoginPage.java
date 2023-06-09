package view;
import model.Admin;
import model.AdminLoginModel;
import model.AdminService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminLoginPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    static Admin admin = Admin.getInstance();

    public AdminLoginPage() {
        // Set the title of the frame
        setTitle("Admin Login");
        // Set the size of the frame
        setSize(800, 600);
        // Set the layout to BorderLayout
        setLayout(new BorderLayout());

        // Create the left panel with blue background
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(33, 97, 140));
        leftPanel.setLayout(new GridBagLayout());
        add(leftPanel, BorderLayout.WEST);

        // Create the "Admin Login" label in big font
        JLabel adminLoginLabel = new JLabel("Admin Login");
        adminLoginLabel.setFont(new Font("Roboto", Font.BOLD, 36));
        adminLoginLabel.setForeground(Color.WHITE);
        adminLoginLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(adminLoginLabel, new GridBagConstraints());

        // Create the right panel with white background
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());
        add(rightPanel, BorderLayout.CENTER);

        // Create a panel for the login form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        rightPanel.add(formPanel, BorderLayout.CENTER);

        // Create the "Email" label and text field
        JLabel emailLabel = new JLabel("Username");
        emailLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 30));
        emailField.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        formPanel.add(emailField, gbc);

        // Create the "Password" label and password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        formPanel.add(passwordField, gbc);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create the login button with blue color and rounded shape
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setFont(new Font("Roboto", Font.BOLD, 18));
        loginButton.setBackground(new Color(33, 97, 140));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setUI(new RoundedButtonUI());
        buttonPanel.add(loginButton);

        // Create the signup button with blue color and rounded shape
        JButton signupButton = new JButton("Signup");
        signupButton.setPreferredSize(new Dimension(120, 40));
        signupButton.setForeground(Color.WHITE);
        signupButton.setOpaque(true);
        signupButton.setBorderPainted(false);
        signupButton.setFont(new Font("Roboto", Font.BOLD, 18));
        signupButton.setBackground(new Color(33, 97, 140));
        signupButton.setFocusPainted(false);
        signupButton.setBorder(BorderFactory.createEmptyBorder());
        signupButton.setUI(new RoundedButtonUI());
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(signupButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Create an instance of AdminService
                AdminLoginModel adminmodel = new AdminLoginModel();

                // Call the login method to authenticate the admin
                boolean success = adminmodel.login(email, password);

                if (success) {
                    // Admin login successful
                    AdminDashboard adminDashboardPage = new AdminDashboard(admin);
                    adminDashboardPage.setVisible(true);
                    dispose();
                } else {
                    // Admin login failed
                    JOptionPane.showMessageDialog(null, "Admin login failed. Please check your email and password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add action listener to the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the admin signup page
                AdminSignupPage adminSignupPage = new AdminSignupPage();
                adminSignupPage.setVisible(true);
                dispose();
            }
        });

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        AdminLoginPage adminLoginPage = new AdminLoginPage();
        adminLoginPage.setVisible(true);
    }

    // Custom UI class for creating rounded buttons
    static class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        protected void installDefaults(AbstractButton button) {
            super.installDefaults(button);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            button.setBackground(new Color(33, 97, 140));
            button.setOpaque(true);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(c.getBackground());
            g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
            super.paint(g2d, c);
            g2d.dispose();
        }
    }
}

