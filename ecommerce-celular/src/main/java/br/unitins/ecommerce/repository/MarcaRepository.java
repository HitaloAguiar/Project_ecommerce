package br.unitins.ecommerce.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.produto.Marca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {
    
    public List<Marca> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Marca WHERE UNACCENT(UPPER(nome)) LIKE UNACCENT(?1)", "%" + nome.toUpperCase() + "%").list();
    }
}
