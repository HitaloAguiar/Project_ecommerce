package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.pagamento.Pix;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixRepository implements PanacheRepository<Pix> {
    
}
