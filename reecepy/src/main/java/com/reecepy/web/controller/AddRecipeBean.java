package com.reecepy.web.controller;

import com.reecepy.ejb.controller.RecipeManagementBeanLocal;
import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.recipe.*;
import com.reecepy.web.helper.SessionUtils;
import com.reecepy.web.helper.UrlUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage a add-recipe view
 * Prepare data for add recipe view
 *
 * @author  Leo Rudin
 */
@ManagedBean
@ViewScoped
public class AddRecipeBean {

    @EJB
    private RecipeManagementBeanLocal recipeManagementBeanLocal;

    private List<Kitchen> kitchens;
    private int kitchen;

    private List<Ingredient> ingredients;
    private List<Unit> units;
    private int unit;

    private Recipe recipe;

    @PostConstruct
    public void init() {

        ingredients = new ArrayList<>();
        units = new ArrayList<>();

        kitchen = -1;
        unit = -1;

        // Prepare dropdown data
        recipe = new Recipe();
        kitchens = recipeManagementBeanLocal.getAllKitchens();
        units = recipeManagementBeanLocal.getAllUnits();

    }

    public String addRecipe() {

        if (kitchen == -1) {
            SessionUtils.createMessage("Please choose a kitchen.");
            return null;
        }

        // Get the kitchenId from List
        recipe.setKitchenId(kitchens.get(kitchen).getKitchenId());

        Product currentProduct;

        try {

            Recipe addedRecipe = recipeManagementBeanLocal.insertRecipe(recipe);

            for(Ingredient ingredient : ingredients) {

                // Check if product already exists in database
                currentProduct = recipeManagementBeanLocal.getProductByName(ingredient.getProduct().getProductName());
                if (currentProduct == null) {
                    // Product does not exist, insert
                    ingredient.setProduct(recipeManagementBeanLocal.insertProduct(ingredient.getProduct()));
                } else {
                    // Product exists, set product in ingredient
                    ingredient.setProduct(currentProduct);
                }

                ingredient.setRecipe(addedRecipe);

            }
            addedRecipe.setIngredients(ingredients);
            recipeManagementBeanLocal.updateRecipe(addedRecipe);

            return UrlUtils.forward(UrlUtils.Dashboard);

        } catch (ValidationException e) {
            SessionUtils.createMessage(e.getMessage());
            return null;
        }

    }


    public void addIngredient() {
        ingredients.add(new Ingredient(true));
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }


    /*
    JSF Getter & setter
     */

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Kitchen> getKitchens() {
        return kitchens;
    }

    public void setKitchens(List<Kitchen> kitchens) {
        this.kitchens = kitchens;
    }

    public RecipeManagementBeanLocal getRecipeManagementBeanLocal() {
        return recipeManagementBeanLocal;
    }

    public void setRecipeManagementBeanLocal(RecipeManagementBeanLocal recipeManagementBeanLocal) {
        this.recipeManagementBeanLocal = recipeManagementBeanLocal;
    }

    public int getKitchen() {
        return kitchen;
    }

    public void setKitchen(int kitchen) {
        this.kitchen = kitchen;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
