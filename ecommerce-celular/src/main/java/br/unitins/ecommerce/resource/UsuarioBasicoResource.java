package br.unitins.ecommerce.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UpgradeUsuarioDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoResponseDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import br.unitins.ecommerce.service.usuario.usuariobasico.UsuarioBasicoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuario-basico")
public class UsuarioBasicoResource {

    @Inject
    UsuarioBasicoService usuarioBasicoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken tokenJwt;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);
    
    @POST
    public Response insertBasico(UsuarioBasicoDTO usuarioBasicoDto) {

        Result result;

        try {
            LOG.infof("Usuário criado com sucesso.");

            return Response
                    .status(Status.CREATED) // 201
                    .entity(usuarioBasicoService.insert(usuarioBasicoDto))
                    .build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir Usuário.");
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();

    }

    @GET
    @RolesAllowed({ "User_Basic" })
    public Response getDadosPessoais() {

        String login = tokenJwt.getSubject();

        UsuarioBasicoResponseDTO usuarioBasico = new UsuarioBasicoResponseDTO(usuarioService.getByLogin(login));
        LOG.infof("Buscando o dados pessoais do usuário: ", login);
        LOG.debug("ERRO DE DEBUG.");

        return Response.ok(usuarioBasico).build();
    }

    @PATCH
    @RolesAllowed({ "User_Basic" })
    public Response upgradeUsuario(UpgradeUsuarioDTO usuarioDTO) {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        return Response.status(Status.CREATED).entity(usuarioBasicoService.upgrade(usuario.getId(), usuarioDTO)).build();
    }
}
