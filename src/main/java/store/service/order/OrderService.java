package store.service.order;

import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;

public interface OrderService {
    Order createOrder(String name, int quantity);
    Order createOrderWithPromotion(String name, int quantity, Promotion promotion);
    List<Item> findByName(String name);
    boolean isTwoPlusOneMore(Order order);
    boolean isTwoPlusOne(Order order);
    boolean isOnePlusOneMore(Order order);
    boolean isOnePlusOne(Order order);
    int applyMembership(List<Order> orders);
    int applyPromotion(List<Order> promotionOrders);
}
