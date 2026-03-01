package br.com.creativex.application.cliente;

import br.com.creativex.domain.entity.cliente.Cliente;
import br.com.creativex.domain.repository.ClienteRepository;

import java.util.List;

public class ClienteUseCases {

    private final ClienteRepository repo;

    public ClienteUseCases(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente save(Cliente cliente) throws Exception {
        return repo.save(cliente);
    }

    public Cliente findById(long id) throws Exception {
        return repo.findById(id);
    }

    public Cliente findByCpf(String cpf) throws Exception {
        return repo.findByCpf(cpf);
    }

    public List<Cliente> findByName(String nome) throws Exception {
        return repo.findByName(nome);
    }

    public List<Cliente> listByIdLimit(long idInicial, int limite) throws Exception {
        return repo.findByIdLimit(idInicial, limite);
    }
}
