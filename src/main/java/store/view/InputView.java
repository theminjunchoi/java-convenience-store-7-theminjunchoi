package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.util.InputValidator;

public class InputView {
    private static final String INPUT_PURCHASE_ITEM = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final static String INPUT_MORE_ITEM = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private final static String INPUT_NOT_DISCOUNT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private final static String INPUT_MEMBERSHIP = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private final static String INPUT_EXTRA_PURCHASE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public String getPurchaseItem() {
        System.out.println(INPUT_PURCHASE_ITEM);
        String purchaseItemInput = Console.readLine();
        InputValidator.validatePurchaseItem(purchaseItemInput);
        return purchaseItemInput;
    }

    public boolean getMoreItem(String itemName) {
        System.out.printf(INPUT_MORE_ITEM, itemName);
        String moreItemInput = Console.readLine();
        InputValidator.validateYesOrNo(moreItemInput);
        return moreItemInput.equals("Y");
    }

    public boolean getNotDiscount(String itemName, int count) {
        System.out.printf(INPUT_NOT_DISCOUNT, itemName, count);
        String moreItemInput = Console.readLine();
        InputValidator.validateYesOrNo(moreItemInput);
        return moreItemInput.equals("Y");
    }

    public boolean getMembership() {
        System.out.printf(INPUT_MEMBERSHIP);
        String moreItemInput = Console.readLine();
        InputValidator.validateYesOrNo(moreItemInput);
        return moreItemInput.equals("Y");
    }

    public boolean getExtraPurchase() {
        System.out.printf(INPUT_EXTRA_PURCHASE);
        String moreItemInput = Console.readLine();
        InputValidator.validateYesOrNo(moreItemInput);
        return moreItemInput.equals("Y");
    }
}
