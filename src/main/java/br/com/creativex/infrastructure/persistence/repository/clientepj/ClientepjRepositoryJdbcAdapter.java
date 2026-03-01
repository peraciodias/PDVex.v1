package br.com.creativex.infrastructure.persistence.repository.clientepj;

import br.com.creativex.domain.entity.clientepj.Clientepj;
import br.com.creativex.domain.repository.ClientepjRepository;

import java.util.List;

public class ClientepjRepositoryJdbcAdapter implements ClientepjRepository {

    private final ClientepjDAO dao;

    public ClientepjRepositoryJdbcAdapter(ClientepjDAO dao) {
        this.dao = dao;
    }

    @Override
    public Clientepj save(Clientepj c) throws Exception {
        if (c.getId() == null) dao.inserir(c);
        else dao.atualizar(c);
        return c;
    }

    @Override
    public void deleteById(long id) throws Exception { throw new UnsupportedOperationException("delete not implemented"); }

    @Override
    public Clientepj findById(long id) throws Exception { return dao.buscarPorId(id); }

    @Override
    public Clientepj findByCnpj(String cnpj) throws Exception { return dao.buscarPorCnpj(cnpj); }

    @Override
    public List<Clientepj> findByRazaoSocial(String nome) throws Exception { return dao.buscarPorRazaoSocial(nome); }

    @Override
    public List<Clientepj> findByIdLimit(int inicio, int limite) throws Exception { return dao.listarPorIdLimite(inicio, limite); }
}
