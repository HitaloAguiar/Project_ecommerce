package br.unitins.ecommerce.service.usuario.perfil;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.perfil.SenhaDTO;
import br.unitins.ecommerce.dto.usuario.perfil.dados.DadosPessoaisDTO;

public interface PerfilService {
    
    void update(Long id, DadosPessoaisDTO dadosPessoaisDTO);

    void update (Long id, SenhaDTO senhaDTO);

    void update(Long id, String nomeImagem);

    void updateTelefonePrincipal(Long id, TelefoneDTO telefoneDto);

    void updateTelefoneOpcional(Long id, TelefoneDTO telefoneDto);

    void updateEndereco(Long id, EnderecoDTO enderecoDTO);
}
