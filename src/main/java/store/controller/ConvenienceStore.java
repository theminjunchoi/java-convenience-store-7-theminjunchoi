package store.controller;

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
    }
}
