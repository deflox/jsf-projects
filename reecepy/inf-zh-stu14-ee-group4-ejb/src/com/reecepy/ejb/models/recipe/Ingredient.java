package com.reecepy.ejb.models.recipe;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the ingredients database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "ingredients")
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ingredientId")
    private int ingredientId;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    public Ingredient() {

    }

    /**
     * Constructor which creates the objects
     *
     * @param manuel doesn't matter
     */
    public Ingredient(boolean manuel) {
        this.product = new Product();
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
