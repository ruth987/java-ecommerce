package model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String image;
    private String type;

    public Product(String name, String description, double price, int quantity, String image, String type, int adminId) {
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.quantity    = quantity;
        this.type        = type;
        this.image       = image;
        this.id          = adminId;
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

    public int getAdminId() {
        return id;
    }
    public String getType() {
        return type;
    }

    public void setId(int generatedId) {
        this.id = generatedId;
    }

    public void setType(String type) {
        this.type = type;
    }
}