package com.recipeproject.services;

import com.recipeproject.commands.IngridientCommand;

public interface IngridientService {
    IngridientCommand findRecipesIngridietById(Long recipeID, Long ingridientId);
}
