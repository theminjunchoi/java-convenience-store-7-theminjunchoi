package store.domain.repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import store.domain.product.Product;
import store.domain.product.Promotion;

public class TextProductRepository implements ProductRepository {
    private static final Path productsPath = Paths.get("src/main/resources/products.md");
    private static final List<Product> store = new ArrayList<>();

    @Override
    public void createRepository() {
        try {
            List<String> stocks = Files.readAllLines(productsPath);
            for (int i = 1; i < stocks.size(); i++) {
                String line = stocks.get(i);
                String[] values = line.split(",");
                String name = values[0];
                int price = Integer.parseInt(values[1]);
                int quantity = Integer.parseInt(values[2]);
                Promotion promotion= Promotion.getPromotion(values[3]);
                save(organizeProduct(name, price, quantity, promotion));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product organizeProduct(String name, int price, int quantity, Promotion promotion) {
        return new Product(name, price, quantity, promotion);
    }

    @Override
    public void save(Product product) {
        store.add(product);
    }

    @Override
    public void update(Product product, int quantity) {
        Product updateProduct = findByNameAndPromotion(product.getName(), product.getPromotion());
        // 추후 구현
    }

    @Override
    public Product findByNameAndPromotion(String name, Promotion promotion) {
        for (Product product : store) {
            if (product.getName().equals(name) && product.getPromotion().equals(promotion)) {
                return product;
            }
        }
        return null;
    }

}