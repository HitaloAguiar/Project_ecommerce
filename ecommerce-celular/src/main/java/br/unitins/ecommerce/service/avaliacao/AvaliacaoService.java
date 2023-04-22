package br.unitins.ecommerce.service.avaliacao;

import java.util.List;

import br.unitins.ecommerce.dto.avaliacao.AvaliacaoDTO;
import br.unitins.ecommerce.dto.avaliacao.AvaliacaoResponseDTO;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.Usuario;

public interface AvaliacaoService {
    
    // Metodos basicos

    List<AvaliacaoResponseDTO> getAll();
    
    AvaliacaoResponseDTO getById(Long id);

    AvaliacaoResponseDTO insert(AvaliacaoDTO avaliacaDto);

    AvaliacaoResponseDTO update(Long id, AvaliacaoDTO avaliacaDto);

    void delete(Long id);

    void delete(Produto produto);

    void delete(Usuario usuario);

    // Metodos extras

    Long count();

    List<AvaliacaoResponseDTO> getByYear(Integer year);

    List<AvaliacaoResponseDTO> getByIdProduto(Long idProduto);

    List<AvaliacaoResponseDTO> getByNomeUsuario(String nome);
}
