package com.recipeproject.services;

import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.*;

public class ImageServiceImplTest {

    ImageService imageService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws Exception{

        Recipe recipe = new Recipe();
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        recipe.setId(2L);

        MockMultipartFile file = new MockMultipartFile("imageFile","tex.txt","text/plain", "Some Text".getBytes());


        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(recipeOptional.get().getId(),file);

        Mockito.verify(recipeRepository,Mockito.times(1)).save(argumentCaptor.capture());

        Recipe saveRecipe = argumentCaptor.getValue();
       Assert.assertEquals(file.getBytes().length, saveRecipe.getImage().length);
    }


}