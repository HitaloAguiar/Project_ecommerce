package br.unitins.ecommerce.service.telefone;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.TelefoneRepository;

@ApplicationScoped
public class TelefoneImplService implements TelefoneService {
    
    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public Telefone insertTelefone(TelefoneDTO telefoneDTO) throws ConstraintViolationException {
        
        validar(telefoneDTO);

        Telefone telefone = new Telefone();

        telefone.setCodigoArea(telefoneDTO.codigoArea());
        telefone.setNumero(telefoneDTO.numero());

        telefoneRepository.persist(telefone);

        return telefone;
    }

    @Override
    @Transactional
    public void updateTelefonePrincipal(Usuario usuario, TelefoneDTO telefoneDto) throws ConstraintViolationException {
        
        validar(telefoneDto);

        Long idTelefone = usuario.getTelefonePrincipal().getId();

        usuario.setTelefonePrincipal(insertTelefone(telefoneDto));

        deleteTelefone(idTelefone);
    }

    @Override
    @Transactional
    public void updateTelefoneOpcional(Usuario usuario, TelefoneDTO telefoneDto) throws ConstraintViolationException {
        
        validar(telefoneDto);

        Long idTelefone;

        if (telefoneDto != null && usuario.getTelefoneOpcional() != null) {

            idTelefone = usuario.getTelefoneOpcional().getId();

            usuario.setTelefoneOpcional(insertTelefone(telefoneDto));

            deleteTelefone(idTelefone);
        }

        else if (telefoneDto != null && usuario.getTelefoneOpcional() == null) {

            usuario.setTelefoneOpcional(insertTelefone(telefoneDto));
        }

        else if (usuario.getTelefoneOpcional() != null) {

            idTelefone = usuario.getTelefoneOpcional().getId();

            usuario.setTelefoneOpcional(null);

            deleteTelefone(idTelefone);
        }
    }

    private void deleteTelefone(Long id) throws NotFoundException, IllegalArgumentException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Telefone telefone = telefoneRepository.findById(id);

        if (telefoneRepository.isPersistent(telefone))
            telefoneRepository.delete(telefone);

        else
            throw new NotFoundException("Nenhum Telefone encontrado");
    }

    private void validar(TelefoneDTO telefoneDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(telefoneDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
