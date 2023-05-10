package br.unitins.ecommerce.resource;

import java.util.List;

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

    @GET
    public List<AvaliacaoResponseDTO> getAll() {

        return avaliacaoService.getAll();
    }

    @GET
    @Path("/{id}")
    public AvaliacaoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return avaliacaoService.getById(id);
    }

    @POST
    public Response insert(AvaliacaoDTO avaliacaoDto) {

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(avaliacaoService.insert(avaliacaoDto))
                    .build();
        } catch (ConstraintViolationException e) {

            Result result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AvaliacaoDTO avaliacaoDto) {

        try {

            avaliacaoService.update(id, avaliacaoDto);

            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {

            Result result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {

        avaliacaoService.delete(id);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/count")
    public Long count() {

        return avaliacaoService.count();
    }

    @GET
    @Path("/searchByYear/{year}")
    public List<AvaliacaoResponseDTO> getByYear(@PathParam("year") Integer year) throws NullPointerException {

        return avaliacaoService.getByYear(year);
    }
}
