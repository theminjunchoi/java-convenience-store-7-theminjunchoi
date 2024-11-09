package store.view;

import java.text.NumberFormat;
import java.util.List;
import store.model.product.Item;
import store.model.product.Promotion;
import store.service.order.Order;

public class OutputView {
    private final static String WELCOME = "안녕하세요. W편의점입니다.\n" + "현재 보유하고 있는 상품입니다.\n";
    private final static String ITEM_CONTENTS = "- %s %s원 %s %s\n";
    private final static String RECEIPT_HEADER_1 = "==============W 편의점================";
    private final static String RECEIPT_HEADER_2 = "%-16s %-7s %-7s\n";
    private final static String PRESENTED = "=============증     정===============";
    private final static String LINE = "====================================";
    private final static String ORDER_FORMAT = "%-16s %-7d %-7s\n";
    private final static String PROMOTION_ORDER_FORMAT = "%-16s %-7d\n";
    private final static String TOTAL_PRICE_FORMAT = "%-16s %-7d %-7s\n";
    private final static String PROMOTION_PRICE_FORMAT = "%-23s -%-7d\n";
    private final static String MEMBERSHIP_PRICE_FORMAT = "%-23s -%-7d\n";
    private final static String FINAL_PRICE_FORMAT = "%-23s %-7d\n";

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
            String promotion = item.getPromotion().getName();
            if (promotion.equals("null")) {
                promotion = "";
            }
            System.out.printf(ITEM_CONTENTS, item.getName(), moneyFormat.format(item.getPrice()), quantityInfo, promotion);
        }
        System.out.print(System.lineSeparator());
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printReceipt(List<Order> orders, List<Order> promotionOrders, int membershipDiscountPrice, int promotionDiscountPrice) {
        NumberFormat moneyFormat = NumberFormat.getInstance();
        int totalQuantity = 0;
        long totalPrice = 0;
        System.out.println(RECEIPT_HEADER_1);
        System.out.printf(RECEIPT_HEADER_2, "상품명", "수량", "금액");
        for (Order order : orders) {
            totalQuantity += order.getQuantity();
            int eachPrice = order.getProductPrice() * order.getQuantity();
            totalPrice += eachPrice;
            System.out.printf(ORDER_FORMAT, order.getName(), order.getQuantity(), moneyFormat.format(eachPrice));
        }

        System.out.println(PRESENTED);
        for (Order promotionOrder : promotionOrders) {
            System.out.printf(PROMOTION_ORDER_FORMAT, promotionOrder.getName(), promotionOrder.getQuantity());
        }

        System.out.println(LINE);
        System.out.printf(TOTAL_PRICE_FORMAT, "총구매액", totalQuantity, totalPrice);

        // 행사 할인
        System.out.printf(PROMOTION_PRICE_FORMAT, "행사할인", promotionDiscountPrice);

        // 멤버십 할인
        System.out.printf(MEMBERSHIP_PRICE_FORMAT, "멤버십할인", membershipDiscountPrice);
        System.out.printf(FINAL_PRICE_FORMAT, "내실돈", totalPrice-promotionDiscountPrice-membershipDiscountPrice);
        System.out.print(System.lineSeparator());
    }


}
