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

    public ConvenienceStore() {
        this.itemRepository = new TextItemRepository();
        this.orderService = new OrderServiceImpl();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void shopping() {
        boolean goShopping = true;
        boolean isMembership;
        organizeStore();
        while (goShopping) {
            orders = purchaseItem();
            isMembership = getMembership();
            showReceipt(orders, isMembership);
            goShopping = askAgain();
        }

    }

    private void organizeStore() {
        itemRepository.createRepository();
        outputView.printWelcome();
        outputView.printItem(itemRepository.getStore());
    }

    private List<Order> purchaseItem() {
        String rawOrders = inputView.getProductAndCount();
        return makeOrders(rawOrders);
    }

    private List<Order> makeOrders(String input) {
        List<Order> orders = new ArrayList<>();
        String[] rawOrders = input.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");

            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);

            checkNameAndQuantity(name, quantity);

            orders.add(orderService.createOrder(name, quantity));
        }
        return orders;
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

    private void showReceipt(List<Order> orders, boolean isMembership) {
        outputView.printReceipt(orders);
    }
}
