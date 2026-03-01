package br.com.creativex.presentation.controller;

import br.com.creativex.application.cliente.ClienteUseCases;
import br.com.creativex.domain.entity.cliente.Cliente;
import br.com.creativex.domain.repository.ClienteRepository;

import java.util.List;

public class ClienteController {

    private final ClienteUseCases useCases;

    public ClienteController(ClienteRepository repository) {
        this.useCases = new ClienteUseCases(repository);
    }

    public Cliente save(Cliente c) throws Exception { return useCases.save(c); }
    public Cliente findById(long id) throws Exception { return useCases.findById(id); }
    public Cliente findByCpf(String cpf) throws Exception { return useCases.findByCpf(cpf); }
    public List<Cliente> findByName(String nome) throws Exception { return useCases.findByName(nome); }
    public List<Cliente> listByIdLimit(long idInicial, int limite) throws Exception { return useCases.listByIdLimit(idInicial, limite); }
}
