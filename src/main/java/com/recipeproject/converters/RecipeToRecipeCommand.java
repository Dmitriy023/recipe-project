package com.recipeproject.converters;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private IngridientToIngridientCommand ingridientConverter;
    private CategoryToCategoryCommand categoryConverter;
    private NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(IngridientToIngridientCommand ingridientConverter, CategoryToCategoryCommand categoryConverter, NotesToNotesCommand notesConverter) {
        this.ingridientConverter = ingridientConverter;
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {

        if (source == null) {
            return null;
        }

        RecipeCommand recipeCmd = new RecipeCommand();
        recipeCmd.setUrl(source.getUrl());
        recipeCmd.setCookTime(source.getCookTime());
        recipeCmd.setDescription(source.getDescription());
        recipeCmd.setDifficulty(source.getDifficulty());
        recipeCmd.setDirections(source.getDirections());
        recipeCmd.setId(source.getId());
        recipeCmd.setImage(source.getImage());
        recipeCmd.setPrepTime(source.getPrepTime());
        recipeCmd.setServings(source.getServings());
        recipeCmd.setSource(source.getSource());
        recipeCmd.setNotes(notesConverter.convert(source.getNotes()));


        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipeCmd.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngridients() != null && source.getIngridients().size() > 0) {
            source.getIngridients().forEach(ingridient -> recipeCmd.getIngridients().add(ingridientConverter.convert(ingridient)));
        }

        return recipeCmd;
    }
}
