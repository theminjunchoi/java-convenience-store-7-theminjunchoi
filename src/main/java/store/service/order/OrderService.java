package store.service.order;

import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;

public interface OrderService {
    Order createOrder(String name, int quantity, Promotion promotion);
    Order useMembership(List<Order> orders);
    List<Item> findByName(String name);
    Item findByNameAndPromotion(String name, Promotion promotion);
    boolean canOrder(String name, int quantity);
    void applyMembership(List<Order> orders);
}
