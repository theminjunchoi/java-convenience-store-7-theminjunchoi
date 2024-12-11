package store.util;

public enum ErrorMessage {
    INVALID_ANSWER("잘못된 입력입니다. 다시 입력해 주세요."),
    NO_EXIST_ITEM("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OUT_OF_ITEM_COUNT_BOUNDARY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해주세요.");

    private final String exceptionMessage;
    private static final String ERROR_HEADER = "[ERROR] ";

    ErrorMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return ERROR_HEADER + this.exceptionMessage;
    }
}
