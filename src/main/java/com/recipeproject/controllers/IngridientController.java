package com.recipeproject.controllers;

import com.recipeproject.commands.IngridientCommand;
import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.commands.UnitOfMeasureCommand;
import com.recipeproject.services.IngridientService;
import com.recipeproject.services.RecipeService;
import com.recipeproject.services.UomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngridientController {
    private RecipeService recipeService;
    private IngridientService ingridientService;
    private UomService uomService;

    public IngridientController(RecipeService recipeService, IngridientService ingridientService, UomService uomService) {
        this.recipeService = recipeService;
        this.ingridientService = ingridientService;
        this.uomService = uomService;
    }

    @GetMapping
    @RequestMapping(value = "/recipe/{recipeId}/ingridients")
    public String listIngridients(@PathVariable String recipeId, Model model) {
        log.debug("Getting Ingridients of Recipe " + recipeId);

        model.addAttribute("recipe", recipeService.findByIdRecipeCommand(Long.valueOf(recipeId)));

        return "recipe/ingridients/list";

    }


    @GetMapping
    @RequestMapping(value = "/recipe/{recipeId}/ingridient/new")
    public  String newIngridientInRecipe(@PathVariable String recipeId, Model model){

        RecipeCommand recipeCommand = recipeService.findByIdRecipeCommand(Long.valueOf(recipeId));

        IngridientCommand ingridientCommand = new IngridientCommand();
        ingridientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingridient", ingridientCommand);

        ingridientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", uomService.listAllUoms());

        return "recipe/ingridients/ingridientform";
    }

    @RequestMapping(value = "/recipe/{recipeId}/ingridient/{ingredientId}/show", method = RequestMethod.GET)
    public String showIngridient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model) {
        model.addAttribute("ingridient", ingridientService.findRecipesIngridietById(Long.valueOf(recipeId),
                Long.valueOf(ingredientId)));

        return "recipe/ingridients/show";
    }

    @RequestMapping(value = "recipe/{recipeId}/ingridient/{ingridientID}/update", method = RequestMethod.GET)
    public String updateRecipeIngridient(@PathVariable String recipeId,
                                         @PathVariable String ingridientID,
                                         Model model) {
        model.addAttribute("ingridient", ingridientService.findRecipesIngridietById(Long.valueOf(recipeId), Long.valueOf(ingridientID)));
        model.addAttribute("uomList", uomService.listAllUoms());
        return "recipe/ingridients/ingridientform";
    }


    @PostMapping
    @RequestMapping(value = "recipe/{recipeId}/ingridient")
    public String saveOrUpdateIngridient(@ModelAttribute IngridientCommand command) {
        IngridientCommand savedIngridient = ingridientService.saveIngridientCommand(command);

        log.debug("saved Recipe: " + savedIngridient.getRecipeId());
        log.debug("saved ingridient " + savedIngridient.getId());

        return "redirect:/recipe/" + savedIngridient.getRecipeId() + "/ingridient/" + savedIngridient.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingridient/{ingridientId}/delete")
    public String deleteIngridient(@PathVariable String recipeId,
                                   @PathVariable String ingridientId){

        ingridientService.deleteIngridientById(Long.valueOf(recipeId),Long.valueOf(ingridientId));

        return "redirect:/recipe/"+recipeId+"/ingridients/";

    }
}
