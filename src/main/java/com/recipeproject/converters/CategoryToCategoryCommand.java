package com.recipeproject.converters;

import com.recipeproject.commands.CategoryCommand;
import com.recipeproject.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

   // private RecipeToRecipeCommand recipeConverter;

//    public CategoryToCategoryCommand(RecipeToRecipeCommand recipeConverter) {
//        this.recipeConverter = recipeConverter;
//    }

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        CategoryCommand categoryCmd = new CategoryCommand();
        categoryCmd.setDescription(source.getDescription());
        categoryCmd.setId(source.getId());

//        if (source.getRecipes() != null && source.getRecipes().size() > 0) {
//
//            source.getRecipes().forEach(recipe -> categoryCmd.getRecipes().add(recipeConverter.convert(recipe)));
//        }

        return categoryCmd;
    }
}
