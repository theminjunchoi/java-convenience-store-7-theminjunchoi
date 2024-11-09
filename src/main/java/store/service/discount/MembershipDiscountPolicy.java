package store.service.discount;

import java.util.List;
import store.model.product.Promotion;
import store.service.order.Order;

public class MembershipDiscountPolicy implements DiscountPolicy {
    private static final int DISCOUNT_RATE = 30;
    private static final int MAX_DISCOUNT = 8000;

    @Override
    public boolean isInDate(Promotion promotion) {
        return true;
    }

    @Override
    public int discount(List<Order> orders) {
        int noPromotionPrice = orders.stream()
                .filter(order -> order.getPromotion().getName().equals("null"))
                .mapToInt(order -> order.getProductPrice() * order.getQuantity())
                .sum();
        int totalMembershipPrice = noPromotionPrice * DISCOUNT_RATE / 100;
        return Math.min(totalMembershipPrice, MAX_DISCOUNT);
    }
}
