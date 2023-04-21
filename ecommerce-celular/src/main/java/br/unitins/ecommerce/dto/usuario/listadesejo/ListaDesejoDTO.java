package br.unitins.ecommerce.dto.usuario.listadesejo;

import javax.validation.constraints.NotNull;

public record ListaDesejoDTO(
    @NotNull
    Long idUsuario,

    Long idProduto
) {
    
}
