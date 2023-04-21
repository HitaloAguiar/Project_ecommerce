package br.unitins.ecommerce.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    
    public List<Endereco> findByLogradouro(String logradouro) {

        if (logradouro == null)
            return null;

        return find("FROM Endereco WHERE UPPER(UNACCENT(logradouro)) LIKE UNACCENT(?1)", "%" + logradouro.toUpperCase() + "%").list();
    }

    public List<Endereco> findByBairro(String bairro) {

        if (bairro == null)
            return null;

        return find("FROM Endereco WHERE UPPER(UNACCENT(bairro)) LIKE UNACCENT(?1)", "%" + bairro.toUpperCase() + "%").list();
    }

    public List<Endereco> findByCep(String cep) {

        if (cep == null)
            return null;

        return find("FROM Endereco WHERE UPPER(UNACCENT(cep)) LIKE UNACCENT(?1)", "%" + cep.toUpperCase() + "%").list();
    }

    public List<Endereco> findByMunicipio(Municipio municipio) {

        if (municipio == null)
            return null;

        return find("FROM Endereco WHERE municipio = ?1", municipio).list();
    }
}
