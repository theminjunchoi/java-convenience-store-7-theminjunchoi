package store.service.order;

import java.util.List;
import java.util.Optional;
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
        List<Item> findItems = findByName(name);
        Optional<Item> promotionItem = findItems.stream()
                .filter(findItem -> findItem.getPromotion() != Promotion.NO_PROMOTION)
                .findAny();
        Item item = promotionItem.orElse(findItems.getFirst());
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
    public boolean canOrder(String name, int quantity) {
        List<Item> items = findByName(name);
        int totalCount = items.stream()
                .mapToInt(Item::getQuantity)
                .sum();
        return quantity < totalCount && !items.isEmpty();
    }
}
