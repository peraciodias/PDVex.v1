package br.com.creativex.presentation.controller;

import br.com.creativex.application.produto.CreateProdutoUseCase;
import br.com.creativex.domain.entity.produto.Produto;
import br.com.creativex.domain.repository.ProdutoRepository;
import br.com.creativex.infrastructure.persistence.repository.produto.ProdutoDAO;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoController {

    private final CreateProdutoUseCase createProdutoUseCase;
    private final ProdutoRepository produtoRepository;
    private final ProdutoDAO produtoDAO;

    public ProdutoController(CreateProdutoUseCase createProdutoUseCase, ProdutoRepository produtoRepository, ProdutoDAO produtoDAO) {
        this.createProdutoUseCase = createProdutoUseCase;
        this.produtoRepository = produtoRepository;
        this.produtoDAO = produtoDAO;
    }

    public Produto criarProduto(Produto produto) {
        return createProdutoUseCase.execute(produto);
    }

    public Produto buscarUltimo() {
        br.com.creativex.model.produto.Produto m = produtoDAO.buscarUltimo();
        return m == null ? null : toDomain(m);
    }

    public Produto buscarPorId(long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto buscarPorNome(String nome) {
        br.com.creativex.model.produto.Produto m = produtoDAO.buscarPorNome(nome);
        return m == null ? null : toDomain(m);
    }

    public Produto buscarPorCodigoBarra(String codigo) {
        br.com.creativex.model.produto.Produto m = produtoDAO.buscarPorCodigoBarra(codigo);
        return m == null ? null : toDomain(m);
    }

    public List<Produto> listarPorIdLimite(long idInicial, int limite) {
        List<br.com.creativex.model.produto.Produto> modelos = produtoDAO.listarPorIdLimite(idInicial, limite);
        return modelos.stream().map(this::toDomain).collect(Collectors.toList());
    }

    public void inserir(Produto p) {
        criarProduto(p);
    }

    public void atualizar(Produto p) {
        produtoRepository.save(p);
    }

    public void excluir(long id) {
        produtoRepository.deleteById(id);
    }

    // mapeamento simples entre model e domain (duplicado do adapter)
    private Produto toDomain(br.com.creativex.model.produto.Produto m) {
        Produto p = new Produto();
        p.setId(m.getId());
        p.setCodigoBarra(m.getCodigoBarra());
        p.setDescricao(m.getDescricao());
        p.setMarca(m.getMarca());
        p.setAtributos(m.getAtributos());
        p.setUnidadeMedida(m.getUnidadeMedida());
        p.setCategoria(m.getCategoria());
        p.setCodGrupo(m.getCodGrupo());
        p.setGrupo(m.getGrupo());
        p.setTipoBalanca(m.getTipoBalanca());
        p.setQuantidadeEstoque(m.getQuantidadeEstoque());
        p.setPrecoCusto(m.getPrecoCusto());
        p.setPrecoVenda(m.getPrecoVenda());
        p.setNcm(m.getNcm());
        p.setCest(m.getCest());
        p.setCfopPadrao(m.getCfopPadrao());
        p.setUnidadeTributavel(m.getUnidadeTributavel());
        p.setCeanTributavel(m.getCeanTributavel());
        p.setCstIcms(m.getCstIcms());
        p.setAliquotaIcms(m.getAliquotaIcms());
        p.setCstPis(m.getCstPis());
        p.setPpis(m.getPpis());
        p.setCstCofins(m.getCstCofins());
        p.setPcofins(m.getPcofins());
        p.setDataCadastro(m.getDataCadastro());
        p.setLoja(m.getLoja());
        return p;
    }
}
