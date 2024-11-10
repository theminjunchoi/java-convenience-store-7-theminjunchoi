package store.controller;

import static store.exception.ErrorMessage.INVALID_ANSWER;
import static store.exception.ErrorMessage.INVALID_ORDER;
import static store.exception.ErrorMessage.NO_EXIST_ITEM;
import static store.exception.ErrorMessage.OVER_QUANTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import store.model.item.Item;
import store.model.item.Promotion;
import store.model.repository.ItemRepository;
import store.model.repository.TextItemRepository;
import store.service.order.Order;
import store.service.order.OrderService;
import store.service.order.OrderServiceImpl;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStore {
    private final ItemRepository itemRepository;
    private final OrderService orderService;
    private final InputView inputView;
    private final OutputView outputView;
    private List<Order> orders;
    private List<Order> promotionOrders;
    private boolean isMembership;

    public ConvenienceStore() {
        this.itemRepository = new TextItemRepository();
        this.orderService = new OrderServiceImpl();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void shopping() {
        boolean goShopping = true;
        organizeStore();
        while (goShopping) {
            showStore();
            initializeOrder();
            purchaseItem();
            isMembership = getMembership();
            showReceipt(orders, promotionOrders, isMembership);
            goShopping = askAgain();
            itemRepository.updateRepository(orders);
        }
    }

    private void showStore() {
        outputView.printWelcome();
        outputView.printItem(itemRepository.getStore());
    }

    private void initializeOrder() {
        this.orders = new ArrayList<>();
        this.promotionOrders = new ArrayList<>();
        this.isMembership = false;
    }

    private void organizeStore() {
        itemRepository.createRepository();
    }

    private void purchaseItem() {
        while(true) {
            try {
                String rawOrders = inputView.getProductAndCount();
                makeOrders(rawOrders);
                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                outputView.printErrorMessage(INVALID_ORDER.getMessage());
            }
        }
        checkMoreItem();
    }

    private void makeOrders(String input) throws ArrayIndexOutOfBoundsException {
        String[] rawOrders = input.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            checkNameAndQuantity(name, quantity);
            List<Item> items = orderService.findByName(name);
            checkItemSize(items, quantity, name);
        }
    }

    private void checkItemSize(List<Item> items, int quantity, String name) {
        if (items.size() > 1) {
            int promotionCount = items.stream()
                    .filter(item -> !item.getPromotion().getName().equals("null"))
                    .mapToInt(Item::getQuantity)
                    .sum();
            if (quantity > promotionCount) {
                purchaseBoth(items, name, quantity, promotionCount);
            } else if (quantity <= promotionCount) {
                Order newOrder = orderService.createOrder(name, quantity);
                orders.add(newOrder);
            }
        } else if (items.size() == 1) {
            Order newOrder = orderService.createOrder(name, quantity);
            orders.add(newOrder);
        }
    }

    private void purchaseBoth(List<Item> items, String name, int quantity, int promotionCount) {
        Promotion promotion = findPromotion(items, false);
        Promotion nullPromotion = findPromotion(items, true);
        Order noPromotionorder = orderService.createOrderWithPromotion(name, quantity, promotion);
        noPromotionorder.setCheckMore(true);
        orders.add(noPromotionorder);
        Order promotionOrder = orderService.createOrderWithPromotion(name, quantity - promotionCount, nullPromotion);
        promotionOrder.setCheckMore(true);
        promotionOrders.add(promotionOrder);
        separatePromotion(name, quantity, promotionCount, promotion);
    }

    private void separatePromotion(String name, int quantity, int promotionCount, Promotion promotion) {
        if (promotion.getBuy()==2 && promotion.getGet()==1) {
            countMaxPromotion(promotionCount, 3, quantity, name);

        } else if (promotion.getBuy()==1 && promotion.getGet()==1) {
            countMaxPromotion(promotionCount, 2, quantity, name);
        }
    }

    private Promotion findPromotion(List<Item> items, boolean isNull) {
        if (isNull) {
            return items.stream()
                    .map(Item::getPromotion)
                    .filter(promotion -> promotion.getName().equals("null"))
                    .findAny()
                    .orElse(null);
        } else {
            return items.stream()
                    .map(Item::getPromotion)
                    .filter(promotion -> !promotion.getName().equals("null"))
                    .findAny()
                    .orElse(null);
        }
    }

    private void countMaxPromotion(int promotionCount, int divisor, int quantity, String name) {
        int maxPromotion = IntStream.range(1, promotionCount)
                .filter(num -> num % divisor == 0)
                .max()
                .orElse(1);
        int noPromotions = quantity - maxPromotion;
        String answer = inputView.getNotDiscount(name, noPromotions);
        if (!answer.equals("Y") && !answer.equals("N")) {
            outputView.printErrorMessage(INVALID_ANSWER.getMessage());
            purchaseItem();
        } else if (answer.equals("N")) {
            purchaseItem();
        }
    }

    private void checkMoreItem() {
        for (Order order : orders) {
            if (orderService.isTwoPlusOneMore(order)) {
                askTwoPlusOne(order);
            } else if (orderService.isOnePlusOneMore(order)) {
                askOnePlusOne(order);
            } else if (orderService.isTwoPlusOne(order)) {
                promotionOrders.add(new Order(order.getItem(), order.getQuantity()/3, order.getProductPrice(), order.getPromotion()));
            } else if (orderService.isOnePlusOne(order)) {
                promotionOrders.add(new Order(order.getItem(), order.getQuantity()/2, order.getProductPrice(), order.getPromotion()));
            }
        }
    }

    private void checkNameAndQuantity(String name, int quantity) {
        List<Item> items = orderService.findByName(name);
        if (items.isEmpty()) {
            outputView.printErrorMessage(NO_EXIST_ITEM.getMessage());
            purchaseItem();
        }
        int totalQuantity = items.stream().mapToInt(Item::getQuantity).sum();
        if (quantity > totalQuantity) {
            outputView.printErrorMessage(OVER_QUANTITY.getMessage());
            initializeOrder();
            purchaseItem();
        }
    }

    private boolean getMembership() {
        return getUserConfirmation(inputView::getMembership);
    }

    private boolean askAgain() {
        return getUserConfirmation(inputView::getExtraPurchase);

    }

    private boolean getUserConfirmation(Supplier<String> inputMethod) {
        while (true) {
            try {
                String answer = inputMethod.get();
                return getYOrN(answer);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean getYOrN(String answer) {
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        } else if (answer.equals("Y")) {
            return true;
        }
        return false;
    }

    private void showReceipt(List<Order> orders, List<Order> promotionOrders, boolean isMembership) {
        int membershipDiscountPrice = 0;
        if (isMembership) {
            membershipDiscountPrice = orderService.applyMembership(orders);
        }
        int promotionDiscountPrice = orderService.applyPromotion(promotionOrders);
        outputView.printReceipt(orders, promotionOrders, membershipDiscountPrice, promotionDiscountPrice);
    }

    private void askTwoPlusOne(Order order) {
        askPromotionConfirmation(order, 3);
    }

    private void askOnePlusOne(Order order) {
        askPromotionConfirmation(order, 2);
    }

    private void askPromotionConfirmation(Order order, int promotionDivider) {
        String answer = inputView.getMoreItemAndCount(order.getName());
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        }
        if (answer.equals("Y")) {
            orders.stream()
                    .filter(findOrder -> findOrder.getName().equals(order.getName()))
                    .findFirst()
                    .ifPresent(findOrder -> findOrder.setQuantity(findOrder.getQuantity() + 1));
            promotionOrders.add(new Order(order.getItem(), order.getQuantity() / promotionDivider, order.getProductPrice(), order.getPromotion()));
        }
    }
}