package br.unitins.ecommerce.model.endereco;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Municipio extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private Estado estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
