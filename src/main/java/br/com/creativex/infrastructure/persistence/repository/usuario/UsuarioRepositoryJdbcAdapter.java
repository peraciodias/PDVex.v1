package br.com.creativex.infrastructure.persistence.repository.usuario;

import br.com.creativex.domain.entity.usuario.Usuario;
import br.com.creativex.domain.repository.UsuarioRepository;

public class UsuarioRepositoryJdbcAdapter implements UsuarioRepository {

    private final UsuarioDAO usuarioDAO;

    public UsuarioRepositoryJdbcAdapter(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public Usuario autenticar(String login, String senha) {
        br.com.creativex.model.usuario.Usuario model = usuarioDAO.autenticar(login, senha);
        return model == null ? null : toDomain(model);
    }

    @Override
    public boolean salvar(Usuario usuario) {
        return usuarioDAO.salvar(toModel(usuario));
    }

    private Usuario toDomain(br.com.creativex.model.usuario.Usuario model) {
        Usuario domain = new Usuario();
        domain.setId(model.getId());
        domain.setNome(model.getNome());
        domain.setLogin(model.getLogin());
        domain.setSenha(model.getSenha());
        domain.setPerfil(model.getPerfil());
        domain.setAtivo(model.isAtivo());
        return domain;
    }

    private br.com.creativex.model.usuario.Usuario toModel(Usuario domain) {
        br.com.creativex.model.usuario.Usuario model = new br.com.creativex.model.usuario.Usuario();
        model.setId(domain.getId());
        model.setNome(domain.getNome());
        model.setLogin(domain.getLogin());
        model.setSenha(domain.getSenha());
        model.setPerfil(domain.getPerfil());
        model.setAtivo(domain.isAtivo());
        return model;
    }
}
