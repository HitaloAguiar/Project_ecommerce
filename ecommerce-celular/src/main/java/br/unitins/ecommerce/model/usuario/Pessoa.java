package br.unitins.ecommerce.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends DefaultEntity {
    
    @Column(nullable = false)
    private String nome;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
