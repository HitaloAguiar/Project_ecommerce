package br.unitins.ecommerce.service.compra;

import java.util.List;

import br.unitins.ecommerce.dto.compra.CompraResponseDTO;
import br.unitins.ecommerce.dto.compra.ItemCompraDTO;

public interface CompraService {

    List<CompraResponseDTO> getAll (Long idUsuario);

    CompraResponseDTO getCompraEmAndamento (Long idUsuario);
    
    void insertItemIntoCompra (Long idCompra, ItemCompraDTO itemCompraDTO);

    void removeItemCompra (Long idUsuario, Long idItemCompra);

    void finishCompra (Long idUsuario);
}
