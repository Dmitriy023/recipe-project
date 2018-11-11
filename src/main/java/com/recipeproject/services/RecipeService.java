package com.recipeproject.services;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.converters.RecipeToRecipeCommand;
import com.recipeproject.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecepies();
    Recipe findById(Long l);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findByIdRecipeCommand(Long l);
    void deleteRecipeById(Long l);
}
