package br.unitins.ecommerce.dto.usuario.perfil.dados;

import br.unitins.ecommerce.model.usuario.Usuario;

public record DadosPessoaisResponseDTO(
    String login,
    String nome,
    String email,
    String cpf,
    String sexo
) {
    
    public DadosPessoaisResponseDTO (Usuario usuario) {

        this(usuario.getLogin(),
            usuario.getPessoaFisica().getNome(),
            usuario.getPessoaFisica().getEmail(),
            usuario.getPessoaFisica().getCpf(),
            usuario.getPessoaFisica().getSexo().getLabel());
    }
}
