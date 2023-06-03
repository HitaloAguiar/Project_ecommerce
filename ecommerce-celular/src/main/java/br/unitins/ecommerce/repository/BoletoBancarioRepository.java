package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.pagamento.BoletoBancario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BoletoBancarioRepository implements PanacheRepository<BoletoBancario> {
    
}
