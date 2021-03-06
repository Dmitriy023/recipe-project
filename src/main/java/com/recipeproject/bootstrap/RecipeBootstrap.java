package com.recipeproject.bootstrap;

import com.recipeproject.domain.*;
import com.recipeproject.enums.Difficulty;
import com.recipeproject.repositories.CategoryRepository;
import com.recipeproject.repositories.RecipeRepository;
import com.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecepies());
        log.info("Loading Bootstrap Data");

    }

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        log.debug("H2 DB Bootstrap");
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecepies() {

        List<Recipe> recipes = new ArrayList<>(1);
        Optional<UnitOfMeasure> eachUOM = unitOfMeasureRepository.findByDescription("Each");
        if (!eachUOM.isPresent()) throw new RuntimeException("Expected UOM not Found!");

        Optional<UnitOfMeasure> tableSpoonUOM = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tableSpoonUOM.isPresent()) throw new RuntimeException("Expected UOM not Found!");

        Optional<UnitOfMeasure> teaspoonUOM = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!teaspoonUOM.isPresent()) throw new RuntimeException("Expected UOM not Found!");

        Optional<UnitOfMeasure> dashUOM = unitOfMeasureRepository.findByDescription("Dash");
        if (!dashUOM.isPresent()) throw new RuntimeException("Expected UOM not Found!");

        Optional<UnitOfMeasure> pintUOM = unitOfMeasureRepository.findByDescription("Pint");
        if (!pintUOM.isPresent()) throw new RuntimeException("Expected UOM not Found!");

        Optional<UnitOfMeasure> cupUOM = unitOfMeasureRepository.findByDescription("Cup");
        if (!cupUOM.isPresent()) throw new RuntimeException("Expected UOM not Found!");


        UnitOfMeasure each = eachUOM.get();
        UnitOfMeasure tableSpoon = tableSpoonUOM.get();
        UnitOfMeasure teaSpoon = teaspoonUOM.get();
        UnitOfMeasure dash = dashUOM.get();
        UnitOfMeasure pint = pintUOM.get();
        UnitOfMeasure cup = cupUOM.get();

        String categoryError = "Expected Category not Found!";
        Optional<Category> americanCategoryOPT = categoryRepository.findByDescription("American");
        if (!americanCategoryOPT.isPresent()) throw new RuntimeException(categoryError);

        Optional<Category> italianCategoryOPT = categoryRepository.findByDescription("Italian");
        if (!italianCategoryOPT.isPresent()) throw new RuntimeException(categoryError);

        Optional<Category> mexicanCategoryOPT = categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOPT.isPresent()) throw new RuntimeException(categoryError);


        Category americanCategory = americanCategoryOPT.get();
        Category italianCategory = italianCategoryOPT.get();
        Category mexicanCategory = mexicanCategoryOPT.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Pefect Guacomole");
        guacRecipe.setCookTime(15);
        guacRecipe.setPrepTime(10);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setSource("Simply Recipes");
        guacRecipe.setDirections(
                "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
                        "\n" +
                        "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                        "\n" +
                        "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown." +
                        "\n" +
                        "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness." +
                        "\n" +
                        "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                        "\n" +
                        "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve." +
                        "\n" +
                        "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).\n" +
                "\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.");
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngridient(new Ingridient("Ripe Avacado", new BigDecimal(2), each));
        guacRecipe.addIngridient(new Ingridient("Salt", new BigDecimal(3), teaSpoon));
        guacRecipe.addIngridient(new Ingridient("Sugar", new BigDecimal(1), teaSpoon));
        guacRecipe.addIngridient(new Ingridient("OliveOil", new BigDecimal(1), tableSpoon));
        guacRecipe.addIngridient(new Ingridient("Lime cut", new BigDecimal(3), each));

        guacRecipe.addCategory(americanCategory);
        guacRecipe.addCategory(mexicanCategory);

        recipes.add(guacRecipe);


        Recipe chickenTacos = new Recipe();
        chickenTacos.setDescription("Spicy Grilled Chicken Tacos Recipe");
        chickenTacos.setDifficulty(Difficulty.MODERATE);
        chickenTacos.setPrepTime(15);
        chickenTacos.setCookTime(20);
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setSource("Simply Recipes");
        chickenTacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat." +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over." +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings." +
                "\n" +
                "Spicy Grilled Chicken Tacos" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes." +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side." +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving." +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");


        Notes chickenTacosNote = new Notes();

        chickenTacosNote.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        chickenTacosNote.setRecipe(chickenTacos);

        chickenTacos.setNotes(chickenTacosNote);

        chickenTacos.addIngridient(new Ingridient("Ancho chili powder", new BigDecimal(2), tableSpoon));
        chickenTacos.addIngridient(new Ingridient("Dried oregano", new BigDecimal(1), teaSpoon));
        chickenTacos.addIngridient(new Ingridient("Dried cumin", new BigDecimal(1), teaSpoon));
        chickenTacos.addIngridient(new Ingridient("Sugar", new BigDecimal(1), teaSpoon));
        chickenTacos.addIngridient(new Ingridient("Boneless chicken thighs", new BigDecimal(0.25), teaSpoon));

        chickenTacos.addCategory(americanCategory);
        chickenTacos.addCategory(mexicanCategory);

        recipes.add(chickenTacos);

        return recipes;
    }
}
