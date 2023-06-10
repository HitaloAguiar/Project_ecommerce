package br.unitins.ecommerce.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import br.unitins.ecommerce.service.usuario.lista_desejo.ListaDesejoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/lista_desejo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListaDesejoResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    ListaDesejoService listaDesejoService;

    @Inject
    JsonWebToken tokenJwt;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);
    
    @GET
    @RolesAllowed({ "Admin", "User" })
    public ListaDesejoResponseDTO getListaDesejo() {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        LOG.infof("Buscando todos os produtos da lista desejo do usuário: ", usuario.getId());
        LOG.debug("ERRO DE DEBUG.");

        return listaDesejoService.getListaDesejo(usuario.getId());
    }

    @PATCH
    @Path("/inserir_produto/{idProduto}")
    @RolesAllowed({ "Admin", "User" })
    public Response insertListaDesejo(@PathParam("idProduto") Long idProduto) {

        Result result = null;

        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            listaDesejoService.insertProdutoIntoListaDesejo(usuario.getId(), idProduto);

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

    @PATCH
    @Path("/remover_produto/{idProduto}")
    @RolesAllowed({ "Admin", "User" })
    public Response deleteProdutoFromListaDesejo(@PathParam("idProduto") Long idProdutoListaDesejo) {
        
        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            listaDesejoService.deleteProdutoFromListaDesejo(usuario.getId(), idProdutoListaDesejo);

            LOG.infof("Produto (%d) removido da lista de desejos do usuário (%d).", idProdutoListaDesejo, usuario.getId());

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
    @RolesAllowed({ "Admin", "User" })
    public Integer countListaDesejo() {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        LOG.infof("Contando todos os produtos do usuário ", usuario.getId());
        LOG.debug("ERRO DE DEBUG.");
        
        return listaDesejoService.countListaDesejo(usuario.getId());
    }
}
