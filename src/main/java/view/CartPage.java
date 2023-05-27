package view;

import javax.swing.*;
import java.awt.*;

public class CartPage extends JFrame {
    private JLabel titleLabel, itemLabel, quantityLabel, priceLabel, totalLabel;
    private JPanel cartPanel, itemPanel, quantityPanel, pricePanel, totalPanel;
    private JButton checkoutButton, continueShoppingButton;

    public CartPage() {
        // Set the title of the frame
        setTitle("Shopping Cart");
        // Set the size of the frame
        setSize(600, 400);
        // Set the layout to null
        setLayout(null);

        // Create and set the title label
        titleLabel = new JLabel("Shopping Cart");
        titleLabel.setBounds(250, 10, 100, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // Create and set the cart panel
        cartPanel = new JPanel();
        cartPanel.setLayout(new GridLayout(0, 5));
        cartPanel.setBounds(50, 50, 500, 250);
        add(cartPanel);

        // Create and set the item label
        itemLabel = new JLabel("Item");
        itemLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cartPanel.add(itemLabel);

        // Create and set the quantity label
        quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cartPanel.add(quantityLabel);

        // Create and set the price label
        priceLabel = new JLabel("Price");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cartPanel.add(priceLabel);

        // Create and set the total label
        totalLabel = new JLabel("Total");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cartPanel.add(totalLabel);

        // Create and set the item panel
        itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemPanel.add(new JLabel("Item 1"));
        cartPanel.add(itemPanel);

        // Create and set the quantity panel
        quantityPanel = new JPanel();
        quantityPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        quantityPanel.add(new JLabel("2"));
        cartPanel.add(quantityPanel);

        // Create and set the price panel
        pricePanel = new JPanel();
        pricePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pricePanel.add(new JLabel("$29.99"));
        cartPanel.add(pricePanel);

        // Create and set the total panel
        totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("$59.98"));
        cartPanel.add(totalPanel);

        // Create and set the checkout button
        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(100,200, 100, 30);
        add(checkoutButton);

        // Create and set the continue shopping button
        continueShoppingButton = new JButton("Continue Shopping");
        continueShoppingButton.setBounds(300, 200, 150, 30);
        add(continueShoppingButton);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(255, 255, 204));

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}