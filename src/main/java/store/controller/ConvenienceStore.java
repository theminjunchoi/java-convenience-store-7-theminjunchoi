package store.controller;

import static store.exception.ErrorMessage.INVALID_ANSWER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public ConvenienceStore() {
        this.itemRepository = new TextItemRepository();
        this.orderService = new OrderServiceImpl();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void shopping() {
        List<Order> orders = null;
        boolean goShopping = true;
        organizeStore();
        while (goShopping) {
            orders = purchaseProduct();
            isMembership(orders);
            //showReceipt(orders);
            goShopping = askAgain();
        }

    }

    private void organizeStore() {
        itemRepository.createRepository();
        outputView.printWelcome();
    }

    private List<Order> purchaseProduct() {
        String rawOrders = inputView.getProductAndCount();
        List<Order> orders = makeOrders(rawOrders);
        return orders;
    }

    private List<Order> makeOrders(String input) {
        List<Order> orders = null;
        String[] rawOrders = input.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");

            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);

            if (orderService.canOrder(name, quantity)) {
                orders = applyPromotion(name, quantity);
            }
        }
        return orders;
    }

    // TODO
    private List<Order> applyPromotion(String name, int quantity) {
        List<Order> orders = new ArrayList<>();
        List<Item> items = orderService.findByName(name);
        if (items.size() > 1) { //promotion인 것과 아닌 게 둘 다 있음
            addTwoTypeItem(name, quantity, items, orders);
        } else if (items.size() == 1) {
            addOneTypeItem(name, quantity, orders, items);
        }
        return orders;
    }

    private void addTwoTypeItem(String name, int quantity, List<Item> items, List<Order> orders) {
        int promotionQuantity = items.stream()
                .filter(item -> item.getPromotion() != Promotion.NO_PROMOTION)
                .mapToInt(Item::getQuantity)
                .sum();
        Optional<Promotion> itemPromotion = items.stream()
                .map(Item::getPromotion)
                .filter(promotion -> promotion != Promotion.NO_PROMOTION)
                .findFirst();
        if (quantity <= promotionQuantity) { // promotion인 것들만 사
            orders.add(orderService.createOrder(name, quantity, itemPromotion.orElse(null)));
        } else if (quantity > promotionQuantity) { // not promotion 인 것들에서도 사야돼
            orders.add(orderService.createOrder(name, promotionQuantity, itemPromotion.orElse(null)));
            orders.add(orderService.createOrder(name, quantity - promotionQuantity, itemPromotion.orElse(null)));
        }
    }

    private void addOneTypeItem(String name, int quantity, List<Order> orders, List<Item> items) {
        orders.add(orderService.createOrder(name, quantity, items.getFirst().getPromotion()));
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

    private void isMembership(List<Order> orders) {
        String answer = inputView.getMembership();
        if (!answer.equals("Y") && !answer.equals("N")) {
            throw new IllegalArgumentException(INVALID_ANSWER.getMessage());
        } else if (answer.equals("Y")) {
            applyMembership(orders);
        }
    }

    private void applyMembership(List<Order> orders) {
        orderService.applyMembership(orders);
    }
}
