package store.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository {
    private static final List<Category> promotions = new ArrayList<>();
    private static final List<Item> repository = new ArrayList<>();

    public void organize() {
        // promotion.md 읽기
        organizePromotion();
        // product.md 읽기
        organizeItem();
    }

    private void organizePromotion() {
        Path promotionsPath = Paths.get("src/main/resources/promotions.md");
        try {
            List<String> rawPromotions = Files.readAllLines(promotionsPath);
            makePromotions(rawPromotions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makePromotions(List<String> rawPromotions) {
        promotions.add(new Category("null", 1, 0, null, null));
        for (int i = 1; i < rawPromotions.size(); i++) {
            promotions.add(makePromotion(rawPromotions.get(i)));
        }
    }

    private Category makePromotion(String line) {
        String[] values = line.split(",");
        String categoryName = values[0];
        int buy = Integer.parseInt(values[1]);
        int get = Integer.parseInt(values[2]);
        LocalDate startDate = LocalDate.parse(values[3]);
        LocalDate endDate = LocalDate.parse(values[4]);
        return new Category(categoryName, buy, get, startDate, endDate);
    }

    private void organizeItem() {
        Path itemsPath = Paths.get("src/main/resources/products.md");
        try {
            List<String> rawItems = Files.readAllLines(itemsPath);
            makeItems(rawItems);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeItems(List<String> rawItems) {
        for (int i = 1; i < rawItems.size()-1; i++) {
            Item item = makeItem(rawItems.get(i));
            repository.add(item);
            checkOnlyPromotionItem(rawItems, i);
        }
        Item item = makeItem(rawItems.get(rawItems.size()-1));
        repository.add(item);
    }

    private Item makeItem(String line) {
        String[] values = line.split(",");
        String itemName = values[0];
        int price = Integer.parseInt(values[1]);
        int count = Integer.parseInt(values[2]);
        Category category = Category.of(promotions, values[3]);
        return new Item(itemName, price, count, category);
    }

    private void checkOnlyPromotionItem(List<String> rawItems, int i) {
        String nowItemName = rawItems.get(i).split(",")[0];
        String nowItemCategoryName = rawItems.get(i).split(",")[3];
        String nextItemName = rawItems.get(i+1).split(",")[0];

        if (!nowItemCategoryName.equals("null") && !nowItemName.equals(nextItemName)) {
            addZeroItem(rawItems.get(i));
        }
    }

    private void addZeroItem(String line) {
        String[] values = line.split(",");
        String itemName = values[0];
        int price = Integer.parseInt(values[1]);
        int count = 0;
        Category category = Category.of(promotions, null);
        repository.add(new Item(itemName, price, count, category));
    }

    public List<Item> show() {
        return repository;
    }

    public static boolean isInRepository(String itemName) {
        return isContain(itemName);
    }

    private static boolean isContain(String itemName) {
        for (Item item : repository) {
            if (item.getItemName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public static int getTotalCount(String itemName) {
        return repository.stream()
                .filter(item -> item.getItemName().equals(itemName))
                .mapToInt(Item::getCount)
                .sum();
    }

    public Category findCategory(String itemName) {
        return repository.stream()
                .filter(item -> item.getItemName().equals(itemName))
                .findFirst()
                .map(Item::getCategory)
                .get();
    }

    public Integer findItemPrice(String itemName) {
        return repository.stream()
                .filter(item -> item.getItemName().equals(itemName))
                .map(Item::getPrice)
                .findFirst()
                .get();
    }

    public void subtract(Map<String, Integer> items) {
    }
}
