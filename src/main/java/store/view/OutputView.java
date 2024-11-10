package store.view;

import java.text.NumberFormat;
import java.util.List;
import store.model.item.Item;
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
    private final static String PROMOTION_PRICE_FORMAT = "%-23s -%-7s\n";
    private final static String MEMBERSHIP_PRICE_FORMAT = "%-22s -%-7s\n";
    private final static String FINAL_PRICE_FORMAT = "%-24s %-7s\n";

    public void printWelcome() {
        System.out.println(WELCOME);
    }

    public void printItem(List<Item> store) {
        NumberFormat moneyFormat = NumberFormat.getInstance();
        for (Item item : store) {
            String quantityInfo = getQuantityInfo(item);
            String promotion = item.getPromotion().getName();
            if (promotion.equals("null")) {
                promotion = "";
            }
            System.out.printf(ITEM_CONTENTS, item.getName(), moneyFormat.format(item.getPrice()), quantityInfo, promotion);
        }
        System.out.print(System.lineSeparator());
    }

    private static String getQuantityInfo(Item item) {
        if (item.getQuantity() == 0) {
            return "재고 없음";
        }
        return item.getQuantity() + "개";
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printReceipt(List<Order> orders, List<Order> promotionOrders, int membershipDiscountPrice, int promotionDiscountPrice) {
        NumberFormat moneyFormat = NumberFormat.getInstance();
        printHeaders();
        int totalQuantity = 0;
        long totalPrice = 0;
        for (Order order : orders) {
            totalQuantity += order.getQuantity();
            int eachPrice = order.getProductPrice() * order.getQuantity();
            totalPrice += eachPrice;
            System.out.printf(ORDER_FORMAT, order.getName(), order.getQuantity(), moneyFormat.format(eachPrice));
        }
        printBody(promotionOrders, membershipDiscountPrice, promotionDiscountPrice, totalQuantity, moneyFormat,
                totalPrice);
    }

    private static void printHeaders() {
        System.out.println(RECEIPT_HEADER_1);
        System.out.printf(RECEIPT_HEADER_2, "상품명", "수량", "금액");
    }

    private static void printBody(List<Order> promotionOrders, int membershipDiscountPrice, int promotionDiscountPrice,
                                  int totalQuantity, NumberFormat moneyFormat, long totalPrice) {
        printPresentedItems(promotionOrders);
        printBeforeDiscount(totalQuantity, moneyFormat, totalPrice);
        printPromotionDiscountPrice(promotionDiscountPrice, moneyFormat);
        printMembershipDiscountPrice(membershipDiscountPrice, moneyFormat);
        printFinalPrice(membershipDiscountPrice, promotionDiscountPrice, moneyFormat, totalPrice);
    }

    private static void printPresentedItems(List<Order> promotionOrders) {
        System.out.println(PRESENTED);
        for (Order promotionOrder : promotionOrders) {
            System.out.printf(PROMOTION_ORDER_FORMAT, promotionOrder.getName(), promotionOrder.getQuantity());
        }
        System.out.println(LINE);
    }

    private static void printBeforeDiscount(int totalQuantity, NumberFormat moneyFormat, long totalPrice) {
        System.out.printf(TOTAL_PRICE_FORMAT, "총구매액", totalQuantity, moneyFormat.format(totalPrice));
    }

    private static void printPromotionDiscountPrice(int promotionDiscountPrice, NumberFormat moneyFormat) {
        System.out.printf(PROMOTION_PRICE_FORMAT, "행사할인", moneyFormat.format(promotionDiscountPrice));
    }

    private static void printMembershipDiscountPrice(int membershipDiscountPrice, NumberFormat moneyFormat) {
        System.out.printf(MEMBERSHIP_PRICE_FORMAT, "멤버십할인", moneyFormat.format(membershipDiscountPrice));
    }

    private static void printFinalPrice(int membershipDiscountPrice, int promotionDiscountPrice, NumberFormat moneyFormat,
                                        long totalPrice) {
        System.out.printf(FINAL_PRICE_FORMAT, "내실돈", moneyFormat.format(
                totalPrice - promotionDiscountPrice - membershipDiscountPrice));
        System.out.print(System.lineSeparator());
    }
}
