package br.unitins;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.perfil.SenhaDTO;
import br.unitins.ecommerce.dto.usuario.perfil.dados.DadosPessoaisDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.token.TokenJwtService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class UsuarioLogadoResourceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;
    
    @Test
    public void getDadosPessoaisTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .get("/perfil/dados-pessoais")
            .then()
                .statusCode(200);
    }

    @Test
    public void getTelefonesTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .get("/perfil/telefones")
            .then()
                .statusCode(200);
    }

    @Test
    public void getEnderecoTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .get("/perfil/endereco")
            .then()
                .statusCode(200);
    }

    @Test
    public void updateDadosPessoaisTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO("joao@gmail.com", 2);

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(dadosPessoaisDTO)
            .when()
                .patch("/perfil/dados-pessoais")
            .then()
                .statusCode(204);
    }

    @Test
    public void updateSenhaTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        SenhaDTO senhaDTO = new SenhaDTO("joao1234", "password");

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(senhaDTO)
            .when()
                .patch("/perfil/senha")
            .then()
                .statusCode(204);
    }

    @Test
    public void updateTelefonePrincipalTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        TelefoneDTO telefoneDTO = new TelefoneDTO("087", "98273-0182");

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(telefoneDTO)
            .when()
                .patch("/perfil/telefone-principal")
            .then()
                .statusCode(204);
    }

    @Test
    public void updateTelefoneOpcionalTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        TelefoneDTO telefoneDTO = new TelefoneDTO("091", "99923-0281");

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(telefoneDTO)
            .when()
                .patch("/perfil/telefone-opcional")
            .then()
                .statusCode(204);
    }

    @Test
    public void updateEnderecoTest() {

        Usuario usuario = usuarioService.getByLogin("JoaoA");

        String token = tokenService.generateJwt(usuario);

        EnderecoDTO enderecoDTO = new EnderecoDTO("Avenida Goiás", "setor Jardim Brasília", "8712", "apto 3", "77798-347", 1l);

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(enderecoDTO)
            .when()
                .patch("/perfil/endereco")
            .then()
                .statusCode(204);
    }
}
