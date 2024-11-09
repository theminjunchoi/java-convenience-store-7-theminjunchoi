package store.service.order;

import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;
import store.model.repository.ItemRepository;
import store.model.repository.TextItemRepository;
import store.service.discount.DiscountPolicy;
import store.service.discount.MembershipDiscountPolicy;
import store.service.discount.PromotionDiscountPolicy;

public class OrderServiceImpl implements OrderService {
    private final ItemRepository itemRepository = new TextItemRepository();
    private final DiscountPolicy promotionDiscountPolicy = new PromotionDiscountPolicy();
    private final DiscountPolicy membershipDiscountPolicy = new MembershipDiscountPolicy();

    @Override
    public Order createOrder(String name, int quantity) {
        Item item = findByName(name).stream()
                .findFirst().orElse(null);
        assert item != null;
        return new Order(item, quantity, item.getPrice(), item.getPromotion());
    }

    @Override
    public Order useMembership(List<Order> orders) {
        return null;
    }

    @Override
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    public Item findByNameAndPromotion(String name, Promotion promotion) {
        return itemRepository.findByNameAndPromotion(name, promotion);
    }

    @Override
    public boolean canOrder(String name, int quantity) {
        List<Item> items = findByName(name);
        int totalCount = items.stream()
                .mapToInt(Item::getQuantity)
                .sum();
        return quantity < totalCount && !items.isEmpty();
    }

    @Override
    public boolean isTwoPlusOneMore(Order order) {
        return order.getPromotion().getBuy() == 2
                && order.getPromotion().getGet() == 1
                && order.getQuantity() % 3 == 2
                && promotionDiscountPolicy.isInDate(order.getPromotion())
                && (itemRepository.getQuantityOfItem(order.getName(), order.getPromotion()) - order.getQuantity()) >= 1;
    }

    @Override
    public boolean isTwoPlusOne(Order order) {
        return order.getPromotion().getBuy() == 2
                && order.getPromotion().getGet() == 1
                && order.getQuantity() % 3 == 0;
    }

    @Override
    public boolean isOnePlusOneMore(Order order) {
        return order.getPromotion().getBuy() == 1
                && order.getPromotion().getGet() == 1
                && order.getQuantity() % 2 == 1
                && promotionDiscountPolicy.isInDate(order.getPromotion())
                && (itemRepository.getQuantityOfItem(order.getName(), order.getPromotion()) - order.getQuantity()) >= 1;
    }

    @Override
    public boolean isOnePlusOne(Order order) {
        return order.getPromotion().getBuy() == 1
                && order.getPromotion().getGet() == 1
                && order.getQuantity() % 2 == 0;
    }

    @Override
    public int applyMembership(List<Order> orders) {
        return membershipDiscountPolicy.discount(orders);
    }

    @Override
    public int applyPromotion(List<Order> promotionOrders) {
        return promotionDiscountPolicy.discount(promotionOrders);
    }
}
