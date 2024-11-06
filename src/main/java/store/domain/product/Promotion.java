package store.domain.product;

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
}
