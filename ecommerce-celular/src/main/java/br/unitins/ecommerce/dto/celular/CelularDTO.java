package br.unitins.ecommerce.dto.celular;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CelularDTO(
    @NotBlank(message = "O campo nome não pode estar nulo")
    String nome,

    String descricao,

    @NotNull
    @Min(1)
    Long idMarca,

    @NotNull
    @Min(0)
    Double preco,

    @NotNull
    @Min(0)
    Integer estoque,

    @NotNull
    Float versaoSistemaOperacional,

    @NotNull
    @Min(1)
    @Max(2)
    Integer sistemaOperacional,

    @NotNull
    @Min(1)
    Integer cor
) {
    
    
}
