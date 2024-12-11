package store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Category;
import store.domain.Customer;
import store.domain.Item;
import store.domain.Order;
import store.domain.Repository;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStore {
    private final InputView inputView;
    private final OutputView outputView;
    private final Repository repository;

    public ConvenienceStore() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.repository = new Repository();
    }

    public void run() {
        repository.organize();
        Customer customer = new Customer();
        outputView.welcome();
        outputView.showRepository(repository.show());
        customer.makeOrder(inputView.getPurchaseItem());
        Map<String, Integer> items = calculate(customer.chooseItem());
        List<Order> totalOrder = makeTotalOrder(items);
        List<Order> totalPromotionOrder = makeTotalPromotionOrder(items);
        boolean isMembership = askMembership();
        outputView.printReceipt(totalOrder, totalPromotionOrder, isMembership);
        repository.subtract(items);
    }

    private Map<String, Integer> calculate(Map<String, Integer> chooseItems) {
        for (String itemName : chooseItems.keySet()) {
            boolean isMoreItem = false;
            if (needMoreItem(chooseItems, itemName)) {
                isMoreItem = askMoreItem(itemName);
            }
            if (isMoreItem) {
                chooseItems.put(itemName, chooseItems.get(itemName)+1);
            }
        }
        return chooseItems;
    }

    private List<Order> makeTotalOrder(Map<String, Integer> items) {
        List<Order> orders = new ArrayList<>();
        for (String itemName : items.keySet()) {
            int price = findPrice(itemName);
            int count = items.get(itemName);
            orders.add(new Order(itemName, price, count));
        }
        return orders;
    }

    private int findPrice(String itemName) {
        return repository.findItemPrice(itemName);
    }

    private List<Order> makeTotalPromotionOrder(Map<String, Integer> items) {
        List<Order> promotionOrders = new ArrayList<>();
        for (String itemName : items.keySet()) {
            if (isPromotionItem(itemName)) {
                int price = findPrice(itemName);
                int count = items.get(itemName) / getUnitCount(itemName);
                promotionOrders.add(new Order(itemName, price, count));
            }
        }
        return promotionOrders;
    }

    private boolean isPromotionItem(String itemName) {
        Category itemCategory = repository.findCategory(itemName);
        if (!itemCategory.getCategoryName().equals("null")) {
            return true;
        }
        return false;
    }

    private Integer getUnitCount(String itemName) {
        Category itemCategory = repository.findCategory(itemName);
        int unitCount = itemCategory.getBuy() + itemCategory.getGet();
        return unitCount;
    }

    private boolean needMoreItem(Map<String, Integer> chooseItems, String itemName) {
        int count = chooseItems.get(itemName);
        Category itemCategory = repository.findCategory(itemName);
        int unitCount = itemCategory.getBuy() + itemCategory.getGet();
        if (count % unitCount != 0) {
            return true;
        }
        return false;
    }

    private boolean askMoreItem(String itemName) {
        return inputView.getMoreItem(itemName);
    }

    private boolean askMembership() {
        return inputView.getMembership();
    }
}
