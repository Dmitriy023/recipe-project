package com.recipeproject.controllers;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.services.ImageService;
import com.recipeproject.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Controller
public class ImageController {
    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findByIdRecipeCommand(Long.valueOf(id)));
        return "/recipe/imageuploadform";
    }

    @PostMapping("/recipe/{id}/image")
    public String handleImagePost(@PathVariable String id,
                                  @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/" + id + "/show";

    }


    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws Exception {
        RecipeCommand command = recipeService.findByIdRecipeCommand(Long.valueOf(id));
        if(command.getImage()==null) return;
        byte[] img = new byte[command.getImage().length];
        int i = 0;
        for (Byte b : command.getImage()) {
            img[i++] = b;
        }
        response.setContentType("image/jpeg");

        InputStream is = new ByteArrayInputStream(img);
        IOUtils.copy(is, response.getOutputStream());

    }

}
