package br.com.creativex.infrastructure.persistence.repository.cliente;

import br.com.creativex.domain.entity.cliente.Cliente;
import br.com.creativex.domain.repository.ClienteRepository;

import java.util.List;

public class ClienteRepositoryJdbcAdapter implements ClienteRepository {

    private final ClienteDAO clienteDAO;

    public ClienteRepositoryJdbcAdapter(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public Cliente save(Cliente cliente) throws Exception {
        if (cliente.getId() == null) {
            clienteDAO.inserir(cliente);
        } else {
            clienteDAO.atualizar(cliente);
        }
        return cliente;
    }

    @Override
    public void deleteById(long id) throws Exception {
        // implement if needed
        throw new UnsupportedOperationException("delete not implemented");
    }

    @Override
    public Cliente findById(long id) throws Exception {
        return clienteDAO.buscarPorId(id);
    }

    @Override
    public Cliente findByCpf(String cpf) throws Exception {
        return clienteDAO.buscarPorCpf(cpf);
    }

    @Override
    public List<Cliente> findByName(String nome) throws Exception {
        return clienteDAO.buscarPorNome(nome);
    }

    @Override
    public List<Cliente> findByIdLimit(long idInicial, int limite) throws Exception {
        return clienteDAO.listarPorIdLimite(idInicial, limite);
    }
}
