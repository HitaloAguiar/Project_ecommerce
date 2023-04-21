package br.unitins.ecommerce.dto.usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;

public record UsuarioDTO(

    @NotBlank(message = "O campo nome não pode estar nulo")
    String nome,

    @NotBlank(message = "O campo email não pode estar nulo")
    String email,

    @NotBlank(message = "O campo senha não pode estar nulo")
    String senha,

    @NotBlank(message = "O campo CPF não pode estar nulo")
    @Size(max = 11, min = 11, message = "O cpf tem que ter no mínimo 11 dígitos e deve conter apenas os números")
    String cpf,

    @NotNull
    EnderecoDTO endereco,

    @NotNull
    TelefoneDTO telefonePrincipal,

    TelefoneDTO telefoneOpcional
) {
    
}
