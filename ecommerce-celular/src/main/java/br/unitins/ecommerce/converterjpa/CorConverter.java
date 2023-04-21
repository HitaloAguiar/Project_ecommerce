package br.unitins.ecommerce.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.ecommerce.model.produto.celular.Cor;

@Converter(autoApply = true)
public class CorConverter implements AttributeConverter<Cor, Integer> {
    
    @Override
    public Integer convertToDatabaseColumn(Cor cor) {
       
        return cor == null ? null : cor.getId();        
    }

    @Override
    public Cor convertToEntityAttribute(Integer id) {
        
        return Cor.valueOf(id);
    }
}