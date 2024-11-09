package store.service.discount;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.List;
import store.model.product.Promotion;
import store.service.order.Order;

public class PromotionDiscountPolicy implements DiscountPolicy {

    public boolean isInDate(Promotion promotion) {
        LocalDateTime now = DateTimes.now();
        LocalDateTime startDate = promotion.getStartDate().atStartOfDay();
        LocalDateTime endDate = promotion.getEndDate().atStartOfDay();
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    @Override
    public int discount(List<Order> promotionOrders) {
        return promotionOrders.stream()
                .mapToInt(promotionOrder -> promotionOrder.getProductPrice() * promotionOrder.getQuantity())
                .sum();
    }
}
