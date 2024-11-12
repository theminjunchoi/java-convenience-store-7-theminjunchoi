package store.model.repository;

import java.util.List;
import store.model.item.Item;
import store.model.item.Promotion;
import store.service.order.Order;

public interface ItemRepository {
    void createRepository();
    void organizePromotions();
    Item organizeItem(String name, int price, int quantity, Promotion promotion);
    void add(Item item);
    void save();
    List<Item> findByName(String name);
    Item findByNameAndPromotion(String name, Promotion promotion);
    List<Item> getStore();
    int getQuantityOfItem(String name, Promotion promotion);
    void updateRepository(List<Order> orders);
}
