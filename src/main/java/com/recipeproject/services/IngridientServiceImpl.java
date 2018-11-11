package com.recipeproject.services;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.converters.IngridientToIngridientCommand;
import com.recipeproject.converters.RecipeToRecipeCommand;
import com.recipeproject.domain.Ingridient;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngridientServiceImpl implements IngridientService {
    private IngridientToIngridientCommand ingridientConverter;
    private RecipeRepository recipeRepository;

    public IngridientServiceImpl(IngridientToIngridientCommand ingridientConverter, RecipeRepository recipeRepository) {
        this.ingridientConverter = ingridientConverter;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngridientCommand findRecipesIngridietById(Long recipeID, Long ingridientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);
        if(!recipeOptional.isPresent()){
            log.debug("Recipe not found!!");
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngridientCommand> ingridientCommandOptional = recipe.getIngridients()
                .stream()
                .filter(ingridient -> ingridient.getId().equals(ingridientId))
                .map(ingridient -> ingridientConverter.convert(ingridient)).findFirst();

        if(!ingridientCommandOptional.isPresent()){
            log.debug("Not found ingridient");
        }
        return ingridientCommandOptional.get();
    }
}
