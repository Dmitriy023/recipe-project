package com.recipeproject.controllers;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Recipe;
import com.recipeproject.exceptions.NotFoundException;
import com.recipeproject.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.Assert.*;

public class RecipeControllerTest {

    RecipeController recipeController;

    MockMvc mockMvc;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }

    @Test
    public void showRecipeById() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Mockito.when(recipeService.findById(Mockito.anyLong())).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));

    }

    @Test
    public void getNewRecipeForm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

    }

    @Test
    public void updateRecipe() throws Exception {

        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.findByIdRecipeCommand(Mockito.anyLong())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

    }

    @Test
    public void saveOrUpdate() throws Exception {

        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("description", "some strong")
                .param("directions", "some strong")

        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));

    }

    @Test
    public void validateRecipeBeforeSaving() throws Exception {
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(2L);

        when(recipeService.findByIdRecipeCommand(anyLong())).thenReturn(cmd);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"));
    }

    @Test
    public void deleteRecipe() {
    }

    @Test
    public void recipeNotFoundPage() throws Exception {

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("404error"));

    }

    @Test
    public void numberFormatExceptionTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/ssss/show"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.model().attributeExists("exception"))
                .andExpect(MockMvcResultMatchers.view().name("400error"));

    }


}