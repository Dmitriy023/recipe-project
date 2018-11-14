package com.recipeproject.converters;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.domain.Ingridient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngridientToIngridientCommand implements Converter<Ingridient, IngridientCommand> {

    private UnitOfMeasureToUnitOfMeasureCommand unitConverter;
   // private RecipeToRecipeCommand recipeConverter;

    public IngridientToIngridientCommand(UnitOfMeasureToUnitOfMeasureCommand unitConverter){//, RecipeToRecipeCommand recipeConverter) {
        this.unitConverter = unitConverter;
       // this.recipeConverter = recipeConverter;

    }

    @Synchronized
    @Nullable
    @Override
    public IngridientCommand convert(Ingridient source) {

        if (source == null) {
            return null;
        }

        IngridientCommand ingridientCmd = new IngridientCommand();
        ingridientCmd.setAmount(source.getAmount());
        ingridientCmd.setDescription(source.getDescription());
        ingridientCmd.setId(source.getId());
        ingridientCmd.setUom(unitConverter.convert(source.getUom()));
        ingridientCmd.setRecipeId(source.getRecipe().getId());

        return ingridientCmd;

    }
}
