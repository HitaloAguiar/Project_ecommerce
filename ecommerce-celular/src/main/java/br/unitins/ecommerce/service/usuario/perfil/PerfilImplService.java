package br.unitins.ecommerce.service.usuario.perfil;

import java.util.Set;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.perfil.SenhaDTO;
import br.unitins.ecommerce.dto.usuario.perfil.dados.DadosPessoaisDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.model.usuario.pessoafisica.Sexo;
import br.unitins.ecommerce.repository.UsuarioRepository;
import br.unitins.ecommerce.service.endereco.EnderecoService;
import br.unitins.ecommerce.service.hash.HashService;
import br.unitins.ecommerce.service.telefone.TelefoneService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class PerfilImplService implements PerfilService {

    @Inject
    Validator validator;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TelefoneService telefoneService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    HashService hashService;

    @Override
    @Transactional
    public void update(Long id, DadosPessoaisDTO dadosPessoaisDTO) {
       
        validar(dadosPessoaisDTO);

        Usuario entity = usuarioRepository.findById(id);

        entity.getPessoaFisica().setEmail(dadosPessoaisDTO.email());

        entity.getPessoaFisica().setSexo(Sexo.valueOf(dadosPessoaisDTO.sexo()));
    }

    @Override
    @Transactional
    public void update(Long id, SenhaDTO senhaDTO) {
        
        validar(senhaDTO);

        Usuario entity = usuarioRepository.findById(id);

        if (entity.getSenha().equals(hashService.getHashSenha(senhaDTO.senhaAntiga())))
            entity.setSenha(hashService.getHashSenha(senhaDTO.senhaNova()));

        else
            throw new NotAuthorizedException("A senha inserida não corresponde à senha atual, acesso negado");
    }

    @Override
    @Transactional
    public void updateTelefonePrincipal(Long id, TelefoneDTO telefoneDto) {
        
        Usuario entity = usuarioRepository.findById(id);

        telefoneService.updateTelefonePrincipal(entity, telefoneDto);
    }

    @Override
    @Transactional
    public void updateTelefoneOpcional(Long id, TelefoneDTO telefoneDto) {
        
        Usuario entity = usuarioRepository.findById(id);

        telefoneService.updateTelefoneOpcional(entity, telefoneDto);
    }

    @Override
    @Transactional
    public void updateEndereco(Long id, EnderecoDTO enderecoDTO) {

        Usuario entity = usuarioRepository.findById(id);

        enderecoService.updateEndereco(entity, enderecoDTO);
    }
    
    private void validar(DadosPessoaisDTO dadosPessoaisDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<DadosPessoaisDTO>> violations = validator.validate(dadosPessoaisDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    private void validar(SenhaDTO senhaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<SenhaDTO>> violations = validator.validate(senhaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
