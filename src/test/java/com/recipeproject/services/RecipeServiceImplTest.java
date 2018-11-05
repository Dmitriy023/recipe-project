package com.recipeproject.services;

import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);

    }
    @Test
    public void getRecepies() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        Mockito.when(recipeService.getRecepies()).thenReturn(recipesData);

        Set<Recipe> recipeSet = recipeService.getRecepies();
        Assert.assertEquals(recipeSet.size(),1);
        Mockito.verify(recipeRepository,Mockito.times(1));
    }
}