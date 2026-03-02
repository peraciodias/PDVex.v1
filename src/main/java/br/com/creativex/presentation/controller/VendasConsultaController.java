package br.com.creativex.presentation.controller;

import br.com.creativex.db.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendasConsultaController {

    private final CaixaController caixaController;

    public VendasConsultaController(CaixaController caixaController) {
        this.caixaController = caixaController;
    }

    public List<VendaResumo> listarVendas() throws SQLException {
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
        }

        return vendas;
    }

    public void cancelarVenda(long idVenda, long idUsuario) throws SQLException {
        caixaController.cancelarVenda(idVenda, idUsuario);
    }
}
