package store.view;

import java.util.List;
import java.util.Map;
import store.domain.Item;

public class OutputView {
    private final static String WELCOME = "안녕하세요. W편의점입니다.\n" + "현재 보유하고 있는 상품입니다.\n";
    private final static String ITEM_FORMAT = "- %s %d원 %d개 %s\n";
    private final static String ZERO_ITEM_FORMAT = "- %s %d원 재고 없음\n";


    public void welcome() {
        System.out.println(WELCOME);
    }

    public void showRepository(List<Item> repository) {
        for (Item item : repository) {
            if (item.getCount() == 0) {
                System.out.printf(ZERO_ITEM_FORMAT, item.getItemName(), item.getPrice());
                continue;
            }
            System.out.printf(ITEM_FORMAT, item.getItemName(), item.getPrice(), item.getCount(), item.getCategoryName());
        }
    }

    public void printReceipt(Map<String, Integer> items, boolean isMembership) {
    }
}
