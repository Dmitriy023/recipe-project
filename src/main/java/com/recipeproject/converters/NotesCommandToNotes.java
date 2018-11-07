package com.recipeproject.converters;

import com.recipeproject.commands.NotesCommand;
import com.recipeproject.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand,Notes>{

    @Override
    public Notes convert(NotesCommand source) {
       if(source==null){
           return null;
       }
       Notes notes = new Notes();
       notes.setId(source.getId());
       notes.setRecipeNotes(source.getRecipeNotes());
       //notes.setRecipe();
        return  notes;
    }
}
