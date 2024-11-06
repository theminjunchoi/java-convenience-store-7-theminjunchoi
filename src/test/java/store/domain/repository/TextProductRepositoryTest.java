package store.domain.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.product.Promotion;

class TextProductRepositoryTest {
    TextProductRepository textProductRepository = new TextProductRepository();

    @Test
    @DisplayName("md 파일에서 products 읽기")
    void organizeRepository_Test() {
        //given
        textProductRepository.createRepository();

        //when
        Product product = new Product("콜라", 1000, 10, Promotion.SOFT_DRINK);
        Product findCola = textProductRepository.findByNameAndPromotion("콜라", Promotion.SOFT_DRINK);

        //then
        assertThat(product.getName()).isEqualTo(findCola.getName());
    }

    @Test
    @DisplayName("md 파일에서 products 읽기")
    void saveMarkdown_Test() {
        //given
        textProductRepository.createRepository();

        //when
        Product hwanta = new Product("환타", 2000, 5, Promotion.SOFT_DRINK);
        textProductRepository.add(hwanta);
        textProductRepository.save();

        //then
        //product.md에 환타 있는 지 확인
        TextProductRepository newTextProductRepository = new TextProductRepository();
        newTextProductRepository.createRepository();
        Product findHwanta = newTextProductRepository.findByNameAndPromotion("환타", Promotion.SOFT_DRINK);
        assertThat(hwanta.getName().equals(findHwanta.getName()));
    }
}