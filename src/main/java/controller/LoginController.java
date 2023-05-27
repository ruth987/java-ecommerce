package controller;
import model.LoginModel;
import view.LoginPage;
import view.SignupPage;

public class LoginController {
    private LoginPage loginPage;
    private LoginModel loginModel;

    public LoginController(LoginModel loginModel, LoginPage loginPage){
        this.loginModel = loginModel;
        this.loginPage = loginPage;
    }

    public void login(String username, String password) {
        boolean success = loginModel.login(username, password);
        if (success) {
            System.out.println("you are successfully logged in.");
            // code for redirecting to home page
        } else {
            System.out.println("The login was unsuccessful.");
            // code for displaying error message
        }
    }

    public void showSignupPage() {
        // code for showing the sign-up page
        SignupPage signupPage = new SignupPage("", "", "");
        loginPage.setVisible(false);
        signupPage.setVisible(true);

    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }
}