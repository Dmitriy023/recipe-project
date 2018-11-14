package com.recipeproject.services;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.converters.IngridientCommandToIngridient;
import com.recipeproject.converters.IngridientToIngridientCommand;
import com.recipeproject.converters.RecipeToRecipeCommand;
import com.recipeproject.domain.Ingridient;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import com.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngridientServiceImpl implements IngridientService {

    private IngridientToIngridientCommand ingridientToIngridientCommand;
    private IngridientCommandToIngridient ingridientCommandToIngridient;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IngridientServiceImpl(IngridientToIngridientCommand ingridientToIngridientCommand,
                                 IngridientCommandToIngridient ingridientCommandToIngridient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {

        this.ingridientToIngridientCommand = ingridientToIngridientCommand;
        this.ingridientCommandToIngridient = ingridientCommandToIngridient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngridientCommand findRecipesIngridietById(Long recipeID, Long ingridientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);
        if (!recipeOptional.isPresent()) {
            log.debug("Recipe not found!!");
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngridientCommand> ingridientCommandOptional = recipe.getIngridients()
                .stream()
                .filter(ingridient -> ingridient.getId().equals(ingridientId))
                .map(ingridient -> ingridientToIngridientCommand.convert(ingridient)).findFirst();

        if (!ingridientCommandOptional.isPresent()) {
            log.debug("Not found ingridient");
        }
        return ingridientCommandOptional.get();
    }

    @Transactional
    @Override
    public IngridientCommand saveIngridientCommand(IngridientCommand command) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (!recipeOptional.isPresent()) {
            return new IngridientCommand();
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingridient> ingridientOptional = recipe.getIngridients()
                .stream()
                .filter(ingridient -> ingridient.getId().equals(command.getId()))
                .findFirst();

        if (ingridientOptional.isPresent()) {
            Ingridient ingridientFound = ingridientOptional.get();
            ingridientFound.setDescription(command.getDescription());
            ingridientFound.setAmount(command.getAmount());
            ingridientFound.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM not found")));
        } else {
            recipe.addIngridient(ingridientCommandToIngridient.convert(command));
        }

        Recipe recipeSaved = recipeRepository.save(recipe);

        Optional<Ingridient> savedIngridientOptional = recipeSaved.getIngridients()
                .stream()
                .filter(ingridient -> ingridient.getDescription().equals(command.getDescription()))
                .filter(ingridient -> ingridient.getAmount().equals(command.getAmount()))
                .filter(ingridient -> ingridient.getUom().getId().equals(command.getUom().getId()))
                .findFirst();


        return ingridientToIngridientCommand.convert(savedIngridientOptional.get());

    }


    @Override
    public void deleteIngridientById(Long recipeId, Long ingridientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.debug("Recipe with given ID not found");
            throw new RuntimeException("Recipe with given ID not found");
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingridient> ingridientDelOptional = recipe.getIngridients().stream().filter(ingridient -> ingridient.getId().equals(ingridientId)).findFirst();


        Ingridient ingridientToDelete = ingridientDelOptional.get();
        ingridientToDelete.setRecipe(null);
        recipe.getIngridients().remove(ingridientToDelete);

        recipeRepository.save(recipe);


    }
}
