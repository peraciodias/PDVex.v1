package br.com.creativex.domain.repository;

import br.com.creativex.domain.entity.venda.Venda;
import java.sql.SQLException;

public interface VendaRepository {
    void finalizarVenda(Venda venda) throws SQLException;
    void cancelarVenda(long idVenda, long idUsuario) throws SQLException;
}
