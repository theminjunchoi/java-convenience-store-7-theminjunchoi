package store.service.discount;

import java.util.List;
import store.model.product.Promotion;
import store.service.order.Order;

public class MembershipDiscountPolicy implements DiscountPolicy {
    private static final int DISCOUNT_RATE = 30 / 100;
    private static final int MAX_DISCOUNT = 8000;

    @Override
    public boolean isInDate() {
        return false;
    }

    @Override
    public void discount(List<Order> orders) {
        orders.stream()
                .filter(order -> order.getPromotion() == Promotion.NO_PROMOTION)
                .forEach(order -> {
                    int discountPrice = (int) (order.getProductPrice() * DISCOUNT_RATE);
                    order.setDiscountPrice(discountPrice);
                });
    }
}
