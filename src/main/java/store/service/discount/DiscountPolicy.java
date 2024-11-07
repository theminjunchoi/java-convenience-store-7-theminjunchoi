package store.service.discount;

public interface DiscountPolicy {
    boolean isInDate();
    int discount(int price);
}
