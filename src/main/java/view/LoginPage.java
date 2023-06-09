package view;
import controller.LoginController;
import model.LoginModel;
import model.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPage extends JFrame {
    private JLabel titleLabel, usernameLabel, passwordLabel, userLoginLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton, forgotPasswordButton;
    private LoginController loginController;

    public LoginPage() {
        this("", "");
    }

    public LoginPage(String username, String password) {
        LoginController lg = new LoginController(new LoginModel(), this);

        this.loginController = lg;
        // Set the title of the frame
        setTitle("Login");
        // Set the size of the frame
        setSize(600, 350);
        // Set the layout to null
        setLayout(null);

        // Create the left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(33, 97, 140));
        leftPanel.setBounds(0, 0, 300, 350);
        leftPanel.setLayout(null);
        add(leftPanel);

        // Create and set the user login label in the left panel
        userLoginLabel = new JLabel("User Login");
        userLoginLabel.setBounds(0, 150, 300, 30);
        userLoginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userLoginLabel.setForeground(Color.WHITE);
        userLoginLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(userLoginLabel);

        // Create the right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(300, 0, 300, 350);
        rightPanel.setLayout(null);
        add(rightPanel);

        // Create and set the title label in the right panel
        titleLabel = new JLabel("Login");
        titleLabel.setBounds(75, 20, 150, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        rightPanel.add(titleLabel);

        // Create and set the username label
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 80, 100, 30);
        rightPanel.add(usernameLabel);

        // Create and set the username text field
        usernameField = new JTextField(username);
        usernameField.setBounds(130, 80, 150, 30);
        rightPanel.add(usernameField);

        // Create and set the password label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 130, 100, 30);
        rightPanel.add(passwordLabel);

        // Create and set the password text field
        passwordField = new JPasswordField(password);
        passwordField.setBounds(130, 130, 150, 30);
        rightPanel.add(passwordField);

        // Create and set the login button
        loginButton = new JButton("Login");
        loginButton.setBounds(120, 220, 80, 30);
        loginButton.setBackground(new Color(33, 97, 140));
        loginButton.setForeground(Color.WHITE);
        Border loginButtonBorder = BorderFactory.createLineBorder(new Color(33, 97, 140), 2);
        loginButton.setBorder(loginButtonBorder);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (loginController != null) {
                    var success = loginController.login(username, password);
                    if (success) {
                        ProductList product = null;
                        try {
                            product = new ProductList(User.getInstance());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        product.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed");
                    }
                }
            }
        });
        rightPanel.add(loginButton);

        // Create and set the signup button
        signupButton = new JButton("Sign Up");
        signupButton.setBounds(120, 260, 80, 30);
        signupButton.setBackground(new Color(33, 97, 140));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBorder(loginButtonBorder);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Sign Up button pressed");
                if (loginController != null) {
                    loginController.showSignupPage();
                }
            }
        });
        rightPanel.add(signupButton);

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
