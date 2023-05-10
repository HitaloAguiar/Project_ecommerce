package br.unitins.ecommerce.dto.usuario.listadesejo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.ecommerce.dto.avaliacao.AvaliacaoResponseDTO;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.Usuario;

public record ListaDesejoResponseDTO(
    Map<String, Object> usuario,
    List<Map<String, Object>> produtos
) {
    
    public ListaDesejoResponseDTO (Usuario usuario) {

        this(AvaliacaoResponseDTO.viewUsuario(usuario.getId(),
                                            usuario.getPessoaFisica().getNome(),
                                            usuario.getPessoaFisica().getEmail()),
            viewProdutos(usuario.getProdutos()));
    }

    private static List<Map<String, Object>> viewProdutos (List<Produto> lista) {

        List<Map<String, Object>> listaProdutos = new ArrayList<>();

        for (Produto produtos : lista) {
            
            Map<String, Object> produto = new HashMap<>();

            produto = AvaliacaoResponseDTO.viewProduto(produtos.getId(), produtos.getNome());

            listaProdutos.add(produto);
        }

        return listaProdutos;
    }
}
