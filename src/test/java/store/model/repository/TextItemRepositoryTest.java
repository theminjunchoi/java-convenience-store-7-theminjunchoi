package store.model.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.item.Item;
import store.model.item.Promotion;
import store.service.order.OrderService;
import store.service.order.OrderServiceImpl;

class TextItemRepositoryTest {
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository = new TextItemRepository();
        itemRepository.createRepository();
    }

    @Test
    @DisplayName("md 파일에서 products 읽기")
    void organizeRepository_Test() {
        //given & when
        Item findCola = itemRepository.findByName("콜라").getFirst();

        //then
        assertThat("콜라").isEqualTo(findCola.getName());
    }

    @Test
    @DisplayName("원하는 Item의 개수 확인하기")
    void getQuantityOfItem_Test() {
        // given
        OrderService orderService = new OrderServiceImpl();

        // when
        List<Item> items = orderService.findByName("물");
        Promotion waterPromotion = items.stream().map(Item::getPromotion).findFirst().orElse(null);
        int WaterCount = itemRepository.getQuantityOfItem("물", waterPromotion);

        // then
        assertThat(WaterCount).isEqualTo(10);
    }
}