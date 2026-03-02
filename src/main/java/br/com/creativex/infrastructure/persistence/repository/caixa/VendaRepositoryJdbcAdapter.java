package br.com.creativex.infrastructure.persistence.repository.caixa;

import br.com.creativex.domain.entity.venda.Venda;
import br.com.creativex.domain.repository.VendaRepository;

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
}
