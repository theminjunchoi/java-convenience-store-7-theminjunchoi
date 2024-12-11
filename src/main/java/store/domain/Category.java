package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;

public enum Category {
    private final String categoryName;
    private final int buy;
    private final int get;
    private final DateTimes startDay;
    private final DateTimes endDay;

    Category(String categoryName, int buy, int get, DateTimes startDay, DateTimes endDay) {
        this.categoryName = categoryName;
        this.buy = buy;
        this.get = get;
        this.startDay = startDay;
        this.endDay = endDay;
    }
}
