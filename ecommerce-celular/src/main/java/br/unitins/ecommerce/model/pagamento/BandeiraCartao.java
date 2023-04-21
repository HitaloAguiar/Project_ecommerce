package br.unitins.ecommerce.model.pagamento;

public enum BandeiraCartao {

    VISA(1, "Visa"),
    AMERICAN_EXPRESS(2, "American Express"),
    HIPERCARD(3, "Hipercard"),
    DINERS(4, "Diners"),
    MASTERCARD(5, "Mastercard"),
    ELO(6, "Elo");

    private int id;
    private String label;

    BandeiraCartao(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static BandeiraCartao valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (BandeiraCartao bandeiraCartao : BandeiraCartao.values()) {
            if (id.equals(bandeiraCartao.getId())) {
                return bandeiraCartao;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }

}
