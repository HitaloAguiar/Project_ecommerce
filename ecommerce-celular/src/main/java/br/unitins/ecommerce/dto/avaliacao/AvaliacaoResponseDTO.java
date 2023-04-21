package br.unitins.ecommerce.dto.avaliacao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.ecommerce.model.produto.avaliacao.Avaliacao;
import br.unitins.ecommerce.model.produto.avaliacao.Estrela;

public record AvaliacaoResponseDTO(
    Long id,
    String comentario,
    LocalDate data,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Estrela estrela,

    Map<String, Object> produto,
    Map<String, Object> usuario
) {
    
    public AvaliacaoResponseDTO (Avaliacao avaliacao) {

        this(avaliacao.getId(),
            avaliacao.getComentario() != null?
                                            avaliacao.getComentario()
                                            : null,
            avaliacao.getData(),
            avaliacao.getEstrela(),
            viewProduto(avaliacao.getProduto().getId(),
                        avaliacao.getProduto().getNome()),
            viewUsuario(avaliacao.getUsuario().getId(),
                        avaliacao.getUsuario().getNome(),
                        avaliacao.getUsuario().getEmail()));
    }

    public static Map<String, Object> viewProduto (Long id, String nome) {

        Map<String, Object> produto = new HashMap<>();

        produto.put("Id:", id);
        produto.put("Nome:", nome);

        return produto;
    }

    public static Map<String, Object> viewUsuario (Long id, String nome, String email) {

        Map<String, Object> usuario = new HashMap<>();

        usuario.put("Id:", id);
        usuario.put("Nome:", nome);
        usuario.put("Email:", email);

        return usuario;
    }
}
