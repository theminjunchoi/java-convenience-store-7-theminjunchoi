package store.controller;

import static store.exception.ErrorMessage.INVALID_ANSWER;
import static store.exception.ErrorMessage.NO_EXIST_ITEM;
import static store.exception.ErrorMessage.OVER_QUANTITY;

import java.util.ArrayList;
import java.util.List;
import store.model.product.Item;
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
            organizeStore();
            initializeOrder();
            purchaseItem();
            isMembership = getMembership();
            showReceipt(orders, promotionOrders, isMembership);
            goShopping = askAgain();
        }
    }

    private void initializeOrder() {
        this.orders = new ArrayList<>();
        this.promotionOrders = new ArrayList<>();
        this.isMembership = false;
    }

    private void organizeStore() {
        itemRepository.createRepository();
        outputView.printWelcome();
        outputView.printItem(itemRepository.getStore());
    }

    private void purchaseItem() {
        String rawOrders = inputView.getProductAndCount();
        makeOrders(rawOrders);
        checkMoreItem();
    }

    private void makeOrders(String input) {
        String[] rawOrders = input.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);

            checkNameAndQuantity(name, quantity);

            Order newOrder = orderService.createOrder(name, quantity);
            orders.add(newOrder);
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
        outputView.printReceipt(orders, promotionOrders, isMembership);
    }

    private void askTwoPlusOne(Order order) {
        String answer = inputView.getMoreItemAndCount(order.getName());
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        } else if (answer.equals("Y")) {
            orders.stream()
                    .filter(findOrder -> findOrder.getName().equals(order.getName()))
                    .findFirst()
                    .ifPresent(findOrder -> findOrder.setQuantity(findOrder.getQuantity() + 1));
            promotionOrders.add(new Order(order.getItem(), order.getQuantity()/3, order.getProductPrice(), order.getPromotion()));
        }
    }

    private void askOnePlusOne(Order order) {
        String answer = inputView.getMoreItemAndCount(order.getName());
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        } else if (answer.equals("Y")) {
            orders.stream()
                    .filter(findOrder -> findOrder.getName().equals(order.getName()))
                    .findFirst()
                    .ifPresent(findOrder -> findOrder.setQuantity(findOrder.getQuantity() + 1));
            promotionOrders.add(new Order(order.getItem(), order.getQuantity()/2, order.getProductPrice(), order.getPromotion()));
        }
    }


}
