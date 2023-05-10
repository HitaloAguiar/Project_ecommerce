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
import br.unitins.ecommerce.dto.celular.CelularDTO;
import br.unitins.ecommerce.dto.celular.CelularResponseDTO;
import br.unitins.ecommerce.service.celular.CelularService;

@Path("/celulares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CelularResource {
    
    @Inject
    CelularService celularService;

    @GET
    public List<CelularResponseDTO> getAll() {

        return celularService.getAll();
    }

    @GET
    @Path("/{id}")
    public CelularResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return celularService.getById(id);
    }

    @POST
    public Response insert(CelularDTO celularDto) {

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(celularService.insert(celularDto))
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
    public Response update(@PathParam("id") Long id, CelularDTO celularDto) {

        try {

            celularService.update(id, celularDto);

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

        celularService.delete(id);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/count")
    public Long count() {

        return celularService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    public List<CelularResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {

        return celularService.getByNome(nome);
    }

    @GET
    @Path("/searchBySistemaOperacional/{sistemaOperacional}")
    public List<CelularResponseDTO> getBySistemaOperacional(@PathParam("sistemaOperacional") Integer sistemaOperacional) throws IndexOutOfBoundsException, NullPointerException {

        return celularService.getBySistemaOperacional(sistemaOperacional);
    }

    @GET
    @Path("/searchByCor/{cor}")
    public List<CelularResponseDTO> getByCor(@PathParam("cor") Integer cor) throws IndexOutOfBoundsException, NullPointerException {

        return celularService.getByCor(cor);
    }

    @GET
    @Path("/searchByMarca/{marca}")
    public List<CelularResponseDTO> getByMarca (@PathParam("marca") String nomeMarca) throws NullPointerException {

        return celularService.getByMarca(nomeMarca);
    }

    @GET
    @Path("/filterByPrecoMin/{precoMin}")
    public List<CelularResponseDTO> filterByPrecoMin (@PathParam("precoMin") Double preco) throws NullPointerException {

        return celularService.filterByPrecoMin(preco);
    }

    @GET
    @Path("/filterByPrecoMax/{precoMax}")
    public List<CelularResponseDTO> filterByPrecoMax (@PathParam("precoMax") Double preco) throws NullPointerException {

        return celularService.filterByPrecoMax(preco);
    }

    @GET
    @Path("/filterByEntrePreco/{precoMin}/{precoMax}")
    public List<CelularResponseDTO> filterByEntrePreco (@PathParam("precoMin") Double precoMin, @PathParam("precoMax") Double precoMax) throws NullPointerException {

        return celularService.filterByEntrePreco(precoMin, precoMax);
    }
}
