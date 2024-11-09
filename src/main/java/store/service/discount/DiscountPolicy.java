package store.service.discount;

import java.util.List;
import store.model.product.Promotion;
import store.service.order.Order;

public interface DiscountPolicy {
    boolean isInDate(Promotion promotion);
    int discount(List<Order> orders);
}
