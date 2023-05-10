package br.unitins.ecommerce.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.endereco.Estado;
import br.unitins.ecommerce.model.endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {
    
    public List<Municipio> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Municipio WHERE UNACCENT(UPPER(nome)) LIKE UNACCENT(?1)", "%" + nome.toUpperCase() + "%").list();
    }

    public List<Municipio> findByEstado (Estado estado) {

        if (estado == null)
            return null;

        return find("FROM Municipio WHERE estado = ?1", estado).list();
    }
}
