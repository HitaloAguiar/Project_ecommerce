package br.unitins.ecommerce.resource;

import org.jboss.logging.Logger;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.ecommerce.application.Result;
import br.unitins.ecommerce.dto.compra.CartaoCreditoDTO;
import br.unitins.ecommerce.dto.compra.ItemCompraDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.compra.CompraService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken tokenJwt;

    private static final Logger LOG = Logger.getLogger(CompraResource.class);

    @GET
    @Path("/historico-compras")
    @RolesAllowed({ "User" })
    public Response getAll() {
        Result result = null;

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        try {
            LOG.infof("Buscando histórico de compras para o usuário: " + usuario.getId());
            return Response.ok(compraService.getAll(usuario.getId())).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao recuperar histórico de compras.", e);

            result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/carrinho")
    @RolesAllowed({ "User" })
    public Response getCompraEmAndamento() {
        Result result = null;

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        try {
            LOG.infof("Buscando compra em andamento para o usuário: " + usuario.getId());

            return Response.ok(compraService.getCompraEmAndamento(usuario.getId())).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao buscar compra em andamento.", e);
            result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @POST
    @Path("/carrinho/adiconar-item")
    @RolesAllowed({ "User" })
    public Response insertIntoCarrrinho(ItemCompraDTO itemCompraDTO) {
        Result result = null;

        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            compraService.insertItemIntoCompra(usuario.getId(), itemCompraDTO);

            LOG.info("Item inserido no carrinho com sucesso.");
            return Response.status(Status.CREATED).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao adicionar item no carrinho.", e);

            result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/carrinho/remover-item/{idProduto}")
    @RolesAllowed({ "User" })
    public Response removeItemFromCarrinho(@PathParam("idProduto") Long idProduto) {
        Result result = null;

        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            compraService.removeItemCompra(usuario.getId(), idProduto);

            LOG.info("Item removido do carrinho com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao remover item do carrinho.", e);

            result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/carrinho/cancelar-compra")
    @RolesAllowed({ "User" })
    public Response cancelarCompra() {
        Result result = null;

        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            compraService.cancelarCompra(usuario.getId());

            LOG.info("Compra cancelada com sucesso.");
            return Response.status(Status.NO_CONTENT).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao cancelar compra.", e);

            result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/carrinho/pagar-boleto-bancario")
    @RolesAllowed({ "User" })
    public Response pagarBoletoBancario() {

        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            compraService.efetuarPagamentoBoleto(usuario.getId());

            LOG.info("Pagamento com boleto efetuado com sucesso.");
            return Response.status(Status.ACCEPTED).build();
        } catch (

        NullPointerException e) {

            Result result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/carrinho/pagar-pix")
    @RolesAllowed({ "User" })
    public Response pagarPix() {
        Result result = null;

        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            compraService.efetuarPagamentoPix(usuario.getId());

            LOG.info("Pagamento com pix efetuado com sucesso.");
            return Response.status(Status.ACCEPTED).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao efetuar o pagamento com pix.", e);
            result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/carrinho/pagar-cartao-credito")
    @RolesAllowed({ "User" })
    public Response pagarCartaoCredito(CartaoCreditoDTO cartaoCreditoDTO) {
        Result result = null;

        try {

            String login = tokenJwt.getSubject();

            Usuario usuario = usuarioService.getByLogin(login);

            compraService.efetuarPagamentoCartaoCredito(usuario.getId(), cartaoCreditoDTO);

            LOG.info("Pagamento com cartão de crédito efetuado com sucesso.");
            return Response.status(Status.ACCEPTED).build();
        } catch (NullPointerException e) {
            LOG.error("Erro ao efetuar o pagamento com cartão de crédito.", e);
            result = new Result(e.getMessage(), false);

            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }
}
