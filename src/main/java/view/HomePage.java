package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JLabel headingLabel;
    private JButton userButton;
    private JButton adminButton;
    private JButton creatorButton;

    public HomePage() {
        // Set the title of the frame
        setTitle("RS-ecommerce");
        // Set the size of the frame
        setSize(1000, 600);
        // Set the layout to BorderLayout
        setLayout(new BorderLayout());

        // Create the panel for the image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        add(imagePanel, BorderLayout.WEST);

        // Create and add the image to the panel
        ImageIcon imageIcon = new ImageIcon("java_images/background.jpg");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(500, getHeight()));
        imagePanel.add(imageLabel);

        // Create the panel for the heading and buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Create the heading label
        headingLabel = new JLabel("RS-ecommerce");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 48));
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setOpaque(true);
        headingLabel.setBackground(new Color(15, 46, 92));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPanel.add(headingLabel, BorderLayout.NORTH);

        // Create the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE); // Set the background color to white
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));
        contentPanel.add(buttonPanel, BorderLayout.EAST);

        // Create the buttons
        userButton = createButton("User");
        adminButton = createButton("Admin");
        creatorButton = createButton("Creator");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        buttonPanel.add(userButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(adminButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(creatorButton, gbc);

        // Add ActionListener to buttons
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions for "Login as User" button
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions for "Login as Admin" button
                AdminLoginPage adminLoginPage = new AdminLoginPage();
                adminLoginPage.setVisible(true);
                dispose();
            }
        });

        creatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions for "Login as Creator" button
                JOptionPane.showMessageDialog(HomePage.this, "Creator Login Clicked!");
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(15, 46, 92)); // Set the button background color to blue
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(200, 50));
        button.setFocusPainted(false);
//        button.setOpaque(true);
//        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
        button.setRolloverEnabled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });
    }
}
