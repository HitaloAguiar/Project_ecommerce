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

import br.unitins.ecommerce.dto.celular.CelularDTO;
import br.unitins.ecommerce.dto.celular.CelularResponseDTO;
import br.unitins.ecommerce.service.celular.CelularService;

@QuarkusTest
public class CelularResourceTest {
    
    @Inject
    CelularService celularService;

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getAllTest() {

        given()
            .when().get("/celulares")
            .then()
            .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void insertTest() {

        CelularDTO celularDto = new CelularDTO(
                "LG K61",
                "4Gb Memória RAM, 64Gb armazenamento",
                3l,
                1389.00,
                40,
                10f,
                1,
                9);

        given()
                .contentType(ContentType.JSON)
                .body(celularDto)
                .when().post("/celulares")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                "nome", is("LG K61"),
                "descricao", is("4Gb Memória RAM, 64Gb armazenamento"),
                "preco", is(1389.0F),
                "estoque", is("Disponível"),
                "nomeMarca", is("LG"),
                "versãoSistemaOperacional", is(10f),
                "sistemaOperacional.label", is("Android"),
                "cor.label", is("Vermelho"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void insertForbiddenTest() {

        CelularDTO celularDto = new CelularDTO(
                "LG K61",
                "4Gb Memória RAM, 64Gb armazenamento",
                3l,
                1389.00,
                40,
                10f,
                1,
                9);

        given()
                .contentType(ContentType.JSON)
                .body(celularDto)
                .when().post("/celulares")
                .then()
                .statusCode(403);
    }

    @Test
    public void insertUnauthorizedTest() {

        CelularDTO celularDto = new CelularDTO(
                "LG K61",
                "4Gb Memória RAM, 64Gb armazenamento",
                3l,
                1389.00,
                40,
                10f,
                1,
                9);

        given()
                .contentType(ContentType.JSON)
                .body(celularDto)
                .when().post("/celulares")
                .then()
                .statusCode(401);
    }

    // @Test
    // @TestSecurity(user = "testUser", roles = {"Admin"})
    // public void updateTest() {

    //     CelularDTO celularDto = new CelularDTO(
    //             "LG K61",
    //             "4Gb Memória RAM, 64Gb armazenamento",
    //             3l,
    //             1389.00,
    //             40,
    //             10f,
    //             1,
    //             9);

    //     Long id = celularService.insert(celularDto).id();

    //     CelularDTO celularUpdate = new CelularDTO(
    //         "iPhone 7",
    //         "4Gb Memória RAM, 128Gb armazenamento",
    //         1l,
    //         2299.00,
    //         0,
    //         10f,
    //         2,
    //         3);

    //     given()
    //       .contentType(ContentType.JSON)
    //       .body(celularUpdate)
    //       .when().put("/celulares/" + id)
    //       .then()
    //          .statusCode(204);

    //     CelularResponseDTO celularResponse = celularService.getById(id);

    //     assertThat(celularResponse.nome(), is("iPhone 7"));
    //     assertThat(celularResponse.descricao(), is("4Gb Memória RAM, 128Gb armazenamento"));
    //     assertThat(celularResponse.preco(), is(2299.0));
    //     assertThat(celularResponse.estoque(), is("Estoque esgotado"));
    //     assertThat(celularResponse.nomeMarca(), is("Apple"));
    //     assertThat(celularResponse.versãoSistemaOperacional(), is(10f));
    //     assertThat(celularResponse.sistemaOperacional().getLabel(), is("iOS"));
    //     assertThat(celularResponse.cor().getLabel(), is("Branco"));
    // }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void deleteTest() {

        CelularDTO celularDto = new CelularDTO(
                "LG K61",
                "4Gb Memória RAM, 64Gb armazenamento",
                3l,
                1389.00,
                40,
                10f,
                1,
                9);

        Long id = celularService.insert(celularDto).id();

        given()
          .when().delete("/celulares/" + id)
          .then()
             .statusCode(204);

        CelularResponseDTO celularResponse = null;

        try {
            
            celularResponse =  celularService.getById(id);
        } catch (Exception e) {

        }
        finally {
            assertNull(celularResponse);   
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void countTest() {

        given()
            .when().get("/celulares/count")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getByIdTest() {

        given()
            .when().get("/celulares/" + 1)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        given()
            .when().get("/celulares/searchByNome/" + "lg")
            .then()
                .statusCode(200);
    }

    @Test
    public void getBySistemaOperacionalTest() {

        given()
            .when().get("/celulares/searchBySistemaOperacional/" + 1)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByCorTest() {

        given()
            .when().get("/celulares/searchByCor/" + 3)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByMarcaTest() {

        given()
            .when().get("/celulares/searchByMarca/" + "app")
            .then()
                .statusCode(200);
    }

    @Test
    public void filterByPrecoMinTest() {

        given()
            .when().get("/celulares/filterByPrecoMin/" + 1200.0)
            .then()
                .statusCode(200);
    }

    @Test
    public void filterByPrecoMaxTest() {

        given()
            .pathParam("precoMax", 1400.0)
            .when()
            .get("/celulares/filterByPrecoMax/{precoMax}")
            .then()
                .statusCode(200);
    }

    @Test
    public void filterByEntrePrecoTest() {

        given()
            .pathParam("precoMin", 1200.0)
            .pathParam("precoMax", 2000.0)
            .when()
            .get("/celulares/filterByEntrePreco/{precoMin}/{precoMax}")
            .then()
                .statusCode(200);
    }
}
