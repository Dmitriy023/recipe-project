package com.recipeproject.services;

import com.recipeproject.converters.RecipeCommandToRecipe;
import com.recipeproject.converters.RecipeToRecipeCommand;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);

    }

    @Test
    public void getRecepiByIdTest(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        Assert.assertNotNull("No Recipes Returned",recipeReturned);
        Assert.assertEquals(1l,recipeReturned.getId());
        Mockito.verify(recipeRepository,Mockito.times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }

    @Test
    public void getRecepiesTest() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        Mockito.when(recipeService.getRecepies()).thenReturn(recipesData);

        Set<Recipe> recipeSet = recipeService.getRecepies();
        Assert.assertEquals(recipeSet.size(),1);
        Mockito.verify(recipeRepository,Mockito.times(1));
    }
}