package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.token.TokenJwtService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import br.unitins.ecommerce.service.usuario.lista_desejo.ListaDesejoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;

@QuarkusTest
public class ListaDesejoResourceTest {

    @Inject
    ListaDesejoService listaDesejoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;
    
    @Test
    @TestSecurity(user = "testUser", roles = {"Admin", "User"})
    public void getListaDesejoTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/lista_desejo")
            .then()
            .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin", "User"})
    public void insertListaDesejoTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .when().patch("/lista_desejo/inserir_produto/" + 1l)
            .then()
            .statusCode(201);

        ListaDesejoResponseDTO listaResponse = listaDesejoService.getListaDesejo(2l);
    
        assertThat(listaResponse.usuario().get("id"), is(2l));
        assertThat(listaResponse.usuario().get("login"), is("MariaFer"));
        assertThat(listaResponse.usuario().get("email"), is("mariaF@gmail.com"));
        assertThat(listaResponse.produtos().get(0).get("id"), is(1l));
        assertThat(listaResponse.produtos().get(0).get("nome"), is("LG K62"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin", "User"})
    public void deleteProdutoFromListaDesejoTest() {

        Usuario usuario = usuarioService.getByLogin("MariaFer");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .pathParam("idProduto", 2l)
          .when().patch("/lista_desejo/remover_produto/{idProduto}")
          .then()
             .statusCode(204);

        ListaDesejoResponseDTO listaResponse = null;

        Boolean ifProdutoRemovido = false;

        listaResponse =  listaDesejoService.getListaDesejo(2l);

        for (Map<String, Object> produto : listaResponse.produtos()) {
            
            if (produto.get("id") == (Object) 2l) {

                ifProdutoRemovido = true;
            }
        }

        assertFalse(ifProdutoRemovido);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin", "User"})
    public void countListaDesejoTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .when().get("lista_desejo/count")
            .then()
                .statusCode(200);
    }
}
