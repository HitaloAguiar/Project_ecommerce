package br.unitins.ecommerce.dto.avaliacao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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
