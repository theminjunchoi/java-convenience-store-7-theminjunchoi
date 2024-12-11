package store.controller;

import java.util.List;
import java.util.Map;
import store.domain.Category;
import store.domain.Customer;
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
        boolean isMembership = askMembership();
        outputView.printReceipt(items, isMembership);
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
