package br.com.creativex.domain.repository;

import br.com.creativex.domain.entity.produto.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    Optional<Produto> findById(long id);
    List<Produto> findAll();
    Produto save(Produto produto);
    void deleteById(long id);
    Optional<Produto> findByCodigoBarra(String codigo);
    Optional<Produto> findByNome(String nome);
    Optional<Produto> findLast();
    List<Produto> findByIdLimit(long idInicial, int limite);
}
