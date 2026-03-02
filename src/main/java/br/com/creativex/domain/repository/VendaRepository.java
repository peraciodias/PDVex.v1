package br.com.creativex.domain.repository;

import br.com.creativex.domain.entity.venda.Venda;

public interface VendaRepository {
    void finalizarVenda(Venda venda);
    void cancelarVenda(long idVenda, long idUsuario);
}
