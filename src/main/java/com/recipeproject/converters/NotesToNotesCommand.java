package com.recipeproject.converters;

import com.recipeproject.commands.NotesCommand;
import com.recipeproject.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

  //  private RecipeToRecipeCommand recipeConverter;

//    public NotesToNotesCommand(RecipeToRecipeCommand recipeConverter) {
//        this.recipeConverter = recipeConverter;
//    }

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {

        if (source == null) {
            return null;
        }

        NotesCommand notesCmd = new NotesCommand();
        notesCmd.setId(source.getId());
        notesCmd.setRecipeNotes(source.getRecipeNotes());
       // notesCmd.setRecipe(recipeConverter.convert(source.getRecipe()));
        return notesCmd;

    }
}
