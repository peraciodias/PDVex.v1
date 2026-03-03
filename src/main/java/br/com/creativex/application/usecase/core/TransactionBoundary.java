package br.com.creativex.application.usecase.core;

public interface TransactionBoundary {
    void begin();
    void commit();
    void rollback();
}
