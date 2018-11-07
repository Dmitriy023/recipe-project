package com.recipeproject.converters;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private IngridientCommandToIngridient ingridientConverter;
    private NotesCommandToNotes notesConverter;
    private CategoryCommandToCategory categoryConverter;

    public RecipeCommandToRecipe(IngridientCommandToIngridient ingridientConverter, NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter) {
        this.ingridientConverter = ingridientConverter;
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {

        if (source == null) {
            return null;
        }

        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setServings(source.getServings());
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setImage(source.getImage());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getIngridients() != null && source.getIngridients().size() > 0) {
            source.getIngridients().forEach(ingridient -> recipe.getIngridients().add(ingridientConverter.convert(ingridient)));
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {

            source.getCategories().forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }


        return recipe;
    }
}
