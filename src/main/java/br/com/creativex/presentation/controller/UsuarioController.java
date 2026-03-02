package br.com.creativex.presentation.controller;

import br.com.creativex.infrastructure.persistence.repository.usuario.UsuarioDAO;
import br.com.creativex.model.usuario.Usuario;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario autenticar(String login, String senha) {
        return usuarioDAO.autenticar(login, senha);
    }

    public boolean salvar(Usuario usuario) {
        return usuarioDAO.salvar(usuario);
    }
}
