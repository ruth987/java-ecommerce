package view;
import controller.LoginController;
import model.LoginModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton, forgotPasswordButton;
    private LoginController loginController;
    public LoginPage() {
        this("", "");
    }

    public LoginPage( String username, String password) {
        LoginController lg = new LoginController(new LoginModel(), this);

        this.loginController = lg;
        // Set the title of the frame
        setTitle("Login");
        // Set the size of the frame
        setSize(400, 350);
        // Set the layout to null
        setLayout(null);

        // Create and set the title label
        titleLabel = new JLabel("Login");
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

        // Create and set the login button
        loginButton = new JButton("Login");
        loginButton.setBounds(80, 220, 80, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if(loginController != null){
                    var success = loginController.login(username, password);
                    if(success){
                        var product = new ProductList();
                        product.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed");
                    }
                }

            }
        });
        add(loginButton);

        // Create and set the signup button
        signupButton = new JButton("Sign Up");
        signupButton.setBounds(200, 220, 80, 30);
//        frame.add(signupButton);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Sign Up button pressed");
                if (loginController != null) {
                    loginController.showSignupPage();
                }
            }
        });
        add(signupButton);

        // Create and set the forgot password button
        forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.setBounds(120, 270, 150, 30);
        add(forgotPasswordButton);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(255, 255, 204));

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
