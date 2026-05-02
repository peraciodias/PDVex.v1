package br.com.creativex.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gerenciador de conexões com o banco de dados PostgreSQL.
 * 
 * Classe utilitária responsável pelo gerenciamento de conexões JDBC com
 * banco de dados PostgreSQL. Encapsula as configurações de conexão
 * (URL, usuário, senha) em um único ponto de acesso.
 * 
 * NOTA: As credenciais devem ser configuradas nas constantes privadas
 * antes de executar a aplicação.
 * 
 * @author Peracio Dias
 * @version 1.0
 * @since 2026-02-01
 */
public class Conexao {
    // 1. Alterado de 'mysql' para 'postgresql'
    // 2. Alterada a porta de '3306' para '5432'
    // 3. Removidos parâmetros específicos do MySQL (?useSSL...)
    private static final String URL = "jdbc:postgresql://localhost:5432/bco_dados_mercado";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; // 🔹 Coloque a senha do usuário 'pera' definida no Postgres

    /**
     * Obtém uma conexão ativa com o banco de dados PostgreSQL.
     * 
     * Carrega o driver JDBC do PostgreSQL e estabelece uma conexão usando
     * as credenciais configuradas nas constantes de classe.
     * 
     * @return Uma instância ativa de {@link Connection} com o banco de dados
     * @throws SQLException Se falhar ao obter a conexão ou driver não for encontrado
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Garante que o driver do PostgreSQL esteja carregado (opcional em versões recentes do JDBC)
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado!", e);
        }
    }
}
