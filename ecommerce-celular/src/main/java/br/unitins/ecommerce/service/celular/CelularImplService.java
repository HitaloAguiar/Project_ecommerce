package br.unitins.ecommerce.service.celular;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.celular.CelularDTO;
import br.unitins.ecommerce.dto.celular.CelularResponseDTO;
import br.unitins.ecommerce.model.produto.Marca;
import br.unitins.ecommerce.model.produto.celular.Celular;
import br.unitins.ecommerce.model.produto.celular.Cor;
import br.unitins.ecommerce.model.produto.celular.SistemaOperacional;
import br.unitins.ecommerce.repository.CelularRepository;
import br.unitins.ecommerce.repository.MarcaRepository;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import br.unitins.ecommerce.service.usuario.UsuarioService;

@ApplicationScoped
public class CelularImplService implements CelularService {

    @Inject
    CelularRepository celularRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    AvaliacaoService avaliacaoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    Validator validator;

    @Override
    public List<CelularResponseDTO> getAll() {

        return celularRepository.findAll()
                .stream()
                .map(CelularResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CelularResponseDTO getById(Long id) throws NotFoundException {

        Celular celular = celularRepository.findById(id);

        if (celular == null)
            throw new NotFoundException("Não encontrado");

        return new CelularResponseDTO(celular);
    }

    @Override
    @Transactional
    public CelularResponseDTO insert(CelularDTO celularDto) throws ConstraintViolationException {

        validar(celularDto);

        Celular entity = new Celular();

        entity.setNome(celularDto.nome());

        entity.setDescricao(celularDto.descricao());

        entity.setMarca(marcaRepository.findById(celularDto.idMarca()));

        entity.setPreco(celularDto.preco());

        entity.setEstoque(celularDto.estoque());

        entity.setVersaoSistemaOperacional(celularDto.versaoSistemaOperacional());

        entity.setSistemaOperacional(SistemaOperacional.valueOf(celularDto.sistemaOperacional()));

        entity.setCor(Cor.valueOf(celularDto.cor()));

        celularRepository.persist(entity);

        return new CelularResponseDTO(entity);
    }

    @Override
    @Transactional
    public void update(Long id, CelularDTO celularDto) throws ConstraintViolationException {

        // validar(celularDto);

        Celular entity = celularRepository.findById(id);

        entity.setCor(Cor.valueOf(celularDto.cor()));

        entity.setNome(celularDto.nome());

        entity.setDescricao(celularDto.descricao());

        entity.setMarca(marcaRepository.findById(celularDto.idMarca()));

        entity.setPreco(celularDto.preco());

        entity.setEstoque(celularDto.estoque());

        entity.setVersaoSistemaOperacional(celularDto.versaoSistemaOperacional());

        // entity.setSistemaOperacional(SistemaOperacional.valueOf(celularDto.sistemaOperacional()));

        // celularRepository.update("SET cor = ?1 WHERE id = ?2", Cor.valueOf(celularDto.cor()), id);

        // entity.setCor(Cor.valueOf(celularDto.cor()));
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Celular celular = celularRepository.findById(id);

        avaliacaoService.delete(celular);

        usuarioService.deleteProdutoFromAllListaDesejo(celular);

        if (celularRepository.isPersistent(celular))
            celularRepository.delete(celular);

        else
            throw new NotFoundException("Nenhum Celular encontrado");
    }

    @Override
    public Long count() {

        return celularRepository.count();
    }

    @Override
    public List<CelularResponseDTO> getByNome(String nome) throws NullPointerException {

        List<Celular> list = celularRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum Celular encontrado");

        return list.stream()
                    .map(CelularResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CelularResponseDTO> getBySistemaOperacional(Integer sistemaOperacional) throws IndexOutOfBoundsException, NullPointerException {
        
        if (sistemaOperacional != 1 && sistemaOperacional != 2)
            throw new IndexOutOfBoundsException("número fora das opções");

        List<Celular> list = celularRepository.findBySistemaOperacional(SistemaOperacional.valueOf(sistemaOperacional));

        if (list == null)
            throw new NullPointerException("Nenhum Celular encontrado");

        return list.stream()
                    .map(CelularResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CelularResponseDTO> getByCor(Integer cor) throws IndexOutOfBoundsException, NullPointerException {
        
        if (cor < 1 || cor > 10)
            throw new IndexOutOfBoundsException("número fora das opções");

        List<Celular> list = celularRepository.findByCor(Cor.valueOf(cor));

        if (list == null)
            throw new NullPointerException("Nenhum Celular encontrado");

        return list.stream()
                    .map(CelularResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CelularResponseDTO> getByMarca(String nome) throws NullPointerException {

        List<Marca> marca = marcaRepository.findByNome(nome);

        if (marca == null)
            throw new NullPointerException("Nenhuma marca encontrada");
        
        List<Celular> list = celularRepository.findByMarca(marca.get(0));

        if (list == null)
            throw new NullPointerException("Nenhum celular com essa marca foi encontrado");

        return list.stream()
                    .map(CelularResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CelularResponseDTO> filterByPrecoMin(Double preco) throws NullPointerException {
        
        List<Celular> list = celularRepository.filterByPrecoMinimo(preco);

        if (list == null)
            throw new NullPointerException("Nenhum Celular encontrado");

        return list.stream()
                    .map(CelularResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CelularResponseDTO> filterByPrecoMax(Double preco) throws NullPointerException {
        
        List<Celular> list = celularRepository.filterByPrecoMaximo(preco);

        if (list == null)
        throw new NullPointerException("Nenhum Celular encontrado");

        return list.stream()
                    .map(CelularResponseDTO::new)
                    .collect(Collectors.toList());
    }

    @Override
    public List<CelularResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax) throws NullPointerException {
        
        List<Celular> list = celularRepository.filterByEntrePreco(precoMin, precoMax);

        if (list == null)
            throw new NullPointerException("Nenhum Celular encontrado");

        return list.stream()
                    .map(CelularResponseDTO::new)
                    .collect(Collectors.toList());
    }

    private void validar(CelularDTO celularDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<CelularDTO>> violations = validator.validate(celularDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
