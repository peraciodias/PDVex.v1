package br.com.creativex.application.usecase.core;

import br.com.creativex.infrastructure.transaction.TransactionManager;

public abstract class TransactionalUseCase<I, O> implements UseCase<I, O> {

    private final TransactionManager tx;

    protected TransactionalUseCase(TransactionManager tx) {
        this.tx = tx;
    }

    @Override
    public O execute(I input) {
        try {
            tx.begin();
            O output = doExecute(input);
            tx.commit();
            return output;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    protected abstract O doExecute(I input);
}