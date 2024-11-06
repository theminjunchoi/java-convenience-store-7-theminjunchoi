package store.domain.product;

public class Product {
    private final String name;
    private final double price;
    private final int quantity;
    private final Promotion promotion;

    public Product(String name, double price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }
}
