package store.service.order;

import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;

public interface OrderService {
    Order createOrder(String name, int quantity);
    Order useMembership(List<Order> orders);
    List<Item> findByName(String name);
    boolean canOrder(String name, int quantity);
}
