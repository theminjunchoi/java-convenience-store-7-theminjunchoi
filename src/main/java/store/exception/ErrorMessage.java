package store.exception;

public enum ErrorMessage {
    INVALID_ORDER("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_ANSWER("잘못된 입력입니다. 다시 입력해 주세요.");

    private final static String EXCEPTION_HEADER = "[ERROR] ";
    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return EXCEPTION_HEADER + message;
    }
}
