package br.unitins.ecommerce.service.usuario.usuariobasico;

import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UpgradeUsuarioDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoResponseDTO;

public interface UsuarioBasicoService {
    
    UsuarioBasicoResponseDTO insert(UsuarioBasicoDTO usuarioBasicoDto);

    UsuarioResponseDTO upgrade(Long id, UpgradeUsuarioDTO usuarioDto);
}
