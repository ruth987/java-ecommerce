package view;

import controller.SignupController;
import model.SignupModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPage extends JFrame {
    private JLabel titleLabel, usernameLabel, passwordLabel, emailLabel;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton signupButton, backButton;

    public SignupPage() {
        this("", "", "");
    }

    public SignupPage(String username, String password, String email) {
        SignupController sg = new SignupController(new SignupModel(), this);

        // Set the title of the frame
        setTitle("Sign Up");
        // Set the size of the frame
        setSize(600, 400);
        // Set the layout to null
        setLayout(null);

        // Create the left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(33, 97, 140));
        leftPanel.setBounds(0, 0, 300, 400);
        leftPanel.setLayout(null);
        add(leftPanel);

        // Create and set the title label in the left panel
        titleLabel = new JLabel("Sign Up");
        titleLabel.setBounds(0, 50, 300, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(titleLabel);

        // Create the right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(300, 0, 300, 400);
        rightPanel.setLayout(null);
        add(rightPanel);

        // Create and set the username label
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 100, 100, 30);
        rightPanel.add(usernameLabel);

        // Create and set the username text field
        usernameField = new JTextField(username);
        usernameField.setBounds(130, 100, 150, 30);
        rightPanel.add(usernameField);

        // Create and set the password label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 150, 100, 30);
        rightPanel.add(passwordLabel);

        // Create and set the password text field
        passwordField = new JPasswordField(password);
        passwordField.setBounds(130, 150, 150, 30);
        rightPanel.add(passwordField);

        // Create and set the email label
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 200, 100, 30);
        rightPanel.add(emailLabel);

        // Create and set the email text field
        emailField = new JTextField(email);
        emailField.setBounds(130, 200, 150, 30);
        rightPanel.add(emailField);

        // Create and set the signup button
        signupButton = new JButton("Sign Up");
        signupButton.setBounds(120, 270, 100, 30);
        signupButton.setBackground(new Color(33, 97, 140));
        signupButton.setForeground(Color.WHITE);
        Border signupButtonBorder = BorderFactory.createLineBorder(new Color(33, 97, 140), 2);
        signupButton.setBorder(signupButtonBorder);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                sg.signup(username, password, email);
            }
        });
        rightPanel.add(signupButton);

        // Create and set the back button
        backButton = new JButton("Back");
        backButton.setBounds(120, 320, 100, 30);
        backButton.setBackground(new Color(33, 97, 140));
        backButton.setForeground(Color.WHITE);
        Border backButtonBorder = BorderFactory.createLineBorder(new Color(33, 97, 140), 2);
        backButton.setBorder(backButtonBorder);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back button pressed");
                sg.showLoginPage();
            }
        });
        rightPanel.add(backButton);

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SignupPage signupPage = new SignupPage();
        signupPage.setVisible(true);
    }
}
