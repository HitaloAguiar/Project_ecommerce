package br.unitins.ecommerce.dto.estado;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record EstadoDTO (
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,

    @NotBlank(message = "Campo sigla não pode estar vazio")
    @Size(max = 2, min = 2, message = "a sigla é composta por extamente duas letras")
    String sigla
) {

}
