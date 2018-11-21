package com.recipeproject.controllers;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Recipe;
import com.recipeproject.services.ImageService;
import com.recipeproject.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;

public class ImageControllerTest {

    ImageController imageController;
    MockMvc mockMvc;

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showUploadForm() throws Exception {
        RecipeCommand recipeCMD = new RecipeCommand();
        recipeCMD.setId(1L);

        Mockito.when(recipeService.findByIdRecipeCommand(Mockito.anyLong())).thenReturn(recipeCMD);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
                .andExpect(MockMvcResultMatchers.view().name("/recipe/imageuploadform"));

    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("imagefile", "tex.txt", "text/plain", "Some Text".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/1/image").file(file))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/1/show"));
    }

    @Test
    public void renderImageFromDB() throws Exception{
        Long id = 1L;
        RecipeCommand command = new RecipeCommand();
        command.setId(id);

        String someText = "This is image";
        Byte[] image = new Byte[someText.getBytes().length];
        int i = 0;

        for (byte b : someText.getBytes()) {
            image[i++] = b;
        }
        command.setImage(image);

        Mockito.when(recipeService.findByIdRecipeCommand(Mockito.anyLong())).thenReturn(command);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/recipeimage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        Assert.assertEquals(someText.getBytes().length,responseBytes.length);

    }

    @Test
    public void numberFormatExceptionTest1() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/ssss/recipeimage"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.model().attributeExists("exception"))
                .andExpect(MockMvcResultMatchers.view().name("400error"));

    }
}