package br.unitins.ecommerce.dto.usuario.usuariobasico;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpgradeUsuarioDTO(

    @NotBlank(message = "O campo CPF não pode estar nulo")
    @Size(max = 11, min = 11, message = "O cpf tem que ter no mínimo 11 dígitos e deve conter apenas os números")
    String cpf,

    @NotNull
    @Min(1)
    Integer sexo,

    @NotNull
    EnderecoDTO endereco,

    @NotNull
    TelefoneDTO telefonePrincipal,
    
    TelefoneDTO telefoneOpcional
) {
    
}
