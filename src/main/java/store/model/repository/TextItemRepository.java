package store.model.repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;
import store.service.order.Order;

public class TextItemRepository implements ItemRepository {
    private static final Path productsPath = Paths.get("src/main/resources/products.md");
    private static final List<Item> store = new ArrayList<>();
    private static final Path promotionsPath = Paths.get("src/main/resources/promotions.md");
    private static final List<Promotion> promotions = new ArrayList<>();

    @Override
    public void createRepository() {
        organizePromotions();
        try {
            List<String> stocks = Files.readAllLines(productsPath);
            for (int i = 1; i < stocks.size(); i++) {
                String line = stocks.get(i);
                String[] values = line.split(",");
                String name = values[0];
                int price = Integer.parseInt(values[1]);
                int quantity = Integer.parseInt(values[2]);
                Promotion promotion = promotions.stream()
                        .filter(findPromotion -> findPromotion.getName().equals(values[3]))
                        .findFirst()
                        .orElse(promotions.getFirst());
                add(organizeProduct(name, price, quantity, promotion));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void organizePromotions() {
        Promotion noPromotion = new Promotion("null", 0, 0, null, null);
        promotions.add(noPromotion);
        try {
            List<String> rawPromotions = Files.readAllLines(promotionsPath);
            for (int i = 1; i < rawPromotions.size(); i++) {
                String line = rawPromotions.get(i);
                String[] values = line.split(",");
                String name = values[0];
                int buy = Integer.parseInt(values[1]);
                int get = Integer.parseInt(values[2]);
                LocalDate startDate = LocalDate.parse(values[3]);
                LocalDate endDate = LocalDate.parse(values[4]);
                promotions.add(new Promotion(name, buy, get, startDate, endDate));
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
