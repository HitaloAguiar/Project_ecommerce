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
import br.unitins.ecommerce.dto.celular.CelularDTO;
import br.unitins.ecommerce.dto.celular.CelularResponseDTO;
import br.unitins.ecommerce.service.celular.CelularService;

@Path("/celulares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CelularResource {
    
    @Inject
    CelularService celularService;

    private static final Logger LOG = Logger.getLogger(CelularResource.class);

    @GET
    @PermitAll
    public List<CelularResponseDTO> getAll() {

        LOG.info("Buscando todas os produtos");
        LOG.debug("ERRO DE DEBUG.");

        return celularService.getAll();
    }

    @GET
    @PermitAll
    @Path("/{id}")
    public CelularResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        LOG.infof("Buscando produtos por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");

        return celularService.getById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(CelularDTO celularDto) {

        LOG.infof("Inserindo um produto: %s", celularDto.nome());

        Result result;

        try {

            LOG.infof("Produto inserido na lista Desejo.");
            return Response
                    .status(Status.CREATED) // 201
                    .entity(celularService.insert(celularDto))
                    .build();
        } catch (ConstraintViolationException e) {

            LOG.error("Erro ao incluir um produto.");
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        } catch (Exception e) {

            LOG.fatal("Erro sem identificacao: " + e.getMessage());

            result = new Result(e.getMessage(), false);

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @PUT
    @RolesAllowed({"Admin"})
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CelularDTO celularDto) {

        Result result;

        try {

            celularService.update(id, celularDto);

            LOG.infof("Produto (%d) atualizado com sucesso.", id);

            return Response
                    .status(Status.NO_CONTENT) // 204
                    .build();
        } catch (ConstraintViolationException e) {

            LOG.errorf("Erro ao atualizar um produto. ", id, e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        } catch (Exception e) {
            
            LOG.fatal("Erro sem identificacao: " + e.getMessage());

            result = new Result(e.getMessage(), false);

            return Response
                    .status(Status.NOT_FOUND)
                    .entity(result)
                    .build();
        }
    }

    @DELETE
    @RolesAllowed({"Admin"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {

        try {

            celularService.delete(id);

            LOG.infof("Produto excluído com sucesso.", id);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();
        } catch (IllegalArgumentException e) {

            LOG.error("Erro ao deletar produto: parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @RolesAllowed({"Admin"})
    @Path("/count")
    public Long count() {

        LOG.info("Contando todos os produtos.");
        LOG.debug("ERRO DE DEBUG.");

        return celularService.count();
    }

    @GET
    @PermitAll
    @Path("/searchByNome/{nome}")
    public List<CelularResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {

        LOG.infof("Buscando produto pelo nome. ", nome);
        LOG.debug("ERRO DE DEBUG.");

        return celularService.getByNome(nome);
    }

    @GET
    @PermitAll
    @Path("/searchBySistemaOperacional/{sistemaOperacional}")
    public List<CelularResponseDTO> getBySistemaOperacional(@PathParam("sistemaOperacional") Integer sistemaOperacional) throws IndexOutOfBoundsException, NullPointerException {

        LOG.infof("Buscando pelo sistema operacional. ", sistemaOperacional);
        LOG.debug("ERRO DE DEBUG.");
        return celularService.getBySistemaOperacional(sistemaOperacional);
    }

    @GET
    @PermitAll
    @Path("/searchByCor/{cor}")
    public List<CelularResponseDTO> getByCor(@PathParam("cor") Integer cor) throws IndexOutOfBoundsException, NullPointerException {

        LOG.infof("Buscando pela cor. ", cor);
        LOG.debug("ERRO DE DEBUG.");
        return celularService.getByCor(cor);
    }

    @GET
    @PermitAll
    @Path("/searchByMarca/{marca}")
    public List<CelularResponseDTO> getByMarca (@PathParam("marca") String nomeMarca) throws NullPointerException {

        LOG.infof("Buscando pelo nome da marca. ", nomeMarca);
        LOG.debug("ERRO DE DEBUG.");
        return celularService.getByMarca(nomeMarca);
    }

    @GET
    @PermitAll
    @Path("/filterByPrecoMin/{precoMin}")
    public List<CelularResponseDTO> filterByPrecoMin (@PathParam("precoMin") Double preco) throws NullPointerException {

        LOG.infof("Filtrando pelo preço mínimo. ", preco);
        LOG.debug("ERRO DE DEBUG.");

        return celularService.filterByPrecoMin(preco);
    }

    @GET
    @PermitAll
    @Path("/filterByPrecoMax/{precoMax}")
    public List<CelularResponseDTO> filterByPrecoMax (@PathParam("precoMax") Double preco) throws NullPointerException {

        LOG.infof("Filtrando pelo preço máximo. ", preco);
        LOG.debug("ERRO DE DEBUG.");
        return celularService.filterByPrecoMax(preco);
    }

    @GET
    @PermitAll
    @Path("/filterByEntrePreco/{precoMin}/{precoMax}")
    public List<CelularResponseDTO> filterByEntrePreco (@PathParam("precoMin") Double precoMin, @PathParam("precoMax") Double precoMax) throws NullPointerException {

        LOG.infof("Filtrando entre os preços mínimo e máximo. ", precoMin, " e ", precoMax);
        LOG.debug("ERRO DE DEBUG.");
        return celularService.filterByEntrePreco(precoMin, precoMax);
    }
}
