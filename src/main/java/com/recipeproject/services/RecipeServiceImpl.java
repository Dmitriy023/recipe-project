package com.recipeproject.services;

import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecepies() {
      Set<Recipe> recipesSet = new HashSet<>();

      recipeRepository.findAll().iterator().forEachRemaining(recipesSet::add);

      return recipesSet;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        if(!recipeOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found");
        }
        return recipeOptional.get();
    }
}
