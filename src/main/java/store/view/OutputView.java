package store.view;

import java.text.NumberFormat;
import java.util.List;
import store.domain.Item;
import store.domain.Order;

public class OutputView {
    private final static String WELCOME = "안녕하세요. W편의점입니다.\n" + "현재 보유하고 있는 상품입니다.\n";
    private final static String ITEM_FORMAT = "- %s %s원 %d개 %s\n";
    private final static String ZERO_ITEM_FORMAT = "- %s %s원 재고 없음\n";
    private final static String RECEIPT_HEADER_1 = "==============W 편의점================";
    private final static String RECEIPT_HEADER_2 = "%-16s %-7s %-7s\n";
    private final static String PRESENTED = "=============증     정===============";
    private final static String LINE = "====================================";
    private final static String ORDER_FORMAT = "%-16s %-7d %-7s\n";
    private final static String PROMOTION_ORDER_FORMAT = "%-16s %-7d\n";
    private final static String TOTAL_PRICE_FORMAT = "%-16s %-7d %-7s\n";
    private final static String PROMOTION_PRICE_FORMAT = "%-23s -%-7s\n";
    private final static String MEMBERSHIP_PRICE_FORMAT = "%-22s -%-7s\n";
    private final static String FINAL_PRICE_FORMAT = "%-24s %-7s\n";

    public void welcome() {
        System.out.println(WELCOME);
    }

    public void showRepository(List<Item> repository) {
        NumberFormat moneyFormat = NumberFormat.getInstance();
        for (Item item : repository) {
            if (item.getCount() == 0) {
                System.out.printf(ZERO_ITEM_FORMAT, item.getItemName(), moneyFormat.format(item.getPrice()));
                continue;
            }
            System.out.printf(ITEM_FORMAT, item.getItemName(), moneyFormat.format(item.getPrice()), item.getCount(), item.getCategoryName());
        }
    }


    public void printReceipt(List<Order> totalOrder, List<Order> totalPromotionOrder, boolean isMembership) {
        NumberFormat moneyFormat = NumberFormat.getInstance();
        printHeaders();
        for (Order order : totalOrder) {
            System.out.printf(ORDER_FORMAT, order.getItemName(), order.getCount(), moneyFormat.format(
                    (long) order.getCount() * order.getPrice()));
        }
        System.out.println(PRESENTED);
        for (Order promotionOrder : totalPromotionOrder) {
            System.out.printf(PROMOTION_ORDER_FORMAT, promotionOrder.getItemName(), promotionOrder.getCount());
        }

    }

    private void printHeaders() {
        System.out.println(RECEIPT_HEADER_1);
        System.out.printf(RECEIPT_HEADER_2, "상품명", "수량", "금액");
    }
}
