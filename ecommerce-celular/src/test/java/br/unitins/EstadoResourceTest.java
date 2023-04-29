package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
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
    public void countTest() {

        given()
            .when().get("/estados/count")
            .then()
                .statusCode(200);
    }

    @Test
    public void getByIdTest() {

        EstadoDTO estado = new EstadoDTO(
            "Roraima", 
            "RR"
        );

        Long id = estadoService.insert(estado).id();

        given()
            .when().get("/estados/" + id)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        EstadoDTO estado = new EstadoDTO(
            "Roraima",
            "RR"
        );

        String nome = estadoService.insert(estado).nome();

        given()
            .when().get("/estados/searchByNome/" + nome)
            .then()
                .statusCode(200);
    }

    @Test
    public void getBySiglaTest() {

        EstadoDTO estado = new EstadoDTO(
            "Roraima",
            "RR"
        );

        String sigla = estadoService.insert(estado).sigla();

        given()
            .when().get("/estados/searchBySigla/" + sigla)
            .then()
                .statusCode(200);
    }
}
