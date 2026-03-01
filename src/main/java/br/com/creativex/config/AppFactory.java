package br.com.creativex.config;

import br.com.creativex.infrastructure.persistence.repository.fornecedor.FornecedorDAO;
import br.com.creativex.infrastructure.persistence.repository.fornecedor.FornecedorRepositoryJdbcAdapter;
import br.com.creativex.presentation.controller.FornecedorController;
import br.com.creativex.infrastructure.persistence.repository.cliente.ClienteDAO;
import br.com.creativex.infrastructure.persistence.repository.cliente.ClienteRepositoryJdbcAdapter;
import br.com.creativex.presentation.controller.ClienteController;
import br.com.creativex.presentation.controller.ClientepjController;
import br.com.creativex.infrastructure.persistence.repository.produto.ProdutoDAO;
import br.com.creativex.infrastructure.persistence.repository.produto.ProdutoRepositoryJdbcAdapter;
import br.com.creativex.presentation.controller.ProdutoController;
import br.com.creativex.application.produto.CreateProdutoUseCase;
import br.com.creativex.domain.repository.ProdutoRepository;

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
        return new ProdutoController(useCase, repo, dao);
    }

    public static ClientepjController clientepjController() {
        br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjDAO dao = new br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjDAO();
        br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjRepositoryJdbcAdapter repo = new br.com.creativex.infrastructure.persistence.repository.clientepj.ClientepjRepositoryJdbcAdapter(dao);
        return new ClientepjController(repo);
    }
}
