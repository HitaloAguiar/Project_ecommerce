package br.unitins.ecommerce.model.compra;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import br.unitins.ecommerce.model.DefaultEntity;
import br.unitins.ecommerce.model.produto.Produto;

@Entity
public class ItemCompra extends DefaultEntity {

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double precoUnitario;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    public ItemCompra (Produto produto, Integer quantidade) {

        this.produto = produto;
        this.precoUnitario = produto.getPreco();
        this.quantidade = quantidade;
    }

    public ItemCompra() {
        
    }

    public boolean contains(Produto produto) {

        if (this.produto.getId() == produto.getId())
            return true;
        
        else
            return false;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void updateQuantidade(Integer quantidade) {

        this.quantidade += quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double preco) {
        this.precoUnitario = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
