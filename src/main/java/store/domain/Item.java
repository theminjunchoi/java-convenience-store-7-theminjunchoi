package store.domain;

public class Item {
    private final String itemName;
    private final int price;
    private final int count;
    private final Category category;

    public Item(String itemName, int price, int count, Category category) {
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getCategoryName() {
        return category.getCategoryName();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Item)) {
            return false;
        }
        Item item = (Item) obj;
        return item.itemName.equals(itemName);
    }
}
