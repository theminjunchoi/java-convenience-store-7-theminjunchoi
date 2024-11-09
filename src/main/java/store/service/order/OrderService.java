package store.service.order;

import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;

public interface OrderService {
    Order createOrder(String name, int quantity);
    Order useMembership(List<Order> orders);
    List<Item> findByName(String name);
    Item findByNameAndPromotion(String name, Promotion promotion);
    boolean canOrder(String name, int quantity);
    boolean isTwoPlusOneMore(Order order);
    boolean isTwoPlusOne(Order order);
    boolean isOnePlusOneMore(Order order);
    boolean isOnePlusOne(Order order);
    int applyMembership(List<Order> orders);
    int applyPromotion(List<Order> promotionOrders);
}
