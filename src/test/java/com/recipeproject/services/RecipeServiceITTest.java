package com.recipeproject.services;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.converters.RecipeCommandToRecipe;
import com.recipeproject.converters.RecipeToRecipeCommand;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceITTest {

    public final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;


    @Transactional
    @Test
    public void saveRecipeCommand() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(recipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        Assert.assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        Assert.assertEquals(testRecipeCommand.getCategories().size(), savedRecipeCommand.getCategories().size());
        Assert.assertEquals(testRecipeCommand.getIngridients().size(), savedRecipeCommand.getIngridients().size());

    }
}