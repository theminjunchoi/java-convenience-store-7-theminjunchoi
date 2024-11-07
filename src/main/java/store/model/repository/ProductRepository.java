package store.model.repository;

import store.model.product.Product;
import store.model.product.Promotion;

public interface ProductRepository {
    void createRepository();
    Product organizeProduct(String name, int price, int quantity, Promotion promotion);
    void add(Product product);
    void save();
    void update(Product product, int quantity);
    Product findByNameAndPromotion(String name, Promotion promotion);
}
