package Data;

public class Cart {
    private int id;
    private int userId;
    private int productId;
    private int quantity;

    public Cart(int id, int userId, int productId, int quantity) {
        this.id        = id;
        this.userId    = userId;
        this.productId = productId;
        this.quantity  = quantity;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
