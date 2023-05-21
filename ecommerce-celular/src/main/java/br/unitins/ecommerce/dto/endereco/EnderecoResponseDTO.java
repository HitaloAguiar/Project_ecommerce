package br.unitins.ecommerce.dto.endereco;

import java.util.HashMap;
import java.util.Map;

import br.unitins.ecommerce.dto.municipio.MunicipioResponseDTO;
import br.unitins.ecommerce.model.endereco.Endereco;

public record EnderecoResponseDTO(
    String logradouro,
    String bairro,
    String numero,
    String complemento,
    String cep,
    Map<String, Object> municipio
) {
    
    public EnderecoResponseDTO (Endereco endereco) {

        this(endereco.getLogradouro(),
            endereco.getBairro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getCep(),
            viewMunicipio(endereco.getMunicipio().getNome(),
                            endereco.getMunicipio().getEstado().getNome(),
                            endereco.getMunicipio().getEstado().getSigla()));
    }

    private static Map<String, Object> viewMunicipio(String nome, String nomeEstado, String siglaEstado) {

        Map<String, Object> municipio = new HashMap<>();

        municipio.put("estado", MunicipioResponseDTO.viewEstado(nomeEstado, siglaEstado));
        municipio.put("nome", nome);

        return municipio;
    }
}
