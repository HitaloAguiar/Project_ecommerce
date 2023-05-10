package br.unitins.ecommerce.model.pagamento;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class BoletoBancario extends Pagamento {

    @Column(nullable = false)
    private String codigoBarra;

    @Column(nullable = false)
    private Date dataGeracaoBoleto;

    @Column(nullable = false)
    private Date dataVencimento;

    public String getCodigoDeBarra() {
        return codigoBarra;
    }

    public void setCodigoDeBarra(String codigoDeBarra) {
        this.codigoBarra = codigoDeBarra;
    }

    public Date getDataDeGeracaoDoBoleto() {
        return dataGeracaoBoleto;
    }

    public void setDataDeGeracaoDoBoleto(Date dataDeGeracaoDoBoleto) {
        this.dataGeracaoBoleto = dataDeGeracaoDoBoleto;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

}
