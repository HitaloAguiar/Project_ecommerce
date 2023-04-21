package br.unitins.ecommerce.dto.municipio;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record MunicipioDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,

    @NotNull
    @Min(1)
    Long idestado
) {

}
