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
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    @Inject
    UsuarioService usuarioService;

    @GET
    public List<UsuarioResponseDTO> getAll() {

        return usuarioService.getAll();
    }

    @GET
    @Path("/{id}")
    public UsuarioResponseDTO getById(@PathParam("id") Long id) throws NotFoundException {

        return usuarioService.getById(id);
    }

    @POST
    public Response insert(UsuarioDTO usuarioDto) {

        try {

            return Response
                    .status(Status.CREATED) // 201
                    .entity(usuarioService.insert(usuarioDto))
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
    public Response update(@PathParam("id") Long id, UsuarioDTO usuarioDto) {

        try {

            usuarioService.update(id, usuarioDto);

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

        usuarioService.delete(id);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/count")
    public Long count() {

        return usuarioService.count();
    }

    @GET
    @Path("/lista_desejo/{idUsuario}")
    public ListaDesejoResponseDTO getListaDesejo(@PathParam("idUsuario") Long idUsuario) {

        return usuarioService.getListaDesejo(idUsuario);
    }

    @POST
    @Path("/lista_desejo")
    public Response insertListaDesejo(ListaDesejoDTO listaDto) {

        try {

            usuarioService.insertProdutoIntoListaDesejo(listaDto);

            return Response
                    .status(Status.CREATED) // 201
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
    @Path("/lista_desejo/{idUsuario}/{idProduto}")
    public Response deleteProdutoFromListaDesejo(@PathParam("idUsuario") Long idUsuario, @PathParam("idProduto") Long idProdutoListaDesejo) {

        usuarioService.deleteProdutoFromListaDesejo(idUsuario, idProdutoListaDesejo);

        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/lista_desejo/count/{id}")
    public Integer countListaDesejo(@PathParam("id") Long id) {

        return usuarioService.countListaDesejo(id);
    }

    @GET
    @Path("/searchByNome/{nome}")
    public List<UsuarioResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {

        return usuarioService.getByNome(nome);
    }

}
