package br.unitins.ecommerce.service.usuario.usuariobasico;

import java.util.Set;

import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UpgradeUsuarioDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoDTO;
import br.unitins.ecommerce.dto.usuario.usuariobasico.UsuarioBasicoResponseDTO;
import br.unitins.ecommerce.model.usuario.Perfil;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.model.usuario.pessoafisica.Sexo;
import br.unitins.ecommerce.repository.UsuarioRepository;
import br.unitins.ecommerce.service.endereco.EnderecoService;
import br.unitins.ecommerce.service.hash.HashService;
import br.unitins.ecommerce.service.pessoafisica.PessoaFisicaService;
import br.unitins.ecommerce.service.telefone.TelefoneService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@ApplicationScoped
public class UsuarioBasicoImplService implements UsuarioBasicoService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PessoaFisicaService pessoaFisicaService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    TelefoneService telefoneService;

    @Inject
    HashService hashService;

    @Inject
    Validator validator;
    
    @Override
    @Transactional
    public UsuarioBasicoResponseDTO insert(UsuarioBasicoDTO usuarioBasicoDto) throws ConstraintViolationException {

        validar(usuarioBasicoDto);

        Usuario entity = new Usuario();

        entity.setPessoaFisica(pessoaFisicaService.insertPessoaFisica(usuarioBasicoDto.nome(), usuarioBasicoDto.email()));

        entity.setLogin(usuarioBasicoDto.login());

        entity.setSenha(hashService.getHashSenha(usuarioBasicoDto.senha()));

        entity.addPerfis(Perfil.USER_BASIC);

        usuarioRepository.persist(entity);

        return new UsuarioBasicoResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO upgrade(Long id, UpgradeUsuarioDTO usuarioDto) {
        
        validar(usuarioDto);

        Usuario entity = usuarioRepository.findById(id);

        entity.getPessoaFisica().setCpf(usuarioDto.cpf());

        entity.getPessoaFisica().setSexo(Sexo.valueOf(usuarioDto.sexo()));

        entity.setEndereco(enderecoService.insertEndereco(usuarioDto.endereco()));

        entity.setTelefonePrincipal(telefoneService.insertTelefone(usuarioDto.telefonePrincipal()));

        if (usuarioDto.telefoneOpcional() != null)
            entity.setTelefoneOpcional(telefoneService.insertTelefone(usuarioDto.telefoneOpcional()));

        entity.addPerfis(Perfil.USER);

        entity.removePerfis(Perfil.USER_BASIC);

        return new UsuarioResponseDTO(entity);
    }

    private void validar(UpgradeUsuarioDTO upgradeUsuarioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UpgradeUsuarioDTO>> violations = validator.validate(upgradeUsuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    private void validar(UsuarioBasicoDTO usuarioBasicoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UsuarioBasicoDTO>> violations = validator.validate(usuarioBasicoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
