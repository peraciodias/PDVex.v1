package br.com.creativex.config;

import br.com.creativex.infrastructure.persistence.repository.fornecedor.FornecedorDAO;
import br.com.creativex.infrastructure.persistence.repository.fornecedor.FornecedorRepositoryJdbcAdapter;
import br.com.creativex.presentation.controller.FornecedorController;
import br.com.creativex.infrastructure.persistence.repository.caixa.VendaDAO;
import br.com.creativex.infrastructure.persistence.repository.caixa.VendaRepositoryJdbcAdapter;
import br.com.creativex.infrastructure.persistence.repository.cliente.ClienteDAO;
import br.com.creativex.infrastructure.persistence.repository.cliente.ClienteRepositoryJdbcAdapter;
import br.com.creativex.infrastructure.persistence.repository.usuario.UsuarioDAO;
import br.com.creativex.infrastructure.persistence.repository.usuario.UsuarioRepositoryJdbcAdapter;
import br.com.creativex.application.caixa.FinalizeVendaUseCase;
import br.com.creativex.application.usuario.UsuarioUseCases;
import br.com.creativex.presentation.controller.ClienteController;
import br.com.creativex.presentation.controller.ClientepjController;
import br.com.creativex.infrastructure.persistence.repository.produto.ProdutoDAO;
import br.com.creativex.infrastructure.persistence.repository.produto.ProdutoRepositoryJdbcAdapter;
import br.com.creativex.presentation.controller.CaixaController;
import br.com.creativex.presentation.controller.ProdutoController;
import br.com.creativex.presentation.controller.UsuarioController;
import br.com.creativex.presentation.controller.VendasConsultaController;
import br.com.creativex.application.produto.CreateProdutoUseCase;
import br.com.creativex.domain.repository.ProdutoRepository;
import br.com.creativex.domain.repository.VendaRepository;

public final class AppFactory {

    private AppFactory() {}

    public static ClienteController clienteController() {
        ClienteDAO dao = new ClienteDAO();
        ClienteRepositoryJdbcAdapter repo = new ClienteRepositoryJdbcAdapter(dao);
        return new ClienteController(repo);
    }

    public static FornecedorController fornecedorController() {
        FornecedorDAO dao = new FornecedorDAO();
        FornecedorRepositoryJdbcAdapter repo = new FornecedorRepositoryJdbcAdapter(dao);
        return new FornecedorController(repo);
    }

    public static ProdutoController produtoController() {
        ProdutoDAO dao = new ProdutoDAO();
        ProdutoRepositoryJdbcAdapter repo = new ProdutoRepositoryJdbcAdapter(dao);
        CreateProdutoUseCase useCase = new CreateProdutoUseCase(repo);
        return new ProdutoController(useCase, repo);
    }

    public static ClientepjController clientepjController() {
        br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjDAO dao = new br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjDAO();
        br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjRepositoryJdbcAdapter repo = new br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjRepositoryJdbcAdapter(dao);
        return new ClientepjController(repo);
    }

    public static CaixaController caixaController() {
        VendaDAO vendaDAO = new VendaDAO();
        VendaRepository vendaRepo = new VendaRepositoryJdbcAdapter(vendaDAO);
        FinalizeVendaUseCase finalizeUseCase = new FinalizeVendaUseCase(vendaRepo);
        return new CaixaController(finalizeUseCase, vendaRepo);
    }

    public static VendasConsultaController vendasConsultaController() {
        return new VendasConsultaController(caixaController());
    }

    public static UsuarioController usuarioController() {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioRepositoryJdbcAdapter repo = new UsuarioRepositoryJdbcAdapter(dao);
        UsuarioUseCases useCases = new UsuarioUseCases(repo);
        return new UsuarioController(useCases);
    }
}
