package br.unitins.ecommerce.dto.celular;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.ecommerce.model.produto.celular.Celular;
import br.unitins.ecommerce.model.produto.celular.Cor;
import br.unitins.ecommerce.model.produto.celular.SistemaOperacional;

public record CelularResponseDTO(
    Long id,
    String nome,
    String descricao,
    Double preco,
    String estoque,
    String nomeMarca,
    Float versãoSistemaOperacional,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    SistemaOperacional sistemaOperacional,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Cor cor
) {
    
    public CelularResponseDTO (Celular celular) {

        this(celular.getId(),
            celular.getNome(),
            celular.getDescricao(),
            celular.getPreco(),
            celular.getEstoque() > 0? "Disponível" : "Estoque esgotado",
            celular.getMarca().getNome(),
            celular.getVersaoSistemaOperacional(),
            celular.getSistemaOperacional(),
            celular.getCor());
    }
}
