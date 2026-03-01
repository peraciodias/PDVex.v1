// Peracio Dias
//creativex sistemas

package br.com.creativex.service;

import br.com.creativex.application.caixa.FinalizeVendaUseCase;
import br.com.creativex.domain.entity.venda.Venda;
import br.com.creativex.domain.repository.VendaRepository;
import br.com.creativex.infrastructure.persistence.repository.caixa.VendaDAO;
import br.com.creativex.infrastructure.persistence.repository.caixa.VendaRepositoryJdbcAdapter;

public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaDAO vendaDAO;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
        this.vendaRepository = new VendaRepositoryJdbcAdapter(vendaDAO);
    }

    public void finalizarVenda(Venda venda) {
        try {
            FinalizeVendaUseCase uc = new FinalizeVendaUseCase(vendaRepository);
            uc.execute(venda);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao finalizar venda", e);
        }
    }
}
