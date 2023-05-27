package view;
import javax.swing.*;
import java.awt.*;
public class ProductPage extends JFrame {
    private JPanel productPanel, imagePanel, infoPanel;
    private JButton viewButton, addButton;
    private JLabel productImage, productName, productPrice;

    public ProductPage() {
        // Set the title of the frame
        setTitle("Products");
        // Set the size of the frame
        setSize(600, 400);

        // Create the product panel
        productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout(10, 10));
        add(productPanel);

        // Create the image panel
        imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(200, 200));
        productImage = new JLabel(new ImageIcon("product.png"));
        imagePanel.add(productImage);
        productPanel.add(imagePanel, BorderLayout.WEST);

        // Create the info panel
        infoPanel = new JPanel();
        infoPanel.setBackground(new Color(204, 255, 255));
        infoPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Add product info
        productName = new JLabel("Product Name");
        productPrice = new JLabel("$20");
        infoPanel.add(productName);
        infoPanel.add(productPrice);

        // Create and add the buttons
        viewButton = new JButton("View");
        addButton = new JButton("Add to Cart");
        infoPanel.add(viewButton);
        infoPanel.add(addButton);

        productPanel.add(infoPanel, BorderLayout.CENTER);

        // Set the frame to be visible
        setVisible(true);
        // Set the frame to exit on close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}