package br.com.creativex.domain.repository;

import br.com.creativex.domain.entity.fornecedor.Fornecedor;

import java.util.List;

public interface FornecedorRepository {

    Fornecedor save(Fornecedor fornecedor) throws Exception;

    void deleteById(long id) throws Exception;

    Fornecedor findById(long id) throws Exception;

    Fornecedor findByCnpj(String cnpj) throws Exception;

    List<Fornecedor> findByRazaoSocial(String razaoSocial) throws Exception;

    List<Fornecedor> findByIdLimit(long idInicial, int limite) throws Exception;
}
