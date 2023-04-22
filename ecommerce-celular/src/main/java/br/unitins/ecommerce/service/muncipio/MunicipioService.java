package br.unitins.ecommerce.service.muncipio;

import java.util.List;

import br.unitins.ecommerce.dto.municipio.MunicipioDTO;
import br.unitins.ecommerce.dto.municipio.MunicipioResponseDTO;
import br.unitins.ecommerce.model.endereco.Estado;

public interface MunicipioService {
    
    // Metodos basicos

    List<MunicipioResponseDTO> getAll();
    
    MunicipioResponseDTO getById(Long id);

    MunicipioResponseDTO insert(MunicipioDTO municipioDto);

    MunicipioResponseDTO update(Long id, MunicipioDTO municipioDto);

    void delete(Long id);

    void delete(Estado estado);

    // Metodos extras

    Long count();

    List<MunicipioResponseDTO> getByNome(String nome);

    List<MunicipioResponseDTO> getByNomeEstado(String nomeEstado);

    List<MunicipioResponseDTO> getBySiglaEstado(String siglaEstado);
}
