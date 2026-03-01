package br.com.creativex.application.clientepj;

import br.com.creativex.domain.entity.clientepj.Clientepj;
import br.com.creativex.domain.repository.ClientepjRepository;

import java.util.List;

public class ClientepjUseCases {

    private final ClientepjRepository repo;

    public ClientepjUseCases(ClientepjRepository repo) { this.repo = repo; }

    public Clientepj save(Clientepj c) throws Exception { return repo.save(c); }
    public Clientepj findById(long id) throws Exception { return repo.findById(id); }
    public Clientepj findByCnpj(String cnpj) throws Exception { return repo.findByCnpj(cnpj); }
    public List<Clientepj> findByRazaoSocial(String nome) throws Exception { return repo.findByRazaoSocial(nome); }
    public List<Clientepj> listByIdLimit(int inicio, int limite) throws Exception { return repo.findByIdLimit(inicio, limite); }
}
