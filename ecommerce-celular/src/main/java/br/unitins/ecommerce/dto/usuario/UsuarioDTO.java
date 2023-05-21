package br.unitins.ecommerce.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;

public record UsuarioDTO(

    @NotBlank(message = "O campo login não pode estar nulo")
    String login,

    @NotBlank(message = "O campo senha não pode estar nulo")
    String senha,

    @NotNull
    PessoaFisicaDTO pessoaFisicaDto,

    @NotNull
    EnderecoDTO endereco,

    @NotNull
    TelefoneDTO telefonePrincipal,

    TelefoneDTO telefoneOpcional
) {
    
}
