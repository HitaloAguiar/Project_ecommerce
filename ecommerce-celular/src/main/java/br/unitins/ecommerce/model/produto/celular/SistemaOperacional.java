package br.unitins.ecommerce.model.produto.celular;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SistemaOperacional {
    
    ANDROID(1, "Android"),
    IOS(2, "iOS");

    private int id;
    private String label;

    SistemaOperacional (int id, String label) {

        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static SistemaOperacional valueOf(Integer id) throws IllegalArgumentException {

        if (id == null)
            return null;

        for (SistemaOperacional sistemaOperacional : SistemaOperacional.values()) {
            
            if (sistemaOperacional.getId() == id)
                return sistemaOperacional;
        }

        throw new IllegalArgumentException("Número fora das opções");
    }
}
