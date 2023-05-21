package br.unitins.ecommerce.dto.telefone;

import java.util.Map;

import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.model.usuario.Usuario;

public record TelefonesResponseDTO(
    Map<String, Object> telefonePrincipal,
    Map<String, Object> telefoneOpcional
) {
    
    public TelefonesResponseDTO (Usuario usuario) {

        this(UsuarioResponseDTO.viewTelefone(usuario.getTelefonePrincipal().getCodigoArea(),
                                            usuario.getTelefonePrincipal().getNumero()),
                usuario.getTelefoneOpcional() != null?
                                            UsuarioResponseDTO.viewTelefone(usuario.getTelefoneOpcional().getCodigoArea(),
                                                                            usuario.getTelefoneOpcional().getNumero())
                                            : null);
    }
}
