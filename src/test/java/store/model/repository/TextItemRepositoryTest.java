package store.model.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.product.Item;
import store.model.product.Promotion;

class TextItemRepositoryTest {
    ItemRepository textProductRepository = new TextItemRepository();

    @Test
    @DisplayName("md 파일에서 products 읽기")
    void organizeRepository_Test() {
        //given
        textProductRepository.createRepository();

        //when
        Item findCola = textProductRepository.findByName("콜라").getFirst();

        //then
        assertThat("콜라").isEqualTo(findCola.getName());
    }


}