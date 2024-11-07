package store.service.discount;

public class PromotionDiscountPolicy implements DiscountPolicy {
    @Override
    public boolean isInDate() {
        return false;
    }

    @Override
    public int discount(int price) {
        return 0;
    }
}
