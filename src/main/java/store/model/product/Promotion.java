
package store.model.product;

public enum Promotion {
    SOFT_DRINK,
    MD_RECOMMENDATION,
    FLASH_DISCOUNT,
    NO_PROMOTION;

    public static Promotion getPromotion(String promotion) {
        if (promotion.equals("탄산2+1")) {
            return SOFT_DRINK;
        } else if (promotion.equals("MD추천상품")) {
            return MD_RECOMMENDATION;
        } else if (promotion.equals("반짝할인")) {
            return FLASH_DISCOUNT;
        } else if (promotion.isBlank()) {
            return NO_PROMOTION;
        }
        return NO_PROMOTION;
    }

    @Override
    public String toString() {
        if (this == SOFT_DRINK) {
            return "탄산2+1";
        } else if (this == MD_RECOMMENDATION) {
            return "MD추천상품";
        } else if (this == FLASH_DISCOUNT) {
            return "반짝할인";
        } else if (this == NO_PROMOTION) {
            return "null";
        }
        return "null";
    }
}
