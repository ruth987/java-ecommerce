package view;

import model.AdminSignupModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminSignupPage extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneNumberField;

    public AdminSignupPage() {
        // Set the title of the frame
        setTitle("Admin Signup");
        // Set the size of the frame
        setSize(800, 600);
        // Set the layout to BorderLayout
        setLayout(new BorderLayout());

        // Create the left panel with a darker background
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(33, 97, 140));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        add(leftPanel, BorderLayout.WEST);

        // Create a rigid area for top spacing
        leftPanel.add(Box.createVerticalStrut(100));

        // Create the "Admin Signup" label in big font
        JLabel adminSignupLabel = new JLabel("Admin Signup");
        adminSignupLabel.setFont(new Font("Roboto", Font.BOLD, 36));
        adminSignupLabel.setForeground(Color.WHITE);
        adminSignupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(adminSignupLabel);

        // Create a rigid area for bottom spacing
        leftPanel.add(Box.createVerticalStrut(200));

        // Create the right panel with white background
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());
        add(rightPanel, BorderLayout.CENTER);

        // Create a panel for the signup form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        rightPanel.add(formPanel, BorderLayout.CENTER);

        // Create the "Username" label and text field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 30));
        usernameField.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        formPanel.add(usernameField, gbc);

        // Create the "Email" label and text field
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 30));
        emailField.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        formPanel.add(emailField, gbc);

        // Create the "Password" label and password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        formPanel.add(passwordField, gbc);

        // Create the "Phone Number" label and text field
        JLabel phoneNumberLabel = new JLabel("Phone Number");
        phoneNumberLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        formPanel.add(phoneNumberLabel, gbc);

        phoneNumberField = new JTextField();
        phoneNumberField.setPreferredSize(new Dimension(300, 30));
        phoneNumberField.setFont(new Font("Roboto", Font.PLAIN, 14));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        formPanel.add(phoneNumberField, gbc);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create the signup button with a darker background and circular shape
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

        // Create the login button with a darker background and circular shape
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

        // Add action listener to the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String phoneNumber = phoneNumberField.getText();

                // Check if all fields are filled
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Create an instance of the AdminSignupModel and call the signup method
                    AdminSignupModel signupModel = new AdminSignupModel();
                    boolean success = signupModel.signup(username, password, email, phoneNumber);

                    if (success) {
                        AdminLoginPage adminLoginPage = new AdminLoginPage();
                        adminLoginPage.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Admin signup failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the admin login page
                AdminLoginPage adminLoginPage = new AdminLoginPage();
                adminLoginPage.setVisible(true);
                dispose();
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(signupButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(loginButton);

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        AdminSignupPage adminSignupPage = new AdminSignupPage();
        adminSignupPage.setVisible(true);
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
