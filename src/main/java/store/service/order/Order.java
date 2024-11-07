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
        this.discountPrice = 0;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
