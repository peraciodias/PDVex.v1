package br.com.creativex.infrastructure.persistence.repository.caixa;

import br.com.creativex.domain.entity.venda.Venda;
import br.com.creativex.domain.repository.VendaRepository;
import br.com.creativex.db.Conexao;
import br.com.creativex.domain.entity.venda.ItemVenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaRepositoryJdbcAdapter implements VendaRepository {

    private final VendaDAO vendaDAO;

    public VendaRepositoryJdbcAdapter(VendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    @Override
    public void finalizarVenda(Venda venda) {
        try {
            vendaDAO.finalizarVenda(venda);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao finalizar venda", e);
        }
    }

    @Override
    public void cancelarVenda(long idVenda, long idUsuario) {
        try {
            vendaDAO.cancelarVenda(idVenda, idUsuario);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cancelar venda", e);
        }
    }
    @Override
    public Venda buscarPorIdComItens(long idVenda) {

        String sqlVenda = """
        SELECT v.id_venda,
               u.nome AS usuario,
               COALESCE(c.nome, v.nome_cliente_avulso, 'NÃO INFORMADO') AS cliente,
               v.total_liquido
        FROM tabela_vendas v
        JOIN tabela_usuarios u ON v.id_usuario = u.id
        LEFT JOIN tabela_clientes c ON c.id = v.id_cliente
        WHERE v.id_venda = ?
    """;

        String sqlItens = """
        SELECT descricao, quantidade, preco_venda
        FROM tabela_itens_venda
        WHERE id_venda = ?
    """;

        try (Connection conn = Conexao.getConnection()) {

            Venda venda = null;

            // 🔹 VENDA
            try (PreparedStatement ps = conn.prepareStatement(sqlVenda)) {
                ps.setLong(1, idVenda);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    venda = new Venda();

                    venda.setIdVenda(rs.getLong("id_venda")); // 🔥 CORRETO
                    venda.setTotalLiquido(rs.getBigDecimal("total_liquido"));

                    venda.setClienteNome(rs.getString("cliente"));
                    venda.setUsuarioNome(rs.getString("usuario"));
                }
            }

            // 🔹 ITENS
            if (venda != null) {
                try (PreparedStatement ps = conn.prepareStatement(sqlItens)) {
                    ps.setLong(1, idVenda);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        ItemVenda item = new ItemVenda(
                                0L,
                                rs.getString("descricao"),
                                rs.getBigDecimal("quantidade"),
                                rs.getBigDecimal("preco_venda")
                        );

                        venda.getItens().add(item);
                    }
                }
            }

            return venda;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar venda", e);
        }
    }
}

