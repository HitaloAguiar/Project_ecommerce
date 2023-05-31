package br.unitins.ecommerce.model.produto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Herança
public abstract class Produto extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Integer estoque;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public void minusEstoque(Integer estoque) {

        this.estoque -= estoque;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
