package store.util;

public class InputValidator {
    public static void validatePurchaseItem(String uncheckedInput) {
        String[] rawOrders = uncheckedInput.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");
            String itemName = parts[0];
            int count = Integer.parseInt(parts[1]);
            // repo에 있는 itemName인지 확인하기
            // count가 그 item의 총 개수보다 적은지 확인하기
        }
    }

    public static void validateYesOrNo(String moreItemInput) {
        if (!moreItemInput.equals("Y") && !moreItemInput.equals("N")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ANSWER.getExceptionMessage());
        }
    }
}
