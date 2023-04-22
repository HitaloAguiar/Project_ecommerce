package br.unitins.ecommerce.service.telefone;

import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;

public interface TelefoneService {
    
    Telefone insertTelefone (TelefoneDTO telefoneDTO);

    void updateTelefonePrincipal(Usuario usuario, TelefoneDTO telefoneDto);

    void updateTelefoneOpcional(Usuario usuario, TelefoneDTO telefoneDto);
}
