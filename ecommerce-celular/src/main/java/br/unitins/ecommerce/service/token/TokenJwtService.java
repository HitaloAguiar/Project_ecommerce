package br.unitins.ecommerce.service.token;

import br.unitins.ecommerce.model.usuario.Usuario;

public interface TokenJwtService {
    
    public String generateJwt(Usuario usuario);
}
