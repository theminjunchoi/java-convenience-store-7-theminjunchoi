package store.domain.repository;

import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(product.getName()).isEqualTo(findCola.getName());
    }
}