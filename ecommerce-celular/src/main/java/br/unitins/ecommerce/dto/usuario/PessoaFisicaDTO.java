package br.unitins.ecommerce.dto.usuario;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PessoaFisicaDTO(
    @NotBlank(message = "O campo nome não pode estar nulo")
    String nome,

    @NotBlank(message = "O campo CPF não pode estar nulo")
    @Size(max = 11, min = 11, message = "O cpf tem que ter no mínimo 11 dígitos e deve conter apenas os números")
    String cpf,

    @NotBlank(message = "O campo email não pode estar nulo")
    String email,

    @NotNull
    @Min(1)
    Integer sexo
) {
    
}
