package br.com.creativex.infrastructure.persistence.repository.produto;

import br.com.creativex.domain.entity.produto.Produto;
import br.com.creativex.domain.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepositoryJdbcAdapter implements ProdutoRepository {

    private final ProdutoDAO produtoDAO;

    public ProdutoRepositoryJdbcAdapter(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    @Override
    public Optional<Produto> findById(long id) {
        br.com.creativex.model.produto.Produto m = produtoDAO.buscarPorId(id);
        if (m == null) return Optional.empty();
        return Optional.of(toDomain(m));
    }

    @Override
    public Optional<Produto> findByCodigoBarra(String codigo) {
        br.com.creativex.model.produto.Produto m = produtoDAO.buscarPorCodigoBarra(codigo);
        return m == null ? Optional.empty() : Optional.of(toDomain(m));
    }

    @Override
    public Optional<Produto> findByNome(String nome) {
        br.com.creativex.model.produto.Produto m = produtoDAO.buscarPorNome(nome);
        return m == null ? Optional.empty() : Optional.of(toDomain(m));
    }

    @Override
    public Optional<Produto> findLast() {
        br.com.creativex.model.produto.Produto m = produtoDAO.buscarUltimo();
        return m == null ? Optional.empty() : Optional.of(toDomain(m));
    }

    @Override
    public List<Produto> findAll() {
        // ProdutoDAO does not provide a direct listAll method in all projects; try listarPorIdLimite as example
        List<br.com.creativex.model.produto.Produto> modelos = produtoDAO.listarPorIdLimite(0, 1000);
        List<Produto> dominios = new ArrayList<>();
        for (br.com.creativex.model.produto.Produto m : modelos) dominios.add(toDomain(m));
        return dominios;
    }

    @Override
    public List<Produto> findByIdLimit(long idInicial, int limite) {
        List<br.com.creativex.model.produto.Produto> modelos = produtoDAO.listarPorIdLimite(idInicial, limite);
        List<Produto> dominios = new ArrayList<>();
        for (br.com.creativex.model.produto.Produto m : modelos) dominios.add(toDomain(m));
        return dominios;
    }

    @Override
    public Produto save(Produto produto) {
        // converte para model e usa DAO inserir/atualizar
        br.com.creativex.model.produto.Produto m = toModel(produto);
        if (produto.getId() <= 0) {
            produtoDAO.inserir(m);
        } else {
            produtoDAO.atualizar(m);
        }
        // após salvar, buscar último ou por id pode ser necessário; retornamos o domínio recebido
        return produto;
    }

    @Override
    public void deleteById(long id) {
        produtoDAO.excluir(id);
    }

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

    private br.com.creativex.model.produto.Produto toModel(Produto p) {
        br.com.creativex.model.produto.Produto m = new br.com.creativex.model.produto.Produto();
        m.setId(p.getId());
        m.setCodigoBarra(p.getCodigoBarra());
        m.setDescricao(p.getDescricao());
        m.setMarca(p.getMarca());
        m.setAtributos(p.getAtributos());
        m.setUnidadeMedida(p.getUnidadeMedida());
        m.setCategoria(p.getCategoria());
        m.setCodGrupo(p.getCodGrupo());
        m.setGrupo(p.getGrupo());
        m.setTipoBalanca(p.getTipoBalanca());
        m.setQuantidadeEstoque(p.getQuantidadeEstoque());
        m.setPrecoCusto(p.getPrecoCusto());
        m.setPrecoVenda(p.getPrecoVenda());
        m.setNcm(p.getNcm());
        m.setCest(p.getCest());
        m.setCfopPadrao(p.getCfopPadrao());
        m.setUnidadeTributavel(p.getUnidadeTributavel());
        m.setCeanTributavel(p.getCeanTributavel());
        m.setCstIcms(p.getCstIcms());
        m.setAliquotaIcms(p.getAliquotaIcms());
        m.setCstPis(p.getCstPis());
        m.setPpis(p.getPpis());
        m.setCstCofins(p.getCstCofins());
        m.setPcofins(p.getPcofins());
        m.setDataCadastro(p.getDataCadastro());
        m.setLoja(p.getLoja());
        return m;
    }
}
