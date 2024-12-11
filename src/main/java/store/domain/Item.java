package store.domain;

public class Item {
    private final String itemName;
    private final int count;
    private final Category category;

    public Item(String itemName, int count, Category category) {
        this.itemName = itemName;
        this.count = count;
        this.category = category;
    }
}
