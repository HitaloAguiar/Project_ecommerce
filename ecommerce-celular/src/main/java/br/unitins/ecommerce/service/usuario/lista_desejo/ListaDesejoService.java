package br.unitins.ecommerce.service.usuario.lista_desejo;

import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.produto.Produto;

public interface ListaDesejoService {
    
    ListaDesejoResponseDTO getListaDesejo(Long id);

    void insertProdutoIntoListaDesejo(ListaDesejoDTO listaDto);

    void deleteProdutoFromListaDesejo(Long id, Long idProduto);

    void deleteProdutoFromAllListaDesejo(Produto produto);

    Integer countListaDesejo(Long id);
}
