// Peracio Dias
//creativex sistemas

/*com.creativex
 ├── dao
 ├── model
 ├── service
 ├── transaction   ← AQUI
 │     ├── TransactionalOperation.java
 │     └── TransactionManager.java
 └── ui
*/
package br.com.creativex.infrastructure.transaction;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionalOperation<T> {

    T execute(Connection conn) throws Exception;

}
