package store.util;

public enum ErrorMessage {
    INVALID_ANSWER("잘못된 입력입니다. 다시 입력해 주세요.");

    private final String exceptionMessage;
    private static final String ERROR_HEADER = "[ERROR] ";

    ErrorMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return ERROR_HEADER + this.exceptionMessage;
    }
}
