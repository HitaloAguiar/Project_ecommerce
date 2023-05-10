package br.unitins.ecommerce.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import br.unitins.ecommerce.model.produto.avaliacao.Estrela;

@Converter(autoApply = true)
public class EstrelaConverter implements AttributeConverter<Estrela, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Estrela estrela) {
        return estrela == null ? null : estrela.getId();
    }

    @Override
    public Estrela convertToEntityAttribute(Integer id) {
        return Estrela.valueOf(id);
    }
}