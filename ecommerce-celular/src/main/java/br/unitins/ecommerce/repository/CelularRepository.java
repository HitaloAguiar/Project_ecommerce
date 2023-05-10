package br.unitins.ecommerce.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.ecommerce.model.produto.Marca;
import br.unitins.ecommerce.model.produto.celular.Celular;
import br.unitins.ecommerce.model.produto.celular.Cor;
import br.unitins.ecommerce.model.produto.celular.SistemaOperacional;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CelularRepository implements PanacheRepository<Celular> {
    
    public List<Celular> findByNome (String nome) {

        if (nome == null)
            return null;

        return find("FROM Celular WHERE UNACCENT(UPPER(nome)) LIKE UNACCENT(?1)", "%" + nome.toUpperCase() + "%").list();
    }

    public List<Celular> findBySistemaOperacional (SistemaOperacional sistemaOperacional) {

        if (sistemaOperacional == null)
            return null;

        return find("FROM Celular WHERE sistemaOperacional = ?1", sistemaOperacional).list();
    }

    public List<Celular> findByCor (Cor cor) {

        if (cor == null)
            return null;

        return find("FROM Celular WHERE cor = ?1", cor).list();
    }

    public List<Celular> findByMarca (Marca marca) {

        if (marca == null)
            return null;

        return find("FROM Celular WHERE marca = ?1", marca).list();
    }

    public List<Celular> filterByPrecoMaximo (Double preco) {

        if (preco == null)
            return null;

        return find("FROM Celular WHERE preco <= ?1", preco).list();
    }

    public List<Celular> filterByPrecoMinimo (Double preco) {

        if (preco == null)
            return null;

        return find("FROM Celular WHERE preco >= ?1", preco).list();
    }

    public List<Celular> filterByEntrePreco (Double precoMin, Double precoMax) {

        if (precoMin == null || precoMax == null)
            return null;

        return find("FROM Celular WHERE preco >= ?1 AND preco <= ?2", precoMin, precoMax).list();
    }
}
