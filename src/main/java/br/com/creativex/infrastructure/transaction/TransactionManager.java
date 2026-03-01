// Peracio Dias
//creativex sistemas

package br.com.creativex.infrastructure.transaction;

import br.com.creativex.db.Conexao;

import java.sql.Connection;

public class TransactionManager {

    public <T> T execute(TransactionalOperation<T> operation) {

        Connection conn = null;

        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false);

            T result = operation.execute(conn);

            conn.commit();
            return result;

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            throw new RuntimeException("Erro na transação: " + e.getMessage(), e);

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void begin() {
    }

    public void commit() {
    }

    public void rollback() {
        
    }
}
