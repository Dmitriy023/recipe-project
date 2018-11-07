package com.recipeproject.converters;

import com.recipeproject.commands.CategoryCommand;
import com.recipeproject.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class CategoryToCategoryCommand implements Converter<Category,CategoryCommand>{

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if(source==null){
            return null;
        }
        CategoryCommand categoryCmd = new CategoryCommand();
        categoryCmd.setDescription(source.getDescription());
        categoryCmd.setId(source.getId());
        //categoryCmd.setRecipes();
        return categoryCmd;
    }
}
