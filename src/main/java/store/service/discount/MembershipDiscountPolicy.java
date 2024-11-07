package store.service.discount;

import java.util.List;
import store.model.product.Promotion;
import store.service.order.Order;

public class MembershipDiscountPolicy implements DiscountPolicy {
    private static final int DISCOUNT_RATE = 30;
    private static final int MAX_DISCOUNT = 8000;

    @Override
    public boolean isInDate() {
        return false;
    }

    @Override
    public int discount(int price) {
        int discount = (price * DISCOUNT_RATE) / 100;
        return Math.min(discount, MAX_DISCOUNT);
    }
}
