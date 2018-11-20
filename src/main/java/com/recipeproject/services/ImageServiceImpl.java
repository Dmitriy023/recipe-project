package com.recipeproject.services;

import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;
    String errorMessage;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        Optional<Recipe> recipeOPT = recipeRepository.findById(recipeId);
        if (!recipeOPT.isPresent()) {
            errorMessage = "Recipe with given id not found";
            log.debug(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        Recipe recipe = recipeOPT.get();

        try {
            Byte[] image = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                image[i++] = b;
            }
            recipe.setImage(image);
            recipeRepository.save(recipe);
            log.debug("saved recipies image:" + recipeId);
        } catch (Exception e) {
            log.debug("An error had ocured during parsing file");
            e.printStackTrace();
        }
    }
}
