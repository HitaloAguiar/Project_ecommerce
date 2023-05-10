package br.unitins.ecommerce.model.compra;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import br.unitins.ecommerce.model.DefaultEntity;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.pagamento.Pagamento;
import br.unitins.ecommerce.model.usuario.Usuario;

@Entity
public class Compra extends DefaultEntity {

    @Column(nullable = false)
    private Date dataCompra;

    @Column(nullable = false)
    private Double totalCompra;

    @ManyToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "id_pagamento", unique = true, nullable = false)
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany
    @JoinColumn(name = "id_compra", nullable = false)
    private List<ItemCompra> itemCompra;

    public Date getDataDaCompra() {
        return dataCompra;
    }

    public void setDataDaCompra(Date dataDaCompra) {
        this.dataCompra = dataDaCompra;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCompra> getItemCompra() {
        return itemCompra;
    }

    public void setItemCompra(List<ItemCompra> itemCompra) {
        this.itemCompra = itemCompra;
    }

}
