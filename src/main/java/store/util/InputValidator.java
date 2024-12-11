package store.util;

import store.domain.Repository;

public class InputValidator {
    public static void validatePurchaseItem(String uncheckedInput) {
        String[] rawOrders = uncheckedInput.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");
            String itemName = parts[0];
            int count = Integer.parseInt(parts[1]);
            // repo에 있는 itemName인지 확인하기
            validateItemName(itemName);
            // count가 그 item의 총 개수보다 적은지 확인하기
            validateItemCount(itemName, count);
        }
    }

    public static void validateYesOrNo(String moreItemInput) {
        if (!moreItemInput.equals("Y") && !moreItemInput.equals("N")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ANSWER.getExceptionMessage());
        }
    }

    private static void validateItemName(String itemName) {
        if (!Repository.isInRepository(itemName)) {
            throw new IllegalArgumentException(ErrorMessage.NO_EXIST_ITEM.getExceptionMessage());
        }
    }

    private static void validateItemCount(String itemName, int count) {
        int totalCount = Repository.getTotalCount(itemName);
        if (totalCount < count) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_ITEM_COUNT_BOUNDARY.getExceptionMessage());
        }
    }
}
