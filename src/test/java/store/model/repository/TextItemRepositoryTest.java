package store.model.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.product.Item;
import store.model.product.Promotion;

class TextItemRepositoryTest {
    TextItemRepository textProductRepository = new TextItemRepository();

    @Test
    @DisplayName("md 파일에서 products 읽기")
    void organizeRepository_Test() {
        //given
        textProductRepository.createRepository();

        //when
        Item item = new Item("콜라", 1000, 10, Promotion.SOFT_DRINK);
        Item findCola = textProductRepository.findByNameAndPromotion("콜라", Promotion.SOFT_DRINK);

        //then
        assertThat(item.getName()).isEqualTo(findCola.getName());
    }

    @Test
    @DisplayName("md 파일에서 products 읽기")
    void saveMarkdown_Test() {
        //given
        textProductRepository.createRepository();

        //when
        Item hwanta = new Item("환타", 2000, 5, Promotion.SOFT_DRINK);
        textProductRepository.add(hwanta);
        textProductRepository.save();

        //then
        //product.md에 환타 있는 지 확인
        TextItemRepository newTextProductRepository = new TextItemRepository();
        newTextProductRepository.createRepository();
        Item findHwanta = newTextProductRepository.findByNameAndPromotion("환타", Promotion.SOFT_DRINK);
        assertThat(hwanta.getName().equals(findHwanta.getName()));
    }
}