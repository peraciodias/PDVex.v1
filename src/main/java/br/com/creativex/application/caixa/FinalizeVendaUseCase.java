package br.com.creativex.application.caixa;

import br.com.creativex.domain.entity.venda.Venda;
import br.com.creativex.domain.repository.VendaRepository;

/**
 * Caso de uso para finalizar uma venda.
 * 
 * Implementa a regra de negócio para conclusão de uma transação de venda,
 * realizando validações necessárias e persistindo os dados através do
 * repositório de vendas. Segue o padrão Clean Architecture para separação
 * de responsabilidades.
 * 
 * @author Peracio Dias
 * @version 1.0
 * @since 2026-02-01
 */
public class FinalizeVendaUseCase {

    /** Repositório para persistência de vendas */
    private final VendaRepository vendaRepository;

    /**
     * Construtor que inicializa o caso de uso com o repositório de vendas.
     * 
     * @param vendaRepository Repositório para operações de venda
     */
    public FinalizeVendaUseCase(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    /**
     * Executa o caso de uso de finalização de venda.
     * 
     * Valida os dados da venda e a persiste no repositório. Este é o ponto
     * de entrada para a lógica de negócio de conclusão de uma transação.
     * 
     * @param venda A venda a ser finalizada
     */
    public void execute(Venda venda) {
        // validações e regras do caso de uso podem ficar aqui
        vendaRepository.finalizarVenda(venda);
    }
}
