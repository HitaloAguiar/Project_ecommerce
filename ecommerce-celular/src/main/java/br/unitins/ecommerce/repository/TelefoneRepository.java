package br.unitins.ecommerce.repository;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.usuario.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {
    
    public List<Telefone> findByListId (List<Long> ids) {

        if (ids == null)
            return null;

        List<Telefone> telefones = new ArrayList<>();

        for (Long id : ids) {
            
            telefones.add(findById(id));
        }

        return telefones;
    }    
}
