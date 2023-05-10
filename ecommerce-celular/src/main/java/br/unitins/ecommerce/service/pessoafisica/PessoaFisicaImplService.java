package br.unitins.ecommerce.service.pessoafisica;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import br.unitins.ecommerce.dto.usuario.PessoaFisicaDTO;
import br.unitins.ecommerce.model.usuario.PessoaFisica;
import br.unitins.ecommerce.model.usuario.Sexo;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.PessoaFisicaRepository;

@ApplicationScoped
public class PessoaFisicaImplService implements PessoaFisicaService {

    @Inject
    PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    Validator validator;

    @Override
    public PessoaFisica insertPessoaFisica(PessoaFisicaDTO pessoaFisicaDTO) {
        
        validar(pessoaFisicaDTO);

        PessoaFisica entity = new PessoaFisica();

        entity.setNome(pessoaFisicaDTO.nome());

        entity.setCpf(pessoaFisicaDTO.cpf());

        entity.setEmail(pessoaFisicaDTO.email());

        entity.setSexo(Sexo.valueOf(pessoaFisicaDTO.sexo()));

        pessoaFisicaRepository.persist(entity);

        return entity;
    }

    @Override
    public void updatePessoaFisica(Usuario usuario, PessoaFisicaDTO pessoaFisicaDTO) {
        
        validar(pessoaFisicaDTO);

        Long idPessoaFisica = usuario.getPessoaFisica().getId();

        usuario.setPessoaFisica(insertPessoaFisica(pessoaFisicaDTO));

        pessoaFisicaRepository.deleteById(idPessoaFisica);
    }

    private void validar(PessoaFisicaDTO pessoaFisicaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<PessoaFisicaDTO>> violations = validator.validate(pessoaFisicaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
