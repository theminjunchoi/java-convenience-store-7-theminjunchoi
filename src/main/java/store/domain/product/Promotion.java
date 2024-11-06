package store.model.product;

public enum Promotion {
    SOFT_DRINK,
    TWO_PLUS_ONE,
    MD_RECOMMENDATION,
    FLASH_DISCOUNT,
    NO_PROMOTION;

    public static Promotion getPromotion(String promotion) {
        if (promotion.equals("soft_drink")) {
            return SOFT_DRINK;
        } else if (promotion.equals("two_plus_one")) {
            return TWO_PLUS_ONE;
        } else if (promotion.equals("md_recommendation")) {
            return MD_RECOMMENDATION;
        } else if (promotion.equals("flash_discount")) {
            return FLASH_DISCOUNT;
        } else if (promotion.isBlank()) {
            return NO_PROMOTION;
        }
    }
}
