package br.unitins.ecommerce.model.compra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;

import br.unitins.ecommerce.model.DefaultEntity;
import br.unitins.ecommerce.model.endereco.Endereco;
// import br.unitins.ecommerce.model.pagamento.Pagamento;
import br.unitins.ecommerce.model.usuario.Usuario;

@Entity
public class Compra extends DefaultEntity {

    private LocalDate dataCompra;

    private Double totalCompra;

    private Boolean ifConcluida;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    // @OneToOne
    // @JoinColumn(name = "id_pagamento", unique = true, nullable = false)
    // private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany
    @JoinColumn(name = "id_compra", nullable = false)
    private List<ItemCompra> itemCompra;

    public Compra (Usuario usuario) {

        this.ifConcluida = false;
        this.usuario = usuario;
        this.itemCompra = new ArrayList<>();
        this.totalCompra = 0.0;
    }

    public Compra() {
        
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataDaCompra) {
        this.dataCompra = dataDaCompra;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public void plusTotalCompra(Double totalCompra) {

        this.totalCompra += totalCompra;
    }

    public void minusTotalCompra(Double totalCompra) {

        this.totalCompra -= totalCompra;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // public Pagamento getPagamento() {
    //     return pagamento;
    // }

    // public void setPagamento(Pagamento pagamento) {
    //     this.pagamento = pagamento;
    // }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCompra> getItemCompra() {
        return itemCompra;
    }

    public void setItemCompra(ItemCompra itemCompra) {
        this.itemCompra.add(itemCompra);
    }

    public Boolean getIfConcluida() {
        return ifConcluida;
    }

    public void setIfConcluida(Boolean ifConcluida) {
        this.ifConcluida = ifConcluida;
    }
}
