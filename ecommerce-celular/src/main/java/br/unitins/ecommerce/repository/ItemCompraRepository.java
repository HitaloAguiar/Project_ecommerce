package br.unitins.ecommerce.repository;

import br.unitins.ecommerce.model.compra.ItemCompra;
import br.unitins.ecommerce.model.produto.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemCompraRepository implements PanacheRepository<ItemCompra> {
    
    public ItemCompra findByProduto (Produto produto) {

        if (produto == null)
            return null;

        return find("FROM ItemCompra WHERE produto = ?1", produto).firstResult();
    }
}
