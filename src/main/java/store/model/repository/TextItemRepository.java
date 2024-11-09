package store.model.repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;
import store.service.order.Order;

public class TextItemRepository implements ItemRepository {
    private static final Path productsPath = Paths.get("src/main/resources/products.md");
    private static final List<Item> store = new ArrayList<>();

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
                add(organizeProduct(name, price, quantity, promotion));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item organizeProduct(String name, int price, int quantity, Promotion promotion) {
        return new Item(name, price, quantity, promotion);
    }

    @Override
    public void add(Item item) {
        store.add(item);
    }

    @Override
    public void save() {
        try(BufferedWriter writer = Files.newBufferedWriter(productsPath)) {
            writer.write("name,price,quantity,promotion\n");
            for(Item item : store) {
                writer.write(item.toString() + "\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        store.clear();
    }

    @Override
    public void update(Item item, int quantity) {
        Item updateItem = findByNameAndPromotion(item.getName(), item.getPromotion());
        // 추후 구현
    }

    @Override
    public List<Item> findByName(String name) {
        List<Item> items = new ArrayList<>();
        for (Item item : store) {
            if (item.getName().equals(name)) {
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public Item findByNameAndPromotion(String name, Promotion promotion) {
        for (Item item : store) {
            if (item.getName().equals(name) && item.getPromotion().equals(promotion)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<Item> getStore() {
        return store;
    }

    @Override
    public int getQuantityOfItem(String name, Promotion promotion) {
        Item item = findByNameAndPromotion(name, promotion);
        return item.getQuantity();
    }

    @Override
    public void updateRepository(List<Order> orders) {
        for (Order order : orders) {
            store.stream()
                .filter(item -> item.getName().equals(order.getItem().getName()))
                .findFirst()
                .ifPresent(item -> item.reduceQuantity(order.getQuantity()));
        }
    }
}
