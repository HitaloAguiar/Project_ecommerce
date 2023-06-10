package br.unitins;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UpgradeUsuarioDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.token.TokenJwtService;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class UsuarioBasicoResourceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;
    
    @Test
    public void insertBasicoTest() {

        UsuarioBasicoDTO usuarioBasicoDTO = new UsuarioBasicoDTO("Luis_gameplays", "policiais_de_Bikini", "Luís Felipe", "luis.games@hotmail.com");

        given()
            .contentType(ContentType.JSON)
                .body(usuarioBasicoDTO)
            .when()
                .post("/usuario-basico")
            .then()
                .statusCode(201);
    }

    @Test
    public void getDadosPessoaisTest() {

        Usuario usuario = usuarioService.getByLogin("Joao_dos_Isekai");

        String token = tokenService.generateJwt(usuario);

        given()
            .header("Authorization", "Bearer " + token)
            .when()
                .get("/usuario-basico")
            .then()
                .statusCode(200);
    }

    @Test
    public void upgradeUsuarioTest() {

        Usuario usuario = usuarioService.getByLogin("Joao_dos_Isekai");

        String token = tokenService.generateJwt(usuario);

        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua da Pax", "bairro da Saudade", "6739", "apto 1", "77791-928", 2l);

        TelefoneDTO telefoneDTO = new TelefoneDTO("078", "98273-1728");

        UpgradeUsuarioDTO upgradeUsuarioDTO = new UpgradeUsuarioDTO("12373802784", 1, enderecoDTO, telefoneDTO, null);

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
                .body(upgradeUsuarioDTO)
            .when()
                .patch("/usuario-basico")
            .then()
                .statusCode(201);
    }
}
