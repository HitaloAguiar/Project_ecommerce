package br.unitins.ecommerce.service.celular;

import java.util.List;

import br.unitins.ecommerce.dto.celular.CelularDTO;
import br.unitins.ecommerce.dto.celular.CelularResponseDTO;

public interface CelularService {
    
    // Metodos basicos

    List<CelularResponseDTO> getAll();
    
    CelularResponseDTO getById(Long id);

    CelularResponseDTO insert(CelularDTO celularDto);

    CelularResponseDTO update(Long id, CelularDTO celularDto);

    void delete(Long id);

    // Metodos extras

    Long count();

    List<CelularResponseDTO> getByNome(String nome);

    List<CelularResponseDTO> getBySistemaOperacional(Integer id);

    List<CelularResponseDTO> getByCor(Integer id);

    List<CelularResponseDTO> getByMarca(String nome);

        // metodos de filtragem

    List<CelularResponseDTO> filterByPrecoMin(Double preco);

    List<CelularResponseDTO> filterByPrecoMax(Double preco);

    List<CelularResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax);

}
