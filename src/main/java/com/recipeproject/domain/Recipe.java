package com.recipeproject.domain;

import com.recipeproject.enums.Difficulty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingridient> ingridients = new HashSet<>();


    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;


    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public Recipe addIngridient(Ingridient ingridient) {
        ingridient.setRecipe(this);
        this.getIngridients().add(ingridient);
        return this;
    }

    public void addCategory(Category category) {
        this.getCategories().add(category);
    }


    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

}
