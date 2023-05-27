package controller;

import model.SignupModel;
import view.SignupPage;
import view.LoginPage;

public class SignupController {
    private SignupPage signupPage;
    private SignupModel signupModel;

    public SignupController(SignupModel signupModel, SignupPage signupPage) {
        this.signupModel = signupModel;
        this.signupPage = signupPage;
    }

    public void signup(String username, String password, String email) {
        boolean success = signupModel.signup(username, password, email);
        if (success) {
            System.out.println("You are successfully registered.");
            // Code for redirecting to the login page
            LoginPage loginPage = new LoginPage();
            signupPage.setVisible(false);
            loginPage.setVisible(true);
        } else {
            System.out.println("The registration was unsuccessful.");
            // Code for displaying error message
        }
    }

    public void showLoginPage() {
        // Code for showing the login page
        LoginPage loginPage = new LoginPage();
        signupPage.setVisible(false);
        loginPage.setVisible(true);
    }
}