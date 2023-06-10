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
import jakarta.ws.rs.PATCH;
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
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoResponseDTO;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

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

    @GET
    @Path("/lista_desejo/{idUsuario}")
    @RolesAllowed({ "Admin", "User" })
    public ListaDesejoResponseDTO getListaDesejo(@PathParam("idUsuario") Long idUsuario) {
        LOG.infof("Buscando todos os produtos da lista desejo do usuário: ", idUsuario);
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getListaDesejo(idUsuario);
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

    @PATCH
    @Path("/lista_desejo")
    @RolesAllowed({ "Admin", "User" })
    public Response insertListaDesejo(ListaDesejoDTO listaDto) {

        Result result = null;
        try {
            usuarioService.insertProdutoIntoListaDesejo(listaDto);
            LOG.infof("Produto inserido na lista Desejo.");
            return Response
                    .status(Status.CREATED) // 201
                    .build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um produto na lista de desejos.");
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

    @PATCH
    @Path("/lista_desejo/{idUsuario}/{idProduto}")
    @RolesAllowed({ "Admin", "User" })
    public Response deleteProdutoFromListaDesejo(@PathParam("idUsuario") Long idUsuario,
            @PathParam("idProduto") Long idProdutoListaDesejo) {
        try {
            usuarioService.deleteProdutoFromListaDesejo(idUsuario, idProdutoListaDesejo);
            LOG.infof("Produto (%d) removido da lista de desejos do usuário (%d).", idProdutoListaDesejo, idUsuario);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();

        } catch (Exception e) {
            LOG.error("Erro ao remover produto da lista de desejos.", e);

            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .build();
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
    @Path("/lista_desejo/count/{id}")
    @RolesAllowed({ "Admin", "User" })
    public Integer countListaDesejo(@PathParam("id") Long id) {
        LOG.infof("Contando todos os produtos do usuário ", id);
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.countListaDesejo(id);
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
