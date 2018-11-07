package com.recipeproject.commands;

import com.recipeproject.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NotesCommand {

    private Long id;
    private Recipe recipe;
    private String recipeNotes;

}