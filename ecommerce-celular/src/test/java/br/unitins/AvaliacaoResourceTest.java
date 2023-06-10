package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.avaliacao.AvaliacaoDTO;
import br.unitins.ecommerce.dto.avaliacao.AvaliacaoResponseDTO;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
public class AvaliacaoResourceTest {

    @Inject
    AvaliacaoService avaliacaoService;

    @Test
    @TestSecurity(user = "testUser", roles = {"User_Basic", "User"})
    public void getAllTest() {

        given()
                .when().get("/avaliacoes")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getAllForbiddenTest() {

        given()
                .when().get("/avaliacoes")
                .then()
                .statusCode(403);
    }

    @Test
    public void getAllUnauthorizedTest() {

        given()
                .when().get("/avaliacoes")
                .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin", "User"})
    public void getByIdTest() {

        given()
                .when().get("/avaliacoes/" + 2)
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void insertTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "muito bão",
                5,
                3l,
                2l);

        given()
                .contentType(ContentType.JSON)
                .body(avaliacao)
                .when().post("/avaliacoes")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "comentario", is("muito bão"),
                        "estrela.label", is("⭐⭐⭐⭐⭐"),
                        "produto.id", is(3),
                        "produto.nome", is("Moto G32"),
                        "usuario.id", is(2),
                        "usuario.login", is("MariaFer"),
                        "usuario.email", is("mariaF@gmail.com"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void updateTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "muito bão",
                5,
                3l,
                2l);

        Long id = avaliacaoService.insert(avaliacao).id();

        AvaliacaoDTO avaliacaoUpdate = new AvaliacaoDTO(
                "Ruim",
                1,
                2l,
                3l);

        given()
                .contentType(ContentType.JSON)
                .body(avaliacaoUpdate)
                .when().put("/avaliacoes/" + id)
                .then()
                .statusCode(204);

        AvaliacaoResponseDTO avaliacaoResponse = avaliacaoService.getById(id);

        assertThat(avaliacaoResponse.comentario(), is("Ruim"));
        assertThat(avaliacaoResponse.estrela().getLabel(), is("⭐"));
        assertThat(avaliacaoResponse.produto().get("id"), is(2l));
        assertThat(avaliacaoResponse.produto().get("nome"), is("iPhone 10"));
        assertThat(avaliacaoResponse.usuario().get("id"), is(3l));
        assertThat(avaliacaoResponse.usuario().get("login"), is("PauloVitor"));
        assertThat(avaliacaoResponse.usuario().get("email"), is("paulo_gaymer@gmail.com"));

    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void deleteTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "muito bão",
                5,
                3l,
                2l);

        Long id = avaliacaoService.insert(avaliacao).id();

        given()
                .when().delete("/avaliacoes/" + id)
                .then()
                .statusCode(204);

        AvaliacaoResponseDTO avaliacaoResponse = null;

        try {

            avaliacaoResponse = avaliacaoService.getById(id);
        } catch (Exception e) {

        } finally {
            assertNull(avaliacaoResponse);
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void countTest() {

        given()
                .when().get("/avaliacoes/count")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void getByYearTest() {

        AvaliacaoDTO avaliacao = new AvaliacaoDTO(
                "muito bão",
                5,
                3l,
                2l);

        Integer anoAvalicao = avaliacaoService.insert(avaliacao).data().getYear();

        given()
                .when().get("/avaliacoes/searchByYear/" + anoAvalicao)
                .then()
                .statusCode(200);
    }

}
