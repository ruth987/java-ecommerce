package view;

import controller.SignupController;
import model.SignupModel;

import javax.swing.*;
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
        setSize(400, 400);
        // Set the layout to null
        setLayout(null);

        // Create and set the title label
        titleLabel = new JLabel("Sign Up");
        titleLabel.setBounds(150, 50, 150, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // Create and set the username label
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 100, 100, 30);
        add(usernameLabel);

        // Create and set the username text field
        usernameField = new JTextField(username);
        usernameField.setBounds(150, 100, 150, 30);
        add(usernameField);

        // Create and set the password label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        add(passwordLabel);

        // Create and set the password text field
        passwordField = new JPasswordField(password);
        passwordField.setBounds(150, 150, 150, 30);
        add(passwordField);

        // Create and set the email label
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 200, 100, 30);
        add(emailLabel);

        // Create and set the email text field
        emailField = new JTextField(email);
        emailField.setBounds(150, 200, 150, 30);
        add(emailField);

        // Create and set the signup button
        signupButton = new JButton("Sign Up");
        signupButton.setBounds(80, 270, 100, 30);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                sg.signup(username, password, email);
            }
        });
        add(signupButton);

        // Create and set the back button
        backButton = new JButton("Back");
        backButton.setBounds(200, 270, 100, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back button pressed");
                sg.showLoginPage();
            }
        });
        add(backButton);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(255, 255, 204));

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}