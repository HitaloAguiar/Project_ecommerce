package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoResponseDTO;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import br.unitins.ecommerce.service.usuario.lista_desejo.ListaDesejoService;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    ListaDesejoService listaDesejoService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed({ "Admin" })
     public List<UsuarioResponseDTO> getAllUsuario() {

        LOG.info("Buscando todos os usuários");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getAllUsuario();
    }

    @GET
    @Path("/usuarios-basicos")
    @RolesAllowed({ "Admin" })
    public List<UsuarioBasicoResponseDTO> getAllUsuarioBasico() {
        
        LOG.info("Buscando todos os usuários basicos");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getAllUsuarioBasico();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public UsuarioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando usuário por nome");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(UsuarioDTO usuarioDto) {
        Result result = null;
        try {
            LOG.infof("Usuário criado com sucesso.");

            return Response
                    .status(Status.CREATED) // 201
                    .entity(usuarioService.insert(usuarioDto))
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

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response update(@PathParam("id") Long id, UsuarioDTO usuarioDto) {
        Result result = null;
        try {
            usuarioService.update(id, usuarioDto);
            LOG.infof("Usuário (%d) atualizado com sucesso.", id);

            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();

        } catch (ConstraintViolationException e) {
            LOG.errorf("Erro ao atualizar um usuário. ", id, e);
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

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {
        try {
            usuarioService.delete(id);
            LOG.infof("Produto excluído com sucesso.", id);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar usuário: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public Long count() {
        LOG.infof("Contando os usuários");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    @RolesAllowed({ "Admin" })
    public List<UsuarioResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {
        LOG.info("Buscando usuário por nome");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getByNome(nome);
    }

}
