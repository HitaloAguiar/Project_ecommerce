package br.unitins.ecommerce.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    
    public List<Usuario> findByNome (String nomePessoaFisica) {

        if (nomePessoaFisica == null)
            return null;

        return find("FROM Usuario WHERE UNACCENT(UPPER(pessoaFisica.nome)) LIKE UNACCENT(?1)", "%" + nomePessoaFisica.toUpperCase() + "%").list();
    }

    public Usuario findByLoginAndSenha(String login, String senha) {

        if (login == null || senha == null)
            return null;

        return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
    }

    public Usuario findByLogin(String login) throws NullPointerException {

        if (login == null)
            return null;

        return find("login = ?1 ", login).firstResult();
    }
}
