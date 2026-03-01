package br.com.creativex.presentation.controller;

import br.com.creativex.application.caixa.FinalizeVendaUseCase;
import br.com.creativex.domain.entity.venda.Venda;
import br.com.creativex.domain.repository.VendaRepository;
import br.com.creativex.infrastructure.persistence.repository.caixa.VendaDAO;

import java.sql.SQLException;

public class CaixaController {

    private final FinalizeVendaUseCase finalizeVendaUseCase;
    private final VendaRepository vendaRepository;
    private final VendaDAO vendaDAO;

    public CaixaController(FinalizeVendaUseCase finalizeVendaUseCase, VendaRepository vendaRepository, VendaDAO vendaDAO) {
        this.finalizeVendaUseCase = finalizeVendaUseCase;
        this.vendaRepository = vendaRepository;
        this.vendaDAO = vendaDAO;
    }

    public void finalizarVenda(Venda venda) throws SQLException {
        finalizeVendaUseCase.execute(venda);
    }

    public void cancelarVenda(long idVenda, long idUsuario) throws SQLException {
        vendaRepository.cancelarVenda(idVenda, idUsuario);
    }
}
