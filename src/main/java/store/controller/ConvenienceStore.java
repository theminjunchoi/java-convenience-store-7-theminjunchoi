package store.controller;

import static store.exception.ErrorMessage.INVALID_ANSWER;
import static store.exception.ErrorMessage.NO_EXIST_ITEM;
import static store.exception.ErrorMessage.OVER_QUANTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.model.product.Item;
import store.model.product.Promotion;
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

    public ConvenienceStore() {
        this.itemRepository = new TextItemRepository();
        this.orderService = new OrderServiceImpl();
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.orders = new ArrayList<>();
        this.promotionOrders = new ArrayList<>();
    }

    public void shopping() {
        boolean goShopping = true;
        boolean isMembership;
        organizeStore();
        while (goShopping) {
            purchaseItem();
            isMembership = getMembership();
            showReceipt(orders, promotionOrders, isMembership);
            goShopping = askAgain();
        }
    }

    private void organizeStore() {
        itemRepository.createRepository();
        outputView.printWelcome();
        outputView.printItem(itemRepository.getStore());
    }

    private void purchaseItem() {
        String rawOrders = inputView.getProductAndCount();
        makeOrders(rawOrders);
    }

    private void makeOrders(String input) {
        String[] rawOrders = input.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            checkNameAndQuantity(name, quantity);
            orders.add(orderService.createOrder(name, quantity));
        }

        checkMoreItem();
    }

    private void calculatePromotion() {
        for (Order order : orders) {
            if (!order.getCheckPromotion()) {
                int quantity = orders.stream()
                        .filter(promotionOrder -> promotionOrder.getName().equals(order.getName()))
                        .map(Order::getQuantity)
                        .findFirst()
                        .orElse(0);
                if (order.getPromotion() == Promotion.SOFT_DRINK) {
                    int promotionItemCount = quantity / 3 + 1;
                    promotionOrders.add(new Order(order.getItem(), promotionItemCount, order.getProductPrice(), order.getPromotion()));
                } else if (order.getPromotion() == Promotion.MD_RECOMMENDATION || order.getPromotion() == Promotion.FLASH_DISCOUNT) {
                    int promotionItemCount = quantity / 2 + 1;
                    promotionOrders.add(new Order(order.getItem(), promotionItemCount, order.getProductPrice(), order.getPromotion()));
                }
                order.setCheckPromotion(true);
            }

        }
    }

    private void checkMoreItem() {
        for (Order order : orders) {
            if(!order.getCheckPromotion()) {
                if (order.getPromotion() == Promotion.SOFT_DRINK && order.getQuantity() % 3 == 2 && (itemRepository.getQuantityOfItem(order.getName(), order.getPromotion()) - order.getQuantity()) >= 1) {
                    // plus 1
                    askPlusOne(order);
                } else if (order.getPromotion() == Promotion.MD_RECOMMENDATION && order.getQuantity() % 2 == 1  && (itemRepository.getQuantityOfItem(order.getName(), order.getPromotion()) - order.getQuantity()) >= 1) {
                    // plus 1
                    askPlusOne(order);
                } else if (order.getPromotion() == Promotion.FLASH_DISCOUNT && order.getQuantity() % 2 == 1  && (itemRepository.getQuantityOfItem(order.getName(), order.getPromotion()) - order.getQuantity()) >= 1) {
                    // plus 1
                    askPlusOne(order);
                }
            }
        }
    }

    private void checkNameAndQuantity(String name, int quantity) {
        List<Item> items = orderService.findByName(name);
        if (items.isEmpty()) {
            throw new IllegalArgumentException(NO_EXIST_ITEM.getMessage());
        }
        int totalQuantity = items.stream().mapToInt(Item::getQuantity).sum();
        if (quantity > totalQuantity) {
            throw new IllegalArgumentException(OVER_QUANTITY.getMessage());
        }
    }

    private boolean getMembership() {
        String answer = inputView.getMembership();
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        } else if (answer.equals("Y")) {
            return true;
        }
        return false;
    }

    private boolean askAgain() {
        String answer = inputView.getExtraPurchase();
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        } else if (answer.equals("Y")) {
            return true;
        }
        return false;
    }

    private void showReceipt(List<Order> orders, List<Order> promotionOrders, boolean isMembership) {
        outputView.printReceipt(orders, promotionOrders);
    }

    private void askPlusOne(Order order) {
        String answer = inputView.getMoreItemAndCount(order.getName());
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        } else if (answer.equals("Y")) {
            calculatePromotion();
//            Optional<Integer> quantity = promotionOrders.stream()
//                    .filter(promotionOrder -> promotionOrder.getName().equals(order.getName()))
//                    .map(Order::getQuantity)
//                    .findFirst();
//            promotionOrders.stream()
//                    .filter(promotionOrder -> promotionOrder.getName().equals(order.getName()))
//                    .forEach(promotionOrder -> promotionOrder.setQuantity(quantity.orElse(0)+1));
        }
    }
}
