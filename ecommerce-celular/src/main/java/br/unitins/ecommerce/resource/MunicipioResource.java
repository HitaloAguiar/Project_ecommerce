package br.unitins.ecommerce.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.PermitAll;
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
import br.unitins.ecommerce.dto.municipio.MunicipioDTO;
import br.unitins.ecommerce.dto.municipio.MunicipioResponseDTO;
import br.unitins.ecommerce.service.municipio.MunicipioService;

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    MunicipioService municipioService;

    private static final Logger LOG = Logger.getLogger(MunicipioResource.class);

    @GET
    @PermitAll
    public List<MunicipioResponseDTO> getAll() {
        LOG.info("Buscando todos os municipios.");
        LOG.debug("ERRO DE DEBUG.");
        return municipioService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public MunicipioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {
        LOG.info("Buscando município por ID: " + id);
        LOG.debug("ERRO DE DEBUG.");
        return municipioService.getById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(MunicipioDTO municipioDto) {
        LOG.infof("Inserindo um municipio: %s", municipioDto.nome());

        Result result = null;

        try {
            MunicipioResponseDTO municipio = municipioService.insert(municipioDto);

            LOG.infof("Municipio (%d) criado com sucesso.", municipio.id());

            return Response.status(Status.CREATED).entity(municipio).build();

        } catch (ConstraintViolationException e) {

            LOG.error("Erro ao incluir um municipio.");

            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());

            result = new Result(e.getMessage(), false);
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, MunicipioDTO municipioDto) {
        Result result = null;
        
        try {
            municipioService.update(id, municipioDto);
            LOG.infof("Município (%d) atualizado com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro de validação ao atualizar o município.", e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

        } catch (Exception e) {
            LOG.fatal("Erro ao atualizar o município " + id + ".", e);
            result = new Result(e.getMessage(), false);
    
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {

        try {
            municipioService.delete(id);
            LOG.infof("Município (%d) excluído com sucesso.", id);
            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar município: parâmetros inválidos.", e);
            throw e;
        } catch (NotFoundException e) {
            LOG.errorf("Município (%d) não encontrado.", id);
            throw e;
        }
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin"})
    public Long count() {
        LOG.info("Contando todos os municipios.");
        LOG.debug("ERRO DE DEBUG.");
        return municipioService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    @PermitAll
    public List<MunicipioResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {
        LOG.infof("Pesquisando Município po nome.", nome);
        LOG.debug("ERRO DE DEBUG.");
        return municipioService.getByNome(nome);
    }

    @GET
    @Path("/searchByNomeEstado/{nomeEstado}")
    @PermitAll
    public List<MunicipioResponseDTO> getByNomeEstado(@PathParam("nomeEstado") String nomeEstado) throws NullPointerException {
        LOG.infof("Buscando município por nome do estado.", nomeEstado);
        LOG.debug("ERRO DE DEBUG.");
        return municipioService.getByNomeEstado(nomeEstado);
    }

    @GET
    @Path("/searchBySiglaEstado/{siglaEstado}")
    @PermitAll
    public List<MunicipioResponseDTO> getBySiglaEstado(@PathParam("siglaEstado") String siglaEstado)
            throws NullPointerException {
                LOG.infof("Buscando município por sigla do estado.", siglaEstado);
                LOG.debug("ERRO DE DEBUG.");
        return municipioService.getBySiglaEstado(siglaEstado);
    }
}

/* */
