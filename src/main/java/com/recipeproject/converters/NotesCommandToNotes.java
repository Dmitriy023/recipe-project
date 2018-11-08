package com.recipeproject.converters;

import com.recipeproject.commands.NotesCommand;
import com.recipeproject.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    //private RecipeCommandToRecipe recipeConverter;

//    public NotesCommandToNotes(RecipeCommandToRecipe recipeConverter) {
//        this.recipeConverter = recipeConverter;
//    }

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
       // notes.setRecipe(recipeConverter.convert(source.getRecipe()));

        return notes;
    }
}
