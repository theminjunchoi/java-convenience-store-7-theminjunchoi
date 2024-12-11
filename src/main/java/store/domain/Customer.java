package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
    private final Map<String, Integer> purchaseItems;

    public Customer() {
        this.purchaseItems = new HashMap<>();
    }

    public void makeOrder(String purchaseItem) {
        String[] rawOrders = purchaseItem.split(",");
        for (String rawOrder : rawOrders) {
            String[] parts = rawOrder.replaceAll("[\\[\\]]", "").split("-");
            String itemName = parts[0];
            int count = Integer.parseInt(parts[1]);
            Map<String, Integer> wantItem = new HashMap<>();
            wantItem.put(itemName, count);
        }
    }

    public Map<String, Integer> chooseItem() {
        return purchaseItems;
    }
}
