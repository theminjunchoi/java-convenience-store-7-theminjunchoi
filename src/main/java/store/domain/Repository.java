package store.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final List<Category> promotions;
    private final List<Item> repository;

    public Repository() {
        this.promotions = new ArrayList<>();
        this.repository = new ArrayList<>();
    }

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
        promotions.add(new Category("no", 1, 0, null, null));
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
    }
}
