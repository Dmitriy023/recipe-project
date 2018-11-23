package com.recipeproject.repositories;

import com.recipeproject.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeRepository extends CrudRepository <Recipe,Long>{
   Set<Recipe> findAllByOrderByIdAsc();
}
