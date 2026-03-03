package br.com.creativex.infrastructure.persistence.repository.caixa;

import br.com.creativex.db.Conexao;
import br.com.creativex.domain.entity.venda.VendaResumo;
import br.com.creativex.domain.repository.VendaConsultaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaConsultaRepositoryJdbcAdapter implements VendaConsultaRepository {

    @Override
    public List<VendaResumo> listarVendas() {
        List<VendaResumo> vendas = new ArrayList<>();
        String sql = """
            SELECT v.id_venda, v.data_venda,
                   u.nome,
                   v.total_liquido,
                   v.status
            FROM tabela_vendas v
            JOIN tabela_usuarios u ON v.id_usuario = u.id
            ORDER BY v.data_venda DESC
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vendas.add(new VendaResumo(
                        rs.getLong("id_venda"),
                        rs.getTimestamp("data_venda"),
                        rs.getString("nome"),
                        rs.getBigDecimal("total_liquido"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas", e);
        }

        return vendas;
    }
}
