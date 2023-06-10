package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.PessoaFisicaDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import br.unitins.ecommerce.service.usuario.lista_desejo.ListaDesejoService;

@QuarkusTest
public class UsuarioResourceTest {

    @Inject
    UsuarioService usuarioService;

    @Inject
    ListaDesejoService listaDesejoService;
    
    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getAllUsuarioTest() {

        given()
            .when().get("/usuarios")
            .then()
            .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"User"})
    public void getAllUsuarioForbiddenTest() {

        given()
            .when()
                .get("/usuarios")
            .then()
                .statusCode(403);
    }

    @Test
    public void getAllUsuarioUnauthorizedTest() {

        given()
            .when()
                .get("/usuarios")
            .then()
                .statusCode(401);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getAllUsuarioBasicoTest() {

        given()
            .when()
                .get("/usuarios/usuarios-basicos")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void insertTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
            "Danilo Da Silva",
            "89912376394",
            "DaniloDaSi@unitins.br",
            1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
            "Avenida Tocantins",
            "Setor Bueno",
            "8780",
            "apto 3",
            "77780-920",
            4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        given()
                .contentType(ContentType.JSON)
                .body(usuarioDto)
                .when().post("/usuarios")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                "nome", is("Danilo Da Silva"),
                "email", is("DaniloDaSi@unitins.br"),
                "cpf", is("89912376394"),
                    "endereco.logradouro", is("Avenida Tocantins"),
                    "endereco.bairro", is("Setor Bueno"),
                    "endereco.numero", is("8780"),
                    "endereco.complemento", is("apto 3"),
                    "endereco.cep", is("77780-920"),
                        "endereco.municipio.nome", is("Belém"),
                            "endereco.municipio.estado.nome", is("Pará"),
                            "endereco.municipio.estado.sigla", is("PA"),
                    "telefonePrincipal.codigoDeArea", is("067"),
                    "telefonePrincipal.numero", is("98467-8901"),
                    "telefoneOpcional.codigoDeArea", is("067"),
                    "telefoneOpcional.numero", is("4002-8922"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void updateTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
            "Danilo Da Silva",
            "89012376094",
            "DanilDaSi@unitins.br",
            1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
            "Avenida Tocantins",
            "Setor Bueno",
            "8780",
            "apto 3",
            "77780-920",
            4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long id = usuarioService.insert(usuarioDto).id();

        PessoaFisicaDTO updatePessoaFisicaDTO = new PessoaFisicaDTO(
            "Erick Santos",
            "64702973802",
            "ErickSantos@unitins.br",
            1);


        EnderecoDTO updateEnderecoDTO = new EnderecoDTO(
            "Avenida Juscelino Kubistchek",
            "Setor Alvorada",
            "8901",
            "apto 1",
            "77572-020",
            4l);

        TelefoneDTO updateTelefonePrincipalDTO = new TelefoneDTO("067", "96821-0293");

        TelefoneDTO updateTelefoneOpcionalDTO = new TelefoneDTO("011", "97202-9313");

        UsuarioDTO updateUsuarioDto = new UsuarioDTO(
                "Erick",
                "conteudoA2",
                updatePessoaFisicaDTO,
                updateEnderecoDTO,
                updateTelefonePrincipalDTO,
                updateTelefoneOpcionalDTO);

        given()
          .contentType(ContentType.JSON)
          .body(updateUsuarioDto)
          .when().put("/usuarios/" + id)
          .then()
             .statusCode(204);

        UsuarioResponseDTO usuarioResponse = usuarioService.getById(id);

        Map<String, Object> municipio = (Map<String, Object>) usuarioResponse.endereco().get("municipio");

        Map<String, Object> estado = (Map<String, Object>) municipio.get("estado");

        assertThat(usuarioResponse.nome(), is("Erick Santos"));
        assertThat(usuarioResponse.email(), is("ErickSantos@unitins.br"));
        assertThat(usuarioResponse.cpf(), is("64702973802"));
        assertThat(usuarioResponse.endereco().get("logradouro"), is("Avenida Juscelino Kubistchek"));
        assertThat(usuarioResponse.endereco().get("bairro"), is("Setor Alvorada"));
        assertThat(usuarioResponse.endereco().get("numero"), is("8901"));
        assertThat(usuarioResponse.endereco().get("complemento"), is("apto 1"));
        assertThat(usuarioResponse.endereco().get("cep"), is("77572-020"));
        assertThat(municipio.get("nome"), is("Belém"));
        assertThat(estado.get("nome"), is("Pará"));
        assertThat(estado.get("sigla"), is("PA"));
        assertThat(usuarioResponse.telefonePrincipal().get("codigoDeArea"), is("067"));
        assertThat(usuarioResponse.telefonePrincipal().get("numero"), is("96821-0293"));
        assertThat(usuarioResponse.telefoneOpcional().get("codigoDeArea"), is("011"));
        assertThat(usuarioResponse.telefoneOpcional().get("numero"), is("97202-9313"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void deleteTest() {

        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
            "Danilo Da Silva",
            "89042376391",
            "Danilojasdfns@unitins.br",
            1);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
            "Avenida Tocantins",
            "Setor Bueno",
            "8780",
            "apto 3",
            "77780-920",
            4l);

        TelefoneDTO telefonePrincipalDTO = new TelefoneDTO("067", "98467-8901");

        TelefoneDTO telefoneOpcionalDTO = new TelefoneDTO("067", "4002-8922");

        UsuarioDTO usuarioDto = new UsuarioDTO(
                "Danilo",
                "senha1234",
                pessoaFisicaDTO,
                enderecoDTO,
                telefonePrincipalDTO,
                telefoneOpcionalDTO);

        Long id = usuarioService.insert(usuarioDto).id();

        given()
          .when().delete("/usuarios/" + id)
          .then()
             .statusCode(204);

        UsuarioResponseDTO usuarioResponse = null;

        try {
            
            usuarioResponse =  usuarioService.getById(id);
        } catch (Exception e) {

        }
        finally {
            assertNull(usuarioResponse);   
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void countTest() {

        given()
            .when().get("/usuarios/count")
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getByIdTest() {

        given()
            .when().get("/usuarios/" + 1)
            .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"Admin"})
    public void getByNomeTest() {

        given()
            .when().get("/usuarios/searchByNome/" + "maria")
            .then()
                .statusCode(200);
    }
}
