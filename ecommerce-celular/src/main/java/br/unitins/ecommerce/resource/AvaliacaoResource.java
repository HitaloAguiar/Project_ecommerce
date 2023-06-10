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
import br.unitins.ecommerce.dto.avaliacao.AvaliacaoDTO;
import br.unitins.ecommerce.dto.avaliacao.AvaliacaoResponseDTO;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;

@Path("/avaliacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {

    @Inject
    AvaliacaoService avaliacaoService;

    private static final Logger LOG = Logger.getLogger(AvaliacaoResource.class);

    @GET
    @RolesAllowed({ "User", "User_Basic" })
    public List<AvaliacaoResponseDTO> getAll() {
        LOG.info("Buscando todas as avaliações");
        LOG.debug("ERRO DE DEBUG.");
        return avaliacaoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "User", "Admin" })
    public AvaliacaoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.infof("Buscando avaliações por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");
        return avaliacaoService.getById(id);
    }

    @POST
    @RolesAllowed({ "User"})
    public Response insert(AvaliacaoDTO avaliacaoDto) {
        Result result = null;
        try {
            AvaliacaoResponseDTO avaliacao = avaliacaoService.insert(avaliacaoDto);
            LOG.infof("Produto inserido na lista Desejo.");

            return Response.status(Status.CREATED).entity(avaliacao).build();

        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao inserir uma avaliação. ", e);

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
    @RolesAllowed({ "User" })
    public Response update(@PathParam("id") Long id, AvaliacaoDTO avaliacaoDto) {
        Result result = null;
        try {

            avaliacaoService.update(id, avaliacaoDto);

            LOG.infof("Avaliação  atualizada com sucesso.", id);

            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {
            LOG.errorf("Erro ao atualizar a avaliação. ", id, e);

            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());
        }catch (Exception e){
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
    @RolesAllowed({ "User" })
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {

        try {
            avaliacaoService.delete(id);
            LOG.infof("avaliação excluída com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar avaliação: parâmetros inválidos.", e);
            throw e;
        } 
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public Long count() {
        LOG.info("Contando todas as avaliações.");
        LOG.debug("ERRO DE DEBUG.");
        return avaliacaoService.count();
    }

    @GET
    @Path("/searchByYear/{year}")
    @RolesAllowed({ "User", "Admin" })
    public List<AvaliacaoResponseDTO> getByYear(@PathParam("year") Integer year) throws NullPointerException {
        LOG.infof("Buscando avaliação por ano. ", year);
        LOG.debug("ERRO DE DEBUG.");
        return avaliacaoService.getByYear(year);
    }
}
