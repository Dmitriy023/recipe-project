package com.recipeproject.controllers;

import com.recipeproject.domain.Recipe;
import com.recipeproject.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);

    }

    @Test
    public void testMVC() throws Exception{

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(MockMvcRequestBuilders.get(""))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getIndexPage() {
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2);
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);

        Mockito.when(recipeService.getRecepies()).thenReturn(recipeSet);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String indexPage = indexController.getIndexPage(model);


        Assert.assertEquals("index", indexPage);
        Mockito.verify(recipeService, Mockito.times(1)).getRecepies();
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"),argumentCaptor.capture());
        Set<Recipe> recipesSetInTest = argumentCaptor.getValue();
        Assert.assertEquals(2,recipesSetInTest.size());
    }
}