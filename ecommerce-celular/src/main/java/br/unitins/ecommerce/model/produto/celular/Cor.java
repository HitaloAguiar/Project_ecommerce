package br.unitins.ecommerce.model.produto.celular;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Cor {
    
    AMARELO(1, "Amarelo"),
    AZUL(2, "Azul"),
    BRANCO(3, "Branco"),
    DOURADO(4, "Dourado"),
    PRATEADO(5, "Prateado"),
    PRETO(6, "Preto"),
    ROSA(7, "Rosa"),
    VERDE(8, "Verde"),
    VERMELHO(9, "Vermelho"),
    CINZA(10, "Cinza");

    private int id;
    private String label;

    Cor (int id, String label) {

        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Cor valueOf (Integer id) throws IllegalArgumentException {

        if (id == null)
            return null;

        for (Cor cor : Cor.values()) {
            
            if (id == cor.id)
                return cor;
        }

        throw new IllegalArgumentException("Número fora das opções disponíveis");
    }
}
