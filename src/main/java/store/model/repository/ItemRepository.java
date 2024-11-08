package store.model.repository;

import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;

public interface ItemRepository {
    void createRepository();
    Item organizeProduct(String name, int price, int quantity, Promotion promotion);
    void add(Item item);
    void save();
    void update(Item item, int quantity);
    List<Item> findByName(String name);
    Item findByNameAndPromotion(String name, Promotion promotion);
    List<Item> getStore();
    int getQuantityOfItem(String name, Promotion promotion);
}
