package com.recipeproject.controllers;

import com.recipeproject.services.IngridientService;
import com.recipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class IngridientController {
    private RecipeService recipeService;
    private IngridientService ingridientService;

    public IngridientController(RecipeService recipeService, IngridientService ingridientService) {
        this.recipeService = recipeService;
        this.ingridientService = ingridientService;
    }

    @GetMapping
    @RequestMapping(value = "/recipe/{recipeId}/ingridients",method = RequestMethod.GET)
    public String listIngridients(@PathVariable String recipeId, Model model){
        log.debug("Getting Ingridients of Recipe " + recipeId);

        model.addAttribute("recipe", recipeService.findByIdRecipeCommand(Long.valueOf(recipeId)));

        return "recipe/ingridients/list";

    }

    @RequestMapping(value = "/recipe/{recipeId}/ingridients/{ingredientId}/show",method = RequestMethod.GET)
    public String showIngridient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model){
        model.addAttribute("ingridient", ingridientService.findRecipesIngridietById(Long.valueOf(recipeId),
                Long.valueOf(ingredientId)));

        return "recipe/ingridients/show";
    }
}
