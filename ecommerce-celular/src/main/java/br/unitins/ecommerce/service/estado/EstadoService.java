package br.unitins.ecommerce.service.estado;

import java.util.List;

import br.unitins.ecommerce.dto.estado.EstadoDTO;
import br.unitins.ecommerce.dto.estado.EstadoResponseDTO;

public interface EstadoService {
    
    List<EstadoResponseDTO> getAll();
    
    EstadoResponseDTO getById(Long id);

    EstadoResponseDTO insert(EstadoDTO estadoDto);

    EstadoResponseDTO update(Long id, EstadoDTO estadoDto);

    void delete(Long id);

    // Metodos extras

    List<EstadoResponseDTO> getByNome(String nome);

    List<EstadoResponseDTO> getBySigla(String sigla);

    Long count();
}
