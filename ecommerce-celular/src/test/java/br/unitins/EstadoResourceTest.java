package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;
import br.unitins.ecommerce.service.estado.EstadoService;

@QuarkusTest
public class EstadoResourceTest {
    
    @Inject
    EstadoService estadoService;

    @Test
    public void getAllTest() {

        given()
                .when().get("/estados")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void insertTest() {

        EstadoDTO estado = new EstadoDTO(
                "Roraima", 
                "RR");

        given()
            .contentType(ContentType.JSON)
            .body(estado)
            .when().post("/estados")
            .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("Roraima"),
                "sigla", is("RR"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void insertForbiddenTest() {

        EstadoDTO estado = new EstadoDTO(
                "Roraima", 
                "RR");

        given()
            .contentType(ContentType.JSON)
            .body(estado)
            .when().post("/estados")
            .then()
                .statusCode(403);
    }

    @Test
    public void insertUnauthorizedTest() {

        EstadoDTO estado = new EstadoDTO(
                "Roraima", 
                "RR");

        given()
            .contentType(ContentType.JSON)
            .body(estado)
            .when().post("/estados")
            .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void updateTest() {

        EstadoDTO estado = new EstadoDTO(
            "Roraima", 
            "RR");

        Long id = estadoService.insert(estado).id();

        EstadoDTO estadoUpdate = new EstadoDTO(
            "Mato Grosso",
            "MT"
        );

        given()
          .contentType(ContentType.JSON)
          .body(estadoUpdate)
          .when().put("/estados/" + id)
          .then()
             .statusCode(204);

        EstadoResponseDTO estadoResponse = estadoService.getById(id);

        assertThat(estadoResponse.nome(), is("Mato Grosso"));
        assertThat(estadoResponse.sigla(), is("MT"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void deleteTest() {

        EstadoDTO estado = new EstadoDTO(
            "Roraima", 
            "RR"
        );

        Long id = estadoService.insert(estado).id();

        given()
          .when().delete("/estados/" + id)
          .then()
             .statusCode(204);

        EstadoResponseDTO estadoResponse = null;

        try {
            
            estadoResponse =  estadoService.getById(id);
        } catch (Exception e) {

        }
        finally {
            assertNull(estadoResponse);   
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void countTest() {

        given()
            .when().get("/estados/count")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getByIdTest() {

        given()
            .when().get("/estados/" + 5)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        given()
            .when().get("/estados/searchByNome/" + "ac")
            .then()
                .statusCode(200);
    }

    @Test
    public void getBySiglaTest() {

        given()
            .when().get("/estados/searchBySigla/" + "to")
            .then()
                .statusCode(200);
    }
}
