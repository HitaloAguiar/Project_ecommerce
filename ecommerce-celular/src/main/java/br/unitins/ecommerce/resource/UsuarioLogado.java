package br.unitins.ecommerce.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.endereco.EnderecoResponseDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.telefone.TelefonesResponseDTO;
import br.unitins.ecommerce.dto.usuario.perfil.SenhaDTO;
import br.unitins.ecommerce.dto.usuario.perfil.dados.DadosPessoaisDTO;
import br.unitins.ecommerce.dto.usuario.perfil.dados.DadosPessoaisResponseDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.service.usuario.UsuarioService;
import br.unitins.ecommerce.service.usuario.perfil.PerfilService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioLogado {
    
    @Inject
    UsuarioService usuarioService;

    @Inject
    PerfilService perfilService;

    @Inject
    JsonWebToken tokenJwt;

    @GET
    @Path("/dados-pessoais")
    @RolesAllowed({"User"})
    public Response getDadosPessoais() {

        String login = tokenJwt.getSubject();

        DadosPessoaisResponseDTO dadosPessoaisUsuario = new DadosPessoaisResponseDTO(usuarioService.getByLogin(login));

        return Response.ok(dadosPessoaisUsuario).build();
    }

    @GET
    @Path("/telefones")
    @RolesAllowed({"User"})
    public Response getTelefones() {

        String login = tokenJwt.getSubject();

        TelefonesResponseDTO telefonesResponseDTO = new TelefonesResponseDTO(usuarioService.getByLogin(login));

        return Response.ok(telefonesResponseDTO).build();
    }

    @GET
    @Path("/endereco")
    @RolesAllowed({"User"})
    public Response getEndereco() {

        String login = tokenJwt.getSubject();

        EnderecoResponseDTO enderecoUsuario = new EnderecoResponseDTO(usuarioService.getByLogin(login).getEndereco());

        return Response.ok(enderecoUsuario).build();
    }

    @PATCH
    @Path("/dados-pessoais")
    @RolesAllowed({"User"})
    public Response updateDadosPessoais(DadosPessoaisDTO dadosPessoaisDTO) {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        perfilService.update(usuario.getId(), dadosPessoaisDTO);

        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/senha")
    @RolesAllowed({"User"})
    public Response updateSenha(SenhaDTO senhaDTO) {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        try {

            perfilService.update(usuario.getId(), senhaDTO);

            return Response.status(Status.NO_CONTENT).build();
        } catch (NotAuthorizedException e) {

            return Response.status(Status.FORBIDDEN).entity(e.getChallenges()).build();
        }
    }

    @PATCH
    @Path("/telefone-principal")
    @RolesAllowed({"User"})
    public Response updateTelefonePrincipal(TelefoneDTO telefonePrincipalDTO) {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        perfilService.updateTelefonePrincipal(usuario.getId(), telefonePrincipalDTO);

        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/telefone-opcional")
    @RolesAllowed({"User"})
    public Response updateTelefoneOpcional(TelefoneDTO telefoneOpcionalDTO) {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        perfilService.updateTelefoneOpcional(usuario.getId(), telefoneOpcionalDTO);

        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/endereco")
    @RolesAllowed({"User"})
    public Response updateEndereco(EnderecoDTO enderecoDTO) {

        String login = tokenJwt.getSubject();

        Usuario usuario = usuarioService.getByLogin(login);

        perfilService.updateEndereco(usuario.getId(), enderecoDTO);

        return Response.status(Status.NO_CONTENT).build();
    }
}
