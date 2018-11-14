package com.recipeproject.controllers;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.services.IngridientService;
import com.recipeproject.services.RecipeService;
import com.recipeproject.services.UomService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;

import static org.junit.Assert.*;

public class IngridientControllerTest {

    IngridientController ingridientController;

    MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;
    @Mock
    private IngridientService ingridientService;
    @Mock
    private UomService uomService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingridientController = new IngridientController(recipeService, ingridientService, uomService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingridientController).build();
    }

    @Test
    public void listIngridients() throws Exception {
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(1L);
        IngridientCommand ingridientCommand = new IngridientCommand();
        ingridientCommand.setId(2L);
        cmd.getIngridients().add(ingridientCommand);

        Mockito.when(ingridientService.findRecipesIngridietById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingridientCommand);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingridients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingridients/list"));

    }

    @Test
    public void newIngridientInRecipe() throws Exception {
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(1L);

        Mockito.when(recipeService.findByIdRecipeCommand(Mockito.anyLong())).thenReturn(cmd);
        Mockito.when(uomService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingridient/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingridient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"));

        Mockito.verify(recipeService, Mockito.times(1)).findByIdRecipeCommand(Mockito.anyLong());

    }

    @Test
    public void showIngridient() throws Exception {

        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        IngridientCommand ingridientCommand = new IngridientCommand();
        ingridientCommand.setId(1L);

        Mockito.when(ingridientService.findRecipesIngridietById(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingridientCommand);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingridient/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingridients/show"));

        Mockito.verify(ingridientService, Mockito.times(1)).findRecipesIngridietById(Mockito.anyLong(), Mockito.anyLong());

    }

    @Test
    public void updateRecipeIngridient() {

        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        IngridientCommand ingridientCommand = new IngridientCommand();
        ingridientCommand.setId(1L);


    }

    @Test
    public void saveOrUpdateIngridient() {
    }

    @Test
    public void deleteIngridient() {
    }
}