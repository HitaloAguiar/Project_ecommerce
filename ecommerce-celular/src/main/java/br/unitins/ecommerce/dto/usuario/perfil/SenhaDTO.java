package br.unitins.ecommerce.dto.usuario.perfil;

import jakarta.validation.constraints.NotBlank;

public record SenhaDTO(
    @NotBlank(message = "O campo \"senha antiga\" não pode ser nulo")
    String senhaAntiga,

    @NotBlank(message = "O campo \"senha nova\" não pode ser nulo")
    String senhaNova
) {
    
}
