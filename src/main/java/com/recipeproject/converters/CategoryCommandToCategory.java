package com.recipeproject.converters;

import com.recipeproject.commands.CategoryCommand;
import com.recipeproject.domain.Category;
import com.recipeproject.domain.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {


//
//    public CategoryCommandToCategory(RecipeCommandToRecipe recipeConverter) {
//        this.recipeConverter = recipeConverter;
//    }



    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        Category category = new Category();
        category.setId(source.getId());
        category.setDescription(category.getDescription());

//        if (source.getRecipes() != null && source.getRecipes().size() > 0) {
//
//            source.getRecipes().forEach(recipe->category.getRecipes().add(recipeConverter.convert(recipe)));
//
//        }
        return category;
    }
}
