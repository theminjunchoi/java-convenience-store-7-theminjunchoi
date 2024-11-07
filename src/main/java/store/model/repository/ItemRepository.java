package store.model.repository;

import store.model.product.Item;
import store.model.product.Promotion;

public interface ItemRepository {
    void createRepository();
    Item organizeProduct(String name, int price, int quantity, Promotion promotion);
    void add(Item item);
    void save();
    void update(Item item, int quantity);
    Item findByNameAndPromotion(String name, Promotion promotion);
}
