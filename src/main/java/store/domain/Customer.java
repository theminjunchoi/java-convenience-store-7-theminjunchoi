package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final List<Item> purchaseItems;

    public Customer() {
        this.purchaseItems = new ArrayList<>();
    }
}
