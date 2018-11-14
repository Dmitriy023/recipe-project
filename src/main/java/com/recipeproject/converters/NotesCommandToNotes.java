package com.recipeproject.converters;

import com.recipeproject.commands.NotesCommand;
import com.recipeproject.domain.Notes;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

  private RecipeRepository recipeRepository;

    public NotesCommandToNotes(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {

        if (source == null) {
            return null;
        }

        Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());

        Optional<Recipe> recipeOptional = recipeRepository.findById(source.getRecipeId());
        if(recipeOptional.isPresent()){
            notes.setRecipe(recipeOptional.get());
        }
       // notes.setRecipe(recipeConverter.convert(source.getRecipe()));

        return notes;
    }
}
