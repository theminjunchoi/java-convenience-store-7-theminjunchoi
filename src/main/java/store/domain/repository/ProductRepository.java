package store.domain.repository;

import store.domain.product.Product;
import store.domain.product.Promotion;

public interface ProductRepository {
    void createRepository();
    Product organizeProduct(String name, int price, int quantity, Promotion promotion);
    void save(Product product);
    void update(Product product, int quantity);
    Product findByNameAndPromotion(String name, Promotion promotion);
