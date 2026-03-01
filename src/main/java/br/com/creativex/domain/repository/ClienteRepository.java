package br.com.creativex.domain.repository;

import br.com.creativex.domain.entity.cliente.Cliente;
import java.util.List;

public interface ClienteRepository {
    Cliente save(Cliente cliente) throws Exception;
    void deleteById(long id) throws Exception;
    Cliente findById(long id) throws Exception;
    Cliente findByCpf(String cpf) throws Exception;
    List<Cliente> findByName(String nome) throws Exception;
    List<Cliente> findByIdLimit(long idInicial, int limite) throws Exception;
}
