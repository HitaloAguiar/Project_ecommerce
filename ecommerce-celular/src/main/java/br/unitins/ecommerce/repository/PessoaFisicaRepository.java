package br.unitins.ecommerce.repository;

import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.ecommerce.model.usuario.pessoafisica.PessoaFisica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PessoaFisicaRepository implements PanacheRepository<PessoaFisica> {
    
}
