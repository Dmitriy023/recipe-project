package com.recipeproject.converters;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.domain.Ingridient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngridientToIngridientCommand implements Converter<Ingridient, IngridientCommand> {

    UnitOfMeasureToUnitOfMeasureCommand uom;

    public IngridientToIngridientCommand(UnitOfMeasureToUnitOfMeasureCommand uom) {
        this.uom = uom;
    }

    @Override
    public IngridientCommand convert(Ingridient source) {
        if (source == null) {
            return null;
        }
        IngridientCommand ingridientCmd = new IngridientCommand();
        ingridientCmd.setAmount(source.getAmount());
        ingridientCmd.setDescription(source.getDescription());
        ingridientCmd.setId(source.getId());
        ingridientCmd.setUom(uom.convert(source.getUom()));
        // ingridient.setRecipe();
        return ingridientCmd;
    }
}
