package store.service.order;

import store.model.product.Item;
import store.model.product.Promotion;

public class Order {
    private final Item item;
    private final int quantity;
    private final int productPrice;
    private final Promotion promotion;

    public Order(Item item, int quantity, int productPrice, Promotion promotion) {
        this.item = item;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.promotion = promotion;
    }

    public String getName() {
        return item.getName();
    }

    public int getProductPrice() {
        return productPrice;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getQuantity() {
        return quantity;
    }
}
