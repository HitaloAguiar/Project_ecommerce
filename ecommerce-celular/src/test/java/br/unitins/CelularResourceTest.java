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

import br.unitins.ecommerce.dto.celular.CelularDTO;
import br.unitins.ecommerce.dto.celular.CelularResponseDTO;
import br.unitins.ecommerce.service.celular.CelularService;

@QuarkusTest
public class CelularResourceTest {
    
    @Inject
    CelularService celularService;

    @Test
    public void getAllTest() {

        given()
            .when().get("/celulares")
            .then()
            .statusCode(200);
    }

    @Test
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
    public void updateTest() {

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

        CelularDTO celularUpdate = new CelularDTO(
            "iPhone 7",
            "4Gb Memória RAM, 128Gb armazenamento",
            1l,
            2299.00,
            0,
            10f,
            2,
            3);

        given()
          .contentType(ContentType.JSON)
          .body(celularUpdate)
          .when().put("/celulares/" + id)
          .then()
             .statusCode(204);

        CelularResponseDTO celularResponse = celularService.getById(id);

        assertThat(celularResponse.nome(), is("iPhone 7"));
        assertThat(celularResponse.descricao(), is("4Gb Memória RAM, 128Gb armazenamento"));
        assertThat(celularResponse.preco(), is(2299.0));
        assertThat(celularResponse.estoque(), is("Estoque esgotado"));
        assertThat(celularResponse.nomeMarca(), is("Apple"));
        assertThat(celularResponse.versãoSistemaOperacional(), is(10f));
        assertThat(celularResponse.sistemaOperacional().getLabel(), is("iOS"));
        assertThat(celularResponse.cor().getLabel(), is("Branco"));
    }

    @Test
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
    public void countTest() {

        given()
            .when().get("/celulares/count")
            .then()
                .statusCode(200);
    }

    @Test
    public void getByIdTest() {

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
            .when().get("/celulares/" + id)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByNomeTest() {

        CelularDTO celularDto = new CelularDTO(
            "LG K61",
            "4Gb Memória RAM, 64Gb armazenamento",
            3l,
            1389.00,
            40,
            10f,
            1,
            9);

        String nome = celularService.insert(celularDto).nome();

        given()
            .when().get("/celulares/searchByNome/" + nome)
            .then()
                .statusCode(200);
    }

    @Test
    public void getBySistemaOperacionalTest() {

        CelularDTO celularDto = new CelularDTO(
            "LG K61",
            "4Gb Memória RAM, 64Gb armazenamento",
            3l,
            1389.00,
            40,
            10f,
            1,
            9);

        Integer sistemaOperacional = celularService.insert(celularDto).sistemaOperacional().getId();

        given()
            .when().get("/celulares/searchBySistemaOperacional/" + sistemaOperacional)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByCorTest() {

        CelularDTO celularDto = new CelularDTO(
            "LG K61",
            "4Gb Memória RAM, 64Gb armazenamento",
            3l,
            1389.00,
            40,
            10f,
            1,
            9);

        Integer cor = celularService.insert(celularDto).cor().getId();

        given()
            .when().get("/celulares/searchByCor/" + cor)
            .then()
                .statusCode(200);
    }

    @Test
    public void getByMarcaTest() {

        CelularDTO celularDto = new CelularDTO(
            "LG K61",
            "4Gb Memória RAM, 64Gb armazenamento",
            3l,
            1389.00,
            40,
            10f,
            1,
            9);

        String marca = celularService.insert(celularDto).nomeMarca();

        given()
            .when().get("/celulares/searchByMarca/" + marca)
            .then()
                .statusCode(200);
    }

    @Test
    public void filterByPrecoMinTest() {

        Double precoMin = 1200.0;

        given()
            .when().get("/celulares/filterByPrecoMin/" + precoMin)
            .then()
                .statusCode(200);
    }

    @Test
    public void filterByPrecoMaxTest() {

        Double precoMax = 1400.0;

        given()
            .pathParam("precoMax", precoMax)
            .when()
            .get("/celulares/filterByPrecoMax/{precoMax}")
            .then()
                .statusCode(200);
    }

    @Test
    public void filterByEntrePrecoTest() {

        Double precoMin = 1200.0;
        Double precoMax = 2000.0;

        given()
            .pathParam("precoMin", precoMin)
            .pathParam("precoMax", precoMax)
            .when()
            .get("/celulares/filterByEntrePreco/{precoMin}/{precoMax}")
            .then()
                .statusCode(200);
    }
}
