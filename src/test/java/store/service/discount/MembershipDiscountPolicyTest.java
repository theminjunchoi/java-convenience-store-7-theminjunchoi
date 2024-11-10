package store.service.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.item.Item;
import store.model.repository.ItemRepository;
import store.model.repository.TextItemRepository;
import store.service.order.Order;
import store.service.order.OrderService;
import store.service.order.OrderServiceImpl;

class MembershipDiscountPolicyTest {
    ItemRepository itemRepository;
    OrderService orderService;

    @BeforeEach
    void setUp() {
        itemRepository = new TextItemRepository();
        itemRepository.createRepository();
        orderService = new OrderServiceImpl();
    }

    @Test
    @DisplayName("일반 상품들의 멤버십 적용 계산")
    void discount_Test() {
        // given
        Item energyBar = orderService.findByName("에너지바").stream().findFirst().get();
        Item water = orderService.findByName("물").stream().findFirst().get();

        // when
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(energyBar, 4, 2000, energyBar.getPromotion()));
        orders.add(new Order(water, 4, 500, water.getPromotion()));
        int membershipDiscountPrice = orderService.applyMembership(orders);

        // then
        assertThat(membershipDiscountPrice).isEqualTo(3000);
    }

    @Test
    @DisplayName("멤버십 할인 가격은 최대 8,000원")
    void discountMax_Test() {
        // given
        Item energyBar = orderService.findByName("에너지바").stream().findFirst().get();
        Item water = orderService.findByName("물").stream().findFirst().get();
        Item dosirak = orderService.findByName("정식도시락").stream().findFirst().get();

        // when
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(energyBar, 5, 2000, energyBar.getPromotion()));
        orders.add(new Order(water, 10, 500, water.getPromotion()));
        orders.add(new Order(dosirak, 8, 6400, dosirak.getPromotion()));
        int membershipDiscountPrice = orderService.applyMembership(orders);

        // then
        assertThat(membershipDiscountPrice).isEqualTo(8000);
    }
}