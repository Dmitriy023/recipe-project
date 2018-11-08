package com.recipeproject.commands;

import com.recipeproject.domain.Recipe;
import com.recipeproject.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class IngridientCommand {

    private Long id;
    private String description;
    private BigDecimal amount;
    private RecipeCommand recipe;
    private UnitOfMeasureCommand uom;

}
