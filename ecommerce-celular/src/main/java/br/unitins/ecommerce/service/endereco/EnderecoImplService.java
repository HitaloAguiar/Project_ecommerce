package br.unitins.ecommerce.service.endereco;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.endereco.Municipio;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.EnderecoRepository;
import br.unitins.ecommerce.repository.MunicipioRepository;

@ApplicationScoped
public class EnderecoImplService implements EnderecoService {
    
    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    Validator validator;

    @Override
    public Endereco insertEndereco(EnderecoDTO enderecoDto) throws ConstraintViolationException {
        
        validar(enderecoDto);

        Endereco endereco = new Endereco();

        endereco.setLogradouro(enderecoDto.logradouro());

        endereco.setBairro(enderecoDto.bairro());

        endereco.setNumero(enderecoDto.numero());

        endereco.setComplemento(enderecoDto.complemento());

        endereco.setCep(enderecoDto.cep());

        endereco.setMunicipio(municipioRepository.findById(enderecoDto.idMunicipio()));

        enderecoRepository.persist(endereco);

        return endereco;
    }

    @Override
    public void updateEndereco(Usuario usuario, EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        
        validar(enderecoDTO);

        Long idEndereco = usuario.getEndereco().getId();

        usuario.setEndereco(insertEndereco(enderecoDTO));

        deleteEndereco(idEndereco);
    }

    @Override
    public void delete(Municipio municipio) {

        List<Endereco> listaEndereco = enderecoRepository.findByMunicipio(municipio);

        for (Endereco endereco : listaEndereco) {
            
            enderecoRepository.delete(endereco);
        }
    }

    private void deleteEndereco(Long id) throws NotFoundException, IllegalArgumentException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Endereco endereco = enderecoRepository.findById(id);

        if (enderecoRepository.isPersistent(endereco))
            enderecoRepository.delete(endereco);

        else
            throw new NotFoundException("Nenhum endereço encontrado");
    }

    private void validar(EnderecoDTO enderecoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
