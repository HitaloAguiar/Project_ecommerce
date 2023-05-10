package br.unitins.ecommerce.model.produto.celular;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import br.unitins.ecommerce.model.produto.Produto;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Celular extends Produto {

    @Column(nullable = false)
    private Float versaoSistemaOperacional;

    @Column(nullable = false)
    private SistemaOperacional sistemaOperacional;
    
    @Column(nullable = false)
    private Cor cor;

    public Float getVersaoSistemaOperacional() {
        return versaoSistemaOperacional;
    }
    public void setVersaoSistemaOperacional(Float versaoDoSistemaOperacional) {
        this.versaoSistemaOperacional = versaoDoSistemaOperacional;
    }
    public SistemaOperacional getSistemaOperacional() {
        return sistemaOperacional;
    }
    public void setSistemaOperacional(SistemaOperacional sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }
    public Cor getCor() {
        return cor;
    }
    public void setCor(Cor cor) {
        this.cor = cor;
    }
}
