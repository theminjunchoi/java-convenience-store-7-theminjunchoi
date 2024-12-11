package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;

public class Category {
    private final String categoryName;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    Category(String categoryName, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.categoryName = categoryName;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Category of (List<Category> promotions, String categoryName) {
        Category category = promotions.stream()
                .filter(promotion -> promotion.categoryName.equals(categoryName))
                .findAny()
                .orElse(null);
        return category;
    }

    public String getCategoryName() {
        if (categoryName.equals("null")) {
            return "";
        }
        return categoryName;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Category)) {
            return false;
        }
        Category category = (Category) obj;
        return category.categoryName.equals(categoryName);
    }
}
