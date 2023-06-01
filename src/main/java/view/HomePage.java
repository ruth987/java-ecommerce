package view;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private JButton loginButton;

    public HomePage() {
        // Set the title of the frame
        setTitle("Ecommerce Application");
        // Set the size of the frame
        setSize(800, 600);
        // Set the layout to null
        setLayout(null);

        // Create and set the panel for the logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(0, 0, 800, 200);
        logoPanel.setBackground(new Color(33, 150, 243));
        JLabel logoLabel = new JLabel("Ecommerce Application");
        logoLabel.setFont(new Font("Roboto", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);
        add(logoPanel);

        // Create and set the panel for the login button
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 200, 800, 400);
        loginPanel.setBackground(Color.WHITE);
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 50));
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setFont(new Font("Roboto", Font.BOLD, 18));
        loginPanel.add(loginButton);
        add(loginPanel);

        // Set the background color of the frame
        getContentPane().setBackground(Color.WHITE);

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}