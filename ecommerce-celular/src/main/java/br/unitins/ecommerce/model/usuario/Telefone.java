package br.unitins.ecommerce.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Telefone extends DefaultEntity {

    @Column(nullable = false)
    private String codigoArea;

    @Column(nullable = false)
    private String numero;

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
