package store.model.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.product.Item;
import store.model.product.Promotion;

class TextItemRepositoryTest {
    TextItemRepository textProductRepository = new TextItemRepository();
    Promotion softDrink = new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"))
;
    @Test
    @DisplayName("md 파일에서 products 읽기")
    void organizeRepository_Test() {
        //given
        textProductRepository.createRepository();

        //when
        Item item = new Item("콜라", 1000, 10, softDrink);
        Item findCola = textProductRepository.findByNameAndPromotion("콜라", softDrink);

        //then
        assertThat(item.getName()).isEqualTo(findCola.getName());
    }

    @Test
    @DisplayName("md 파일에서 products 읽기")
    void saveMarkdown_Test() {
        //given
        textProductRepository.createRepository();

        //when
        Item hwanta = new Item("환타", 2000, 5, softDrink);
        textProductRepository.add(hwanta);
        textProductRepository.save();

        //then
        //product.md에 환타 있는 지 확인
        TextItemRepository newTextProductRepository = new TextItemRepository();
        newTextProductRepository.createRepository();
        Item findHwanta = newTextProductRepository.findByNameAndPromotion("환타", softDrink);
        assertThat(hwanta.getName().equals(findHwanta.getName()));
    }
}