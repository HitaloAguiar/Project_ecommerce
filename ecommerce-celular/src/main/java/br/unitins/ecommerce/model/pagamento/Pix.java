package br.unitins.ecommerce.model.pagamento;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Pix extends Pagamento {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    private LocalDate dataExpiracaoTokenPix;

    public Pix (Double valor, String nome, String cpf) {

        super(valor);

        this.nome = nome;
        this.cpf = cpf;
        this.dataExpiracaoTokenPix = LocalDate.now().plusDays(1);
    }

    public Pix () {
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataExpiracaoTokenPix() {
        return dataExpiracaoTokenPix;
    }

    public void setDataExpiracaoTokenPix(LocalDate dataExpiracaoTokenPix) {
        this.dataExpiracaoTokenPix = dataExpiracaoTokenPix;
    }
}
