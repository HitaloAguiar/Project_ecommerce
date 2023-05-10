package br.unitins.ecommerce.model.endereco;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Estado extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 2)
    private String sigla;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
