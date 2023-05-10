package br.unitins.ecommerce.model.produto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Marca extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

}
