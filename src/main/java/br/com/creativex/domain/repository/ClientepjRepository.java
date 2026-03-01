package br.com.creativex.domain.repository;

import br.com.creativex.domain.entity.clientepj.Clientepj;

import java.util.List;

public interface ClientepjRepository {
    Clientepj save(Clientepj c) throws Exception;
    void deleteById(long id) throws Exception;
    Clientepj findById(long id) throws Exception;
    Clientepj findByCnpj(String cnpj) throws Exception;
    List<Clientepj> findByRazaoSocial(String nome) throws Exception;
    List<Clientepj> findByIdLimit(int inicio, int limite) throws Exception;
}
