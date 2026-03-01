package br.com.creativex.infrastructure.persistence.repository.fornecedor;

import br.com.creativex.domain.entity.fornecedor.Fornecedor;
import br.com.creativex.domain.repository.FornecedorRepository;

import java.util.List;

public class FornecedorRepositoryJdbcAdapter implements FornecedorRepository {

    private final FornecedorDAO dao;

    public FornecedorRepositoryJdbcAdapter(FornecedorDAO dao) {
        this.dao = dao;
    }

    @Override
    public Fornecedor save(Fornecedor fornecedor) throws Exception {
        if (fornecedor.getId() == null) {
            dao.inserir(fornecedor);
        } else {
            dao.atualizar(fornecedor);
        }
        return fornecedor;
    }

    @Override
    public void deleteById(long id) throws Exception {
        throw new UnsupportedOperationException("delete not implemented");
    }

    @Override
    public Fornecedor findById(long id) throws Exception {
        return dao.buscarPorId(id);
    }

    @Override
    public Fornecedor findByCnpj(String cnpj) throws Exception {
        return dao.buscarPorCnpj(cnpj);
    }

    @Override
    public List<Fornecedor> findByRazaoSocial(String razaoSocial) throws Exception {
        return dao.buscarPorRazaoSocial(razaoSocial);
    }

    @Override
    public List<Fornecedor> findByIdLimit(long idInicial, int limite) throws Exception {
        return dao.listarPorIdLimite(idInicial, limite);
    }
}
