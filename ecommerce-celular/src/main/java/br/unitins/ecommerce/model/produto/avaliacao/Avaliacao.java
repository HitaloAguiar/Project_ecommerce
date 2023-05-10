package br.unitins.ecommerce.model.produto.avaliacao;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import br.unitins.ecommerce.model.DefaultEntity;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.Usuario;

@Entity
public class Avaliacao extends DefaultEntity {

    private String comentario;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Estrela estrela;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Estrela getEstrela() {
        return estrela;
    }

    public void setEstrela(Estrela estrela) {
        this.estrela = estrela;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
