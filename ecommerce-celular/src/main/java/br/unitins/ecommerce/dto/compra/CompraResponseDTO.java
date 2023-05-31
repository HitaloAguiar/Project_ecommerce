package br.unitins.ecommerce.dto.compra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.model.compra.Compra;
import br.unitins.ecommerce.model.compra.ItemCompra;

public record CompraResponseDTO(
    LocalDate dataCompra,
    String totalCompra,
    List<Map<String, Object>> itensCompra,
    Map<String, Object> endereco,
    String statusCompra
) {
    
    public CompraResponseDTO (Compra compra) {

        this(compra.getDataCompra(),
            "R$ " + String.format("%.2f", compra.getTotalCompra()),
            compra.getItemCompra() != null?
                viewItensCompra(compra.getItemCompra()) :
                null,
            compra.getEndereco() != null?
                UsuarioResponseDTO.viewEndereco(compra.getEndereco().getLogradouro(),
                                        compra.getEndereco().getBairro(),
                                        compra.getEndereco().getNumero(),
                                        compra.getEndereco().getComplemento(),
                                        compra.getEndereco().getCep(),
                                        compra.getEndereco().getMunicipio()) :
                null,
            compra.getIfConcluida() == true ? "Compra concluída" : "Compra em andamento");
    }

    private static List<Map<String, Object>> viewItensCompra (List<ItemCompra> lista) {

        List<Map<String, Object>> listaitensCompra = new ArrayList<>();

        for (ItemCompra itensCompra : lista) {
            
            Map<String, Object> itemCompra = new HashMap<>();

            itemCompra = viewItemCompra(itensCompra.getProduto().getNome(), itensCompra.getPrecoUnitario(), itensCompra.getQuantidade());

            listaitensCompra.add(itemCompra);
        }

        return listaitensCompra;
    }

    private static Map<String, Object> viewItemCompra (String nome, Double preco, Integer quantidade) {

        Map<String, Object> itemCompra = new HashMap<>();

        itemCompra.put("nome", nome);
        itemCompra.put("preço", preco);
        itemCompra.put("quantidade", quantidade);

        return itemCompra;
    }
}
