package br.unitins;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.usuario.perfil.AuthUsuarioDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class AuthResourceTest {

    @Test
    public void login() {

        AuthUsuarioDTO authUsuarioDTO = new AuthUsuarioDTO("JoaoA", "joao1234");

        given()
            .contentType(ContentType.JSON)
            .body(authUsuarioDTO)
            .when().post("/auth")
            .then()
            .statusCode(200);
    }

    @Test
    public void loginFailed() {

        AuthUsuarioDTO authUsuarioDTO = new AuthUsuarioDTO("JoaoA", "joao12345");

        given()
            .contentType(ContentType.JSON)
            .body(authUsuarioDTO)
            .when().post("/auth")
            .then()
            .statusCode(404);
    }
}
