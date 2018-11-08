package com.recipeproject.converters;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Ingridient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngridientCommandToIngridient  implements Converter<IngridientCommand,Ingridient>{

    private UnitOfMeasureCommandToUnitOfMeasure unitConverter;
    //private RecipeCommandToRecipe recipeConverter;


    public IngridientCommandToIngridient(UnitOfMeasureCommandToUnitOfMeasure unitConverter) {
        this.unitConverter = unitConverter;
       // this.recipeConverter = recipeConverter;

    }

    @Synchronized
    @Nullable
    @Override
    public Ingridient convert(IngridientCommand source) {

        if(source == null){
            return null;
        }

        Ingridient ingridient = new Ingridient();
        ingridient.setAmount(source.getAmount());
        ingridient.setDescription(source.getDescription());
        ingridient.setId(source.getId());
        ingridient.setUom(unitConverter.convert(source.getUom()));
      //  ingridient.setRecipe(recipeConverter.convert(source.getRecipe()));

        return ingridient;
    }
}
