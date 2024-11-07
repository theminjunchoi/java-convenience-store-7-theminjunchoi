package store.service.discount;

import java.util.List;
import store.service.order.Order;

public interface DiscountPolicy {
    boolean isInDate();
    void discount(List<Order> orders);


}
