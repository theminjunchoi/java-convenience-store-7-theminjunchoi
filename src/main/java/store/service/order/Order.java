package store.service.order;

import store.model.product.Item;
import store.model.product.Promotion;

public class Order {
    private Item item;
    private int quantity;
    private int productPrice;
    private int discountPrice;
    private Promotion promotion;

    public Order(Item item, int quantity, int productPrice, Promotion promotion) {
        this.item = item;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.promotion = promotion;
    }
}
