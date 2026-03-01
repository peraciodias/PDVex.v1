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
    public void finalizarVenda(Venda venda) throws SQLException {
        // VendaDAO now accepts domain entities directly
        vendaDAO.finalizarVenda(venda);
    }

    @Override
    public void cancelarVenda(long idVenda, long idUsuario) throws SQLException {
        vendaDAO.cancelarVenda(idVenda, idUsuario);
    }
}
