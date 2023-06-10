package br.unitins.ecommerce.service.pessoafisica;

import br.unitins.ecommerce.dto.usuario.PessoaFisicaDTO;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.model.usuario.pessoafisica.PessoaFisica;

public interface PessoaFisicaService {
    
    PessoaFisica insertPessoaFisica (PessoaFisicaDTO pessoaFisicaDTO);

    PessoaFisica insertPessoaFisica (String nome, String email);

    void updatePessoaFisica (Usuario usuario, PessoaFisicaDTO pessoaFisicaDTO);
}
