package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.pagamento.CartaoCredito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartaoCreditoRepository implements PanacheRepository<CartaoCredito> {
    
}
