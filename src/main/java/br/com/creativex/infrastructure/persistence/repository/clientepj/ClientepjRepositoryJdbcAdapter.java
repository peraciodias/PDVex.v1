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
    public Clientepj save(Clientepj c) {
        try {
            if (c.getId() == null) dao.inserir(c);
            else dao.atualizar(c);
            return c;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente PJ", e);
        }
    }

    @Override
    public void deleteById(long id) { throw new UnsupportedOperationException("delete not implemented"); }

    @Override
    public Clientepj findById(long id) {
        try {
            return dao.buscarPorId(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente PJ por ID", e);
        }
    }

    @Override
    public Clientepj findByCnpj(String cnpj) {
        try {
            return dao.buscarPorCnpj(cnpj);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente PJ por CNPJ", e);
        }
    }

    @Override
    public List<Clientepj> findByRazaoSocial(String nome) {
        try {
            return dao.buscarPorRazaoSocial(nome);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente PJ por razao social", e);
        }
    }

    @Override
    public List<Clientepj> findByIdLimit(int inicio, int limite) {
        try {
            return dao.listarPorIdLimite(inicio, limite);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes PJ", e);
        }
    }
}
