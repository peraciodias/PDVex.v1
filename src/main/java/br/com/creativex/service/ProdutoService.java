// Peracio Dias
//creativex sistemas
package br.com.creativex.service;

import br.com.creativex.domain.entity.produto.Produto;
import br.com.creativex.domain.repository.ProdutoRepository;
import br.com.creativex.infrastructure.persistence.repository.produto.ProdutoDAO;
import br.com.creativex.infrastructure.persistence.repository.produto.ProdutoRepositoryJdbcAdapter;

import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
        this.repository = new ProdutoRepositoryJdbcAdapter(produtoDAO);
    }

    // constructor for DI/testing
    public ProdutoService(ProdutoRepository repository, ProdutoDAO produtoDAO) {
        this.repository = repository;
        this.produtoDAO = produtoDAO;
    }

    public Optional<Produto> buscarUltimo() {
        return repository.findLast();
    }

    public void inserir(Produto p) {
        repository.save(p);
    }

    public void atualizar(Produto p) {
        repository.save(p);
    }

    public void excluir(long id) {
        repository.deleteById(id);
    }

    public Optional<Produto> buscarPorId(long id) {
        return repository.findById(id);
    }

    public Optional<Produto> buscarPorNome(String nome) {
        return repository.findByNome(nome);
    }

    public Optional<Produto> buscarPorCodigoBarra(String codigo) {
        return repository.findByCodigoBarra(codigo);
    }

    public List<Produto> listarPorIdLimite(long id, int limite) {
        return repository.findByIdLimit(id, limite);
    }

    // Conveniência: versões compatíveis que retornam null quando não encontradas
    public Produto buscarUltimoCompat() {
        return buscarUltimo().orElse(null);
    }

    public Produto buscarPorIdCompat(long id) {
        return buscarPorId(id).orElse(null);
    }

    public Produto buscarPorNomeCompat(String nome) {
        return buscarPorNome(nome).orElse(null);
    }

    public Produto buscarPorCodigoBarraCompat(String codigo) {
        return buscarPorCodigoBarra(codigo).orElse(null);
    }
}
