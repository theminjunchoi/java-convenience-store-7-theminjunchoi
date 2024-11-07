package store.service.discount;

import java.util.List;
import store.service.order.Order;

public class PromotionDiscountPolicy implements DiscountPolicy {
    @Override
    public boolean isInDate() {
        return false;
    }

    @Override
    public void discount(List<Order> order) {

    }
}
