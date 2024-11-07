package store.view;

import java.text.NumberFormat;
import java.util.List;
import store.model.product.Item;

public class OutputView {
    private final static String WELCOME = "안녕하세요. W편의점입니다.\n"
            + "현재 보유하고 있는 상품입니다.\n";
    private final static String ITEM_CONTENTS = "- %s %s원 %s %s\n";

    public void printWelcome() {
        System.out.println(WELCOME);
    }

    public void printItem(List<Item> store) {
        NumberFormat moneyFormat = NumberFormat.getInstance();
        for (Item item : store) {
            String quantityInfo;
            if (item.getQuantity() == 0) {
                quantityInfo = "재고 없음";
            } else {
                quantityInfo = item.getQuantity() + "개";
            }
            System.out.printf(ITEM_CONTENTS,
                    item.getName(),
                    moneyFormat.format(item.getPrice()),
                    quantityInfo,
                    item.getPromotion());
        }
    }
}
