package br.unitins.ecommerce.dto.municipio;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MunicipioDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,

    @NotNull
    @Min(1)
    Long idestado
) {

}
