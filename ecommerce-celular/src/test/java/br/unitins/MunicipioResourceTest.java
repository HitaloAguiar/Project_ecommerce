package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.municipio.MunicipioDTO;
import br.unitins.ecommerce.dto.municipio.MunicipioResponseDTO;
import br.unitins.ecommerce.service.municipio.MunicipioService;
import jakarta.inject.Inject;

@QuarkusTest
public class MunicipioResourceTest {

    @Inject
    MunicipioService municipioService;

    @Test
    public void getAllTest() {

        given()
                .when().get("/municipios")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void insertTest() {

        MunicipioDTO municipio = new MunicipioDTO(
                "Miracema do Tocantins", 
                5l);

        given()
                .contentType(ContentType.JSON)
                .body(municipio)
                .when().post("/municipios")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("Miracema do Tocantins"),
                "estado.get(\"nome\")", is("Tocantins"), "estado.sigla", is("TO"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void insertForbiddenTest() {

        MunicipioDTO municipio = new MunicipioDTO(
                "Miracema do Tocantins", 
                5l);

        given()
                .contentType(ContentType.JSON)
                .body(municipio)
                .when().post("/municipios")
                .then()
                .statusCode(403);
    }

    @Test
    public void insertUnauthorizedTest() {

        MunicipioDTO municipio = new MunicipioDTO(
                "Miracema do Tocantins", 
                5l);

        given()
            .contentType(ContentType.JSON)
            .body(municipio)
            .when().post("/municipios")
            .then()
            .statusCode(401);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void updateTest() {

        MunicipioDTO municipioDto = new MunicipioDTO(
                "Miracema do Tocantins",
                5l);

        Long id = municipioService.insert(municipioDto).id();

        MunicipioDTO municipioUpdate = new MunicipioDTO(
            "Rio Verde",
            3l
        );

        given()
          .contentType(ContentType.JSON)
          .body(municipioUpdate)
          .when().put("/municipios/" + id)
          .then()
             .statusCode(204);

        MunicipioResponseDTO municipioResponse = municipioService.getById(id);

        assertThat(municipioResponse.nome(), is("Rio Verde"));
        assertThat(municipioResponse.estado().get("nome"), is("Goiás"));
        assertThat(municipioResponse.estado().get("sigla"), is("GO"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void deleteTest() {

        MunicipioDTO municipio = new MunicipioDTO(
            "Miracema do Tocantins",
            5l
        );

        Long id = municipioService.insert(municipio).id();

        given()
          .when().delete("/municipios/" + id)
          .then()
             .statusCode(204);

        MunicipioResponseDTO municipioResponse = null;

        try {
            
            municipioResponse =  municipioService.getById(id);
        } catch (Exception e) {

        }
        finally {
            assertNull(municipioResponse);   
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void countTest() {

        given()
            .when().get("/municipios/count")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getByIdTest() {

        given()
            .when().get("/municipios/" + 2)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        given()
            .when().get("/municipios/searchByNome/" + "man")
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeEstadoTest() {

        given()
            .when().get("/municipios/searchByNomeEstado/" + "ama")
            .then()
                .statusCode(200);
    }

    @Test
    public void getBySiglaEstadoTest() {

        given()
            .when().get("/municipios/searchBySiglaEstado/" + "go")
            .then()
                .statusCode(200);
    }
}