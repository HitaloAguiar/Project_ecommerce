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
import br.unitins.ecommerce.dto.municipio.MunicipioDTO;
import br.unitins.ecommerce.dto.municipio.MunicipioResponseDTO;
import br.unitins.ecommerce.service.muncipio.MunicipioService;

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {
    
    @Inject
    MunicipioService municipioService;

    @GET
    public List<MunicipioResponseDTO> getAll() {

        return municipioService.getAll();
    }

    @GET
    @Path("/{id}")
    public MunicipioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return municipioService.getById(id);
    }

    @POST
    public Response insert(MunicipioDTO municipioDto) {

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(municipioService.insert(municipioDto))
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
    public Response update(@PathParam("id") Long id, MunicipioDTO municipioDto) {

        try {

            municipioService.update(id, municipioDto);

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

        municipioService.delete(id);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/count")
    public Long count() {

        return municipioService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    public List<MunicipioResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {

        return municipioService.getByNome(nome);
    }

    @GET
    @Path("/searchByNomeEstado/{nomeEstado}")
    public List<MunicipioResponseDTO> getByNomeEstado(@PathParam("nomeEstado") String nomeEstado) throws NullPointerException {

        return municipioService.getByNomeEstado(nomeEstado);
    }

    @GET
    @Path("/searchBySiglaEstado/{siglaEstado}")
    public List<MunicipioResponseDTO> getBySiglaEstado(@PathParam("siglaEstado") String siglaEstado) throws NullPointerException {

        return municipioService.getBySiglaEstado(siglaEstado);
    }
}
