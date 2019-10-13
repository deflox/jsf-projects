package com.reecepy.ejb.models.recipe;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the recipes database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "recipes")
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "recipeId")
    private int recipeId;

    @Column(name = "kitchenId")
    private int kitchenId;

    @Column(name = "recipeName")
    private String recipeName;

    @Column(name = "shortDesc")
    private String shortDesc;

    @Column(name = "calories")
    private int calories;

    @Lob
    @Column(name = "fullDesc")
    private String fullDesc;

    @Column(name = "titleImage")
    private String titleImage;

    @Column(name = "duration")
    private int duration;

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.PERSIST)
    private List<Ingredient> ingredients;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(int kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getFullDesc() {
        return fullDesc;
    }

    public void setFullDesc(String fullDesc) {
        this.fullDesc = fullDesc;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


}
