package br.unitins.ecommerce.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.service.estado.EstadoService;

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {
    
    @Inject
    EstadoService estadoService;

    @GET
    public List<EstadoResponseDTO> getAll() {

        return estadoService.getAll();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return estadoService.getById(id);
    }

    @POST
    public Response insert(EstadoDTO estadoDto) {

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(estadoService.insert(estadoDto))
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
    public Response update(@PathParam("id") Long id, EstadoDTO estadoDto) {

        try {

            estadoService.update(id, estadoDto);

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
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {

        estadoService.delete(id);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/count")
    public Long count() {

        return estadoService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    public List<EstadoResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {

        return estadoService.getByNome(nome);
    }

    @GET
    @Path("/searchBySigla/{sigla}")
    public List<EstadoResponseDTO> getBySigla(@PathParam("sigla") String sigla) throws NullPointerException {

        return estadoService.getBySigla(sigla);
    }
}
