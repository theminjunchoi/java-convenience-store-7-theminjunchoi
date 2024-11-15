package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private final static String INPUT_PRODUCT_AND_COUNT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final static String INPUT_MORE_PRODUCT_AND_COUNT = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private final static String INPUT_NOT_DISCOUNT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private final static String INPUT_MEMBERSHIP = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private final static String INPUT_EXTRA_PURCHASE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public String getProductAndCount() {
        System.out.println(INPUT_PRODUCT_AND_COUNT);
        return Console.readLine();
    }

    public String getMoreItemAndCount(String name) {
        System.out.printf(INPUT_MORE_PRODUCT_AND_COUNT + "%n", name);
        return Console.readLine();
    }

    public String getNotDiscount(String product, int count) {
        System.out.printf(INPUT_NOT_DISCOUNT + "%n", product, count);
        return Console.readLine();
    }

    public String getMembership() {
        System.out.println(INPUT_MEMBERSHIP);
        return Console.readLine();
    }

    public String getExtraPurchase() {
        System.out.println(INPUT_EXTRA_PURCHASE);
        return Console.readLine();
    }
}
