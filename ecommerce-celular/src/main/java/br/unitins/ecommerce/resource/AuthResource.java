package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.usuario.perfil.AuthUsuarioDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.hash.HashService;
import br.unitins.ecommerce.service.token.TokenJwtService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;

    @POST
    public Response login(AuthUsuarioDTO authDTO) {
        
        String hash = hashService.getHashSenha(authDTO.senha());

        Usuario usuario = usuarioService.getByLoginAndSenha(authDTO.login(), hash);

        if (usuario == null) {
            return Response.status(Status.NO_CONTENT)
                            .entity("Usuario não encontrado").build();
        }

        return Response.ok()
                    .header("Authorization", tokenService.generateJwt(usuario))
                    .build();
    }

    
}
