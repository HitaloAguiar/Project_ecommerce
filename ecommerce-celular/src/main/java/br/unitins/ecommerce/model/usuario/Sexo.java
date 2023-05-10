package br.unitins.ecommerce.model.usuario;

public enum Sexo {
    
    MASCULINO(1, "Masculino"),
    FEMININO(2, "Feminino");

    private int id;
    private String label;

    Sexo (int id, String label) {

        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Sexo valueOf (Integer id) throws IllegalArgumentException {

        if (id == null)
            return null;

        for (Sexo sexo : Sexo.values()) {
            
            if (id == sexo.id)
                return sexo;
        }

        throw new IllegalArgumentException("Número fora das opções disponíveis");
    }
}
