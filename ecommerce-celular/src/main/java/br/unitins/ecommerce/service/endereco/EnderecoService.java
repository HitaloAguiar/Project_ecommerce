package br.unitins.ecommerce.service.endereco;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.endereco.Municipio;
import br.unitins.ecommerce.model.usuario.Usuario;

public interface EnderecoService {
    
    Endereco insertEndereco(EnderecoDTO enderecoDto);

    void updateEndereco(Usuario usuario, EnderecoDTO enderecoDTO);

    void delete(Municipio municipio);
}
