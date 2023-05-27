package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

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
import br.unitins.ecommerce.dto.municipio.MunicipioDTO;
import br.unitins.ecommerce.dto.municipio.MunicipioResponseDTO;
import br.unitins.ecommerce.service.muncipio.MunicipioService;

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {
    
    private static final Logger LOG = Logger.getLogger(MunicipioResource.class);

    @Inject
    MunicipioService municipioService;

    @GET
    public List<MunicipioResponseDTO> getAll() {

        LOG.info("Buscando todos os muncípios.");

        return municipioService.getAll();
    }

    @GET
    @Path("/{id}")
    public MunicipioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return municipioService.getById(id);
    }

    @POST
    public Response insert(MunicipioDTO municipioDto) {

        LOG.info("Inserindo um municipio: " + municipioDto.nome());

        Result result;

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(municipioService.insert(municipioDto))
                    .build();
        } catch (ConstraintViolationException e) {

            LOG.error("Erro ao incluir um municipio.");

            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        } catch (Exception e) {

            LOG.fatal("Erro sem identificação: " + e.getMessage());

            result = new Result(e.getMessage(), false);

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
