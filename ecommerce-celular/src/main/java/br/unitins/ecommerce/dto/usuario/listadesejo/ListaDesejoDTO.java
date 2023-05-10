package br.unitins.ecommerce.dto.usuario.listadesejo;

import jakarta.validation.constraints.NotNull;

public record ListaDesejoDTO(
    @NotNull
    Long idUsuario,

    Long idProduto
) {
    
}
