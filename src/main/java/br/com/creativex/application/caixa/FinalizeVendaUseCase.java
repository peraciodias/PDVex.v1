package br.com.creativex.application.caixa;

import br.com.creativex.domain.entity.venda.Venda;
import br.com.creativex.domain.repository.VendaRepository;

import java.sql.SQLException;

public class FinalizeVendaUseCase {

    private final VendaRepository vendaRepository;

    public FinalizeVendaUseCase(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public void execute(Venda venda) throws SQLException {
        // validações e regras do caso de uso podem ficar aqui
        vendaRepository.finalizarVenda(venda);
    }
}
