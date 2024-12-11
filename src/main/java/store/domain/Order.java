package store.domain;

public class Order {
    private final String itemName;
    private final int price;
    private final int count;

    public Order(String itemName, int price, int count) {
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getItemName() {
        return itemName;
    }
}
