package store.service.discount;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.item.Item;
import store.model.repository.ItemRepository;
import store.model.repository.TextItemRepository;
import store.service.order.Order;
import store.service.order.OrderService;
import store.service.order.OrderServiceImpl;

class PromotionDiscountPolicyTest {
    ItemRepository itemRepository;
    OrderService orderService;

    @BeforeEach
    void setUp() {
        itemRepository = new TextItemRepository();
        itemRepository.createRepository();
        orderService = new OrderServiceImpl();
    }

    @Test
    @DisplayName("Promotion인 상품들의 discount 계산")
    void discount_Test() {
        // given
        Item cola = orderService.findByName("콜라").stream().findFirst().get();

        // when
        List<Order> promotionOrders = new ArrayList<>();
        promotionOrders.add(new Order(cola, 3, 1000, cola.getPromotion()));
        int promotionDiscountPrice = orderService.applyPromotion(promotionOrders);

        // then
        Assertions.assertThat(promotionDiscountPrice).isEqualTo(3000);
    }
}