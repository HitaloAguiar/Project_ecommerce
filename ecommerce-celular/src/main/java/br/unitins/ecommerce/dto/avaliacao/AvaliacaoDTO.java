package br.unitins.ecommerce.dto.avaliacao;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record AvaliacaoDTO(
    String comentario,

    @NotNull
    @Min(1)
    Integer estrela,

    @NotNull
    @Min(1)
    Long idProduto,

    @NotNull
    @Min(1)
    Long idUsuario
) {
    
}
