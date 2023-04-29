package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.municipio.MunicipioDTO;
import br.unitins.ecommerce.dto.municipio.MunicipioResponseDTO;
import br.unitins.ecommerce.service.muncipio.MunicipioService;

import javax.inject.Inject;

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
    public void countTest() {

        given()
            .when().get("/municipios/count")
            .then()
                .statusCode(200);
    }

    @Test
    public void getByIdTest() {

        MunicipioDTO municipio = new MunicipioDTO(
            "Miracema do Tocantins",
            5l
        );

        Long id = municipioService.insert(municipio).id();

        given()
            .when().get("/municipios/" + id)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        MunicipioDTO municipio = new MunicipioDTO(
            "Miracema do Tocantins",
            5l
        );

        String nome = municipioService.insert(municipio).nome();

        given()
            .when().get("/municipios/searchByNome/" + nome)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeEstadoTest() {

        MunicipioDTO municipio = new MunicipioDTO(
            "Miracema do Tocantins",
            5l
        );

        String nomeEstado = (String) municipioService.insert(municipio).estado().get("nome");

        given()
            .when().get("/municipios/searchByNomeEstado/" + nomeEstado)
            .then()
                .statusCode(200);
    }

    @Test
    public void getBySiglaEstadoTest() {

        MunicipioDTO municipio = new MunicipioDTO(
            "Miracema do Tocantins",
            5l
        );

        String siglaEstado = (String) municipioService.insert(municipio).estado().get("sigla");

        given()
            .when().get("/municipios/searchBySiglaEstado/" + siglaEstado)
            .then()
                .statusCode(200);
    }
}