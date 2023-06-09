package view;

import controller.LoginController;
import model.LoginModel;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
//        LoginModel loginModel = new LoginModel();
//        LoginPage loginPage = new LoginPage();
//        loginPage.setVisible(true);

//        SignupPage signupPage = new SignupPage("", "", "");
//        CartPage cartPage = new CartPage();
//        cartPage.setVisible(true);
        var loginPage = new LoginPage();
        loginPage.setVisible(true);
//        HomePage homePage = new HomePage();
//        homePage.setVisible(true);
    }
}

