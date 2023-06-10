package br.unitins.ecommerce.service.usuario;

import java.util.List;

import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoResponseDTO;
import br.unitins.ecommerce.model.usuario.Usuario;

public interface UsuarioService {
    
    // Metodos basicos

    List<UsuarioResponseDTO> getAllUsuario();

    public List<UsuarioBasicoResponseDTO> getAllUsuarioBasico();
    
    UsuarioResponseDTO getById(Long id);

    UsuarioResponseDTO insert(UsuarioDTO usuarioDto);

    UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    List<UsuarioResponseDTO> getByNome(String nome);

    Usuario getByLoginAndSenha(String login, String senha);

    Usuario getByLogin(String login);
}
