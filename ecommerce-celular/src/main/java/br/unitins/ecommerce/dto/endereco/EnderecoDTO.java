package br.unitins.ecommerce.dto.endereco;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EnderecoDTO(
    @NotBlank(message = "O campo logradouro não pode estar vazio")
    String logradouro,

    @NotBlank(message = "O campo bairro não pode estar vazio")
    String bairro,

    @NotBlank(message = "O campo número não pode estar vazio")
    String numero,

    String complemento,

    @NotBlank(message = "O campo cep não pode estar vazio")
    String cep,

    @NotNull
    @Min(1)
    Long idMunicipio
) {

}
