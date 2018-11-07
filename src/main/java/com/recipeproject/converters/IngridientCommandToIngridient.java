package com.recipeproject.converters;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.domain.Ingridient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngridientCommandToIngridient  implements Converter<IngridientCommand,Ingridient>{
    UnitOfMeasureCommandToUnitOfMeasure uomC;

    public IngridientCommandToIngridient(UnitOfMeasureCommandToUnitOfMeasure uomC) {
        this.uomC = uomC;
    }

    @Override
    public Ingridient convert(IngridientCommand source) {
        if(source == null){
            return null;
        }
        Ingridient ingridient = new Ingridient();
        ingridient.setAmount(source.getAmount());
        ingridient.setDescription(source.getDescription());
        ingridient.setId(source.getId());
        ingridient.setUom(uomC.convert(source.getUom()));
       // ingridient.setRecipe();
        return ingridient;
    }
}
