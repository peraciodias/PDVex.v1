package br.com.creativex.domain.transaction;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionalOperation<T> {
    T execute(Connection conn) throws Exception;
}
