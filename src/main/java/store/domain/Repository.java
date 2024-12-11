package store.domain;

import java.util.List;

public class Repository {
    private final List<Item> repository;

    public Repository(List<Item> repository) {
        this.repository = repository;
    }
}
