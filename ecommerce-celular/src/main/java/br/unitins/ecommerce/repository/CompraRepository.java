package br.unitins.ecommerce.repository;

import java.util.List;

import br.unitins.ecommerce.model.compra.Compra;
import br.unitins.ecommerce.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {
    
    public List<Compra> findByUsuarioWhereIsFinished (Usuario usuario) {

        if (usuario == null)
            return null;

        return find("FROM Compra WHERE usuario = ?1 AND ifConcluida = true", usuario).list();
    }

    public Compra findByUsuarioWhereIsNotFinished (Usuario usuario) {

        if (usuario == null)
            return null;

        return find("FROM Compra WHERE usuario = ?1 AND ifConcluida = false", usuario).firstResult();
    }
}
