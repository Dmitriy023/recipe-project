package com.recipeproject.services;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.converters.RecipeCommandToRecipe;
import com.recipeproject.converters.RecipeToRecipeCommand;
import com.recipeproject.domain.Recipe;
import com.recipeproject.exceptions.NotFoundException;
import com.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecepies() {
        Set<Recipe> recipesSet = new HashSet<>();

        recipeRepository.findAllByOrderByIdAsc().iterator().forEachRemaining(recipesSet::add);

        return recipesSet;
    }


    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe Not Found");
        }
        return recipeOptional.get();
    }

    @Transactional
    @Override
    public RecipeCommand findByIdRecipeCommand(Long l) {
        return recipeToRecipeCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {

        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe ID: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);

    }

    @Override
    public void deleteRecipeById(Long l) {
        recipeRepository.deleteById(l);
    }
}
