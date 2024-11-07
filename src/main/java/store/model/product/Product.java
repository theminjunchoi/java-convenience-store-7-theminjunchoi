package store.model.product;

public class Product {
    private final String name;
    private final int price;
    private final int quantity;
    private final Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return name + "," + price + "," + quantity + "," + promotion.toString();
    }
}