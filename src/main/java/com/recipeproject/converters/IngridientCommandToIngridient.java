package com.recipeproject.converters;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Ingridient;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngridientCommandToIngridient  implements Converter<IngridientCommand,Ingridient>{

    private UnitOfMeasureCommandToUnitOfMeasure unitConverter;
    private RecipeRepository recipeRepository;


    public IngridientCommandToIngridient(UnitOfMeasureCommandToUnitOfMeasure unitConverter, RecipeRepository recipeRepository) {
        this.unitConverter = unitConverter;
        this.recipeRepository = recipeRepository;
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

        Optional<Recipe> recipe = recipeRepository.findById(source.getRecipeId());
        if(recipe.isPresent()){
            ingridient.setRecipe(recipe.get());
        }


        return ingridient;
    }
}
