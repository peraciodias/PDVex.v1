package br.com.creativex.infrastructure.transaction;

import br.com.creativex.application.usecase.core.TransactionBoundary;

public class TransactionManagerBoundaryAdapter implements TransactionBoundary {

    private final TransactionManager transactionManager;

    public TransactionManagerBoundaryAdapter(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void begin() {
        transactionManager.begin();
    }

    @Override
    public void commit() {
        transactionManager.commit();
    }

    @Override
    public void rollback() {
        transactionManager.rollback();
    }
}
