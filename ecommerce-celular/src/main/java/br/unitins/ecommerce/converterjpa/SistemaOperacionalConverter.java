package br.unitins.ecommerce.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.ecommerce.model.produto.celular.SistemaOperacional;

@Converter(autoApply = true)
public class SistemaOperacionalConverter implements AttributeConverter<SistemaOperacional, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SistemaOperacional sistemaOperacional) {
       
        return sistemaOperacional == null ? null : sistemaOperacional.getId();        
    }

    @Override
    public SistemaOperacional convertToEntityAttribute(Integer id) {
        
        return SistemaOperacional.valueOf(id);
    }
}
