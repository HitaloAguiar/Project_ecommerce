package br.unitins.ecommerce.service.compra;

import java.util.List;

import br.unitins.ecommerce.dto.compra.CartaoCreditoDTO;
import br.unitins.ecommerce.dto.compra.CompraResponseDTO;
import br.unitins.ecommerce.dto.compra.ItemCompraDTO;

public interface CompraService {

    List<CompraResponseDTO> getAll (Long idUsuario);

    CompraResponseDTO getCompraEmAndamento (Long idUsuario);
    
    void insertItemIntoCompra (Long idUsuario, ItemCompraDTO itemCompraDTO);

    void removeItemCompra (Long idUsuario, Long idItemCompra);

    void efetuarPagamentoBoleto(Long idUsuario);

    void efetuarPagamentoPix(Long idUsuario);

    void efetuarPagamentoCartaoCredito(Long idUsuario, CartaoCreditoDTO cartaoCreditoDTO);

    void cancelarCompra(Long idUsuario);

    void finishCompra (Long idCompra);
}
