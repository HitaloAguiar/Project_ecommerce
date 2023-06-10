package br.unitins;

import static io.restassured.RestAssured.given;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.compra.CartaoCreditoDTO;
import br.unitins.ecommerce.dto.compra.ItemCompraDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.compra.CompraService;
import br.unitins.ecommerce.service.token.TokenJwtService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class CompraResourceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    CompraService compraService;

    @Inject
    TokenJwtService tokenService;
    
    @Test
    public void getAllTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token) // Adiciona o cabeçalho de autenticação
        .when()
            .get("/compras/historico-compras")
            .then()
                .statusCode(200);
    }

    @Test
    public void getCompraEmAndamentoTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        ItemCompraDTO itemCompraDTO = new ItemCompraDTO(1l, 12);

        compraService.insertItemIntoCompra(usuario.getId(), itemCompraDTO);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .get("/compras/carrinho")
            .then()
                .statusCode(200);
    }

    @Test
    public void insertIntoCarrrinhoTest() {

        ItemCompraDTO itemCompraDTO = new ItemCompraDTO(1l, 10);

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(itemCompraDTO)
            .when()
                .post("/compras/carrinho/adiconar-item")
            .then()
                .statusCode(201);
    }

    @Test
    public void removeItemFromCarrinhoTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        ItemCompraDTO itemCompraDTO = new ItemCompraDTO(2l, 5);

        compraService.insertItemIntoCompra(usuario.getId(), itemCompraDTO);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .patch("/compras/carrinho/remover-item/" + 2l)
            .then()
                .statusCode(204);
    }

    @Test
    public void cancelarCompraTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        ItemCompraDTO itemCompraDTO = new ItemCompraDTO(1l, 20);

        compraService.insertItemIntoCompra(usuario.getId(), itemCompraDTO);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .delete("/compras/carrinho/cancelar-compra")
            .then()
                .statusCode(204);
    }

    @Test
    public void pagarBoletoBancarioTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        ItemCompraDTO itemCompraDTO = new ItemCompraDTO(1l, 11);

        compraService.insertItemIntoCompra(usuario.getId(), itemCompraDTO);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .patch("/compras/carrinho/pagar-boleto-bancario")
            .then()
                .statusCode(202);
    }

    @Test
    public void pagarPixTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        ItemCompraDTO itemCompraDTO = new ItemCompraDTO(3l, 3);

        compraService.insertItemIntoCompra(usuario.getId(), itemCompraDTO);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .patch("/compras/carrinho/pagar-pix")
            .then()
                .statusCode(202);
    }

    @Test
    public void pagarCartaoCreditoTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        ItemCompraDTO itemCompraDTO = new ItemCompraDTO(2l, 7);

        compraService.insertItemIntoCompra(usuario.getId(), itemCompraDTO);

        CartaoCreditoDTO cartaoCreditoDTO = new CartaoCreditoDTO("3234654354325589", "Maria Fernanda", LocalDate.now().plusMonths(10), "544", 2);

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(cartaoCreditoDTO)
            .when()
                .patch("/compras/carrinho/pagar-cartao-credito")
            .then()
                .statusCode(202);
    }
}
