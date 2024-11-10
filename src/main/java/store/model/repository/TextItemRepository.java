package store.model.repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.model.item.Item;
import store.model.item.Promotion;
import store.service.order.Order;

public class TextItemRepository implements ItemRepository {
    private static final Path productsPath = Paths.get("src/main/resources/products.md");
    private static List<Item> store;
    private static final Path promotionsPath = Paths.get("src/main/resources/promotions.md");
    private static List<Promotion> promotions;

    @Override
    public void createRepository() {
        store = new ArrayList<>();
        promotions = new ArrayList<>();
        organizePromotions();
        try {
            List<String> stocks = Files.readAllLines(productsPath);
            for (int i = 1; i < stocks.size(); i++) {
                add(makeItem(stocks.get(i)));
                if (i != stocks.size()-1) {
                    checkZeroQuantity(stocks.get(i), stocks.get(i+1));
                } else if (i == stocks.size()-1) {
                    checkLastLine(stocks.get(i));
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkLastLine(String line) {
        String[] nowValues = line.split(",");
        if (!nowValues[3].equals("null")) {
            addZeroQuantityItem(nowValues);
        }
    }

    private void checkZeroQuantity(String line, String nextLine) {
        String[] nowValues = line.split(",");
        String[] nextValues = nextLine.split(",");
        if (!nowValues[3].equals("null") && !nowValues[0].equals(nextValues[0])) {
            addZeroQuantityItem(nowValues);
        }
    }

    private void addZeroQuantityItem(String[] nowValues) {
        String name = nowValues[0];
        int price = Integer.parseInt(nowValues[1]);
        int quantity = 0;
        Promotion promotion = promotions.stream()
                .filter(findPromotion -> findPromotion.getName().equals("null"))
                .findFirst()
                .orElse(null);
        add(organizeItem(name, price, quantity, promotion));
    }

    private Item makeItem (String line) {
        String[] values = line.split(",");
        String name = values[0];
        int price = Integer.parseInt(values[1]);
        int quantity = Integer.parseInt(values[2]);
        Promotion promotion = promotions.stream()
                .filter(findPromotion -> findPromotion.getName().equals(values[3]))
                .findFirst()
                .orElse(null);
        return organizeItem(name, price, quantity, promotion);
    }

    @Override
    public void organizePromotions() {
        Promotion noPromotion = new Promotion("null", 0, 0, null, null);
        promotions.add(noPromotion);
        try {
            List<String> rawPromotions = Files.readAllLines(promotionsPath);
            for (int i = 1; i < rawPromotions.size(); i++) {
                promotions.add(makePromotion(rawPromotions.get(i)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Promotion makePromotion(String line) {
        String[] values = line.split(",");
        String name = values[0];
        int buy = Integer.parseInt(values[1]);
        int get = Integer.parseInt(values[2]);
        LocalDate startDate = LocalDate.parse(values[3]);
        LocalDate endDate = LocalDate.parse(values[4]);
        return new Promotion(name, buy, get, startDate, endDate);
    }

    @Override
    public Item organizeItem(String name, int price, int quantity, Promotion promotion) {
        return new Item(name, price, quantity, promotion);
    }

    @Override
    public void add(Item item) {
        store.add(item);
    }

    // 개발 초기, 프로그램 실행 후 구매한만큼이 products.md에도 반영을 해야하는 줄 알고 만든 메소드
    // 지금은 사용 안함.
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
            int remainingQuantity = order.getQuantity();
            String itemName = order.getItem().getName();
            remainingQuantity = reduceQuantity(itemName, remainingQuantity, false);
            if (remainingQuantity > 0) {
                remainingQuantity = reduceQuantity(itemName, remainingQuantity, true);
            }
        }
    }

    private int reduceQuantity(String itemName, int remainingQuantity, boolean isNoPromotion) {
        for (Item item : store.stream().filter(item -> item.getName().equals(itemName) & (isNoPromotion == item.getPromotion().equals("null"))).toList()) {
            int itemQuantity = item.getQuantity();
            if (itemQuantity >= remainingQuantity) {
                item.setQuantity(itemQuantity - remainingQuantity);
                return 0;
            } else if (itemQuantity < remainingQuantity) {
                item.setQuantity(0);
                remainingQuantity -= itemQuantity;
            }
        }
        return remainingQuantity;
    }
}
