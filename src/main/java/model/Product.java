package model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String image;

    public Product(int id, String name, String description, double price, int quantity, String image) {
        this.id          = id;
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.quantity    = quantity;
        this.image       = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return name;
    }

    public Object getProductId() {
        return id;
    }

    public String getProductPrice() {
        return String.valueOf(price);
    }

    public String getProductDescription() {
        return description;
    }
}