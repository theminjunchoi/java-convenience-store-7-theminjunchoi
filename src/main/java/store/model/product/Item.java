package store.model.product;

public class Item {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Item(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return name + "," + price + "," + quantity + "," + promotion.toString();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
