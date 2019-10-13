package com.reecepy.ejb.controller;

import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.recipe.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages everything regarding recipes
 *
 * @author Patrick Stillhart
 */
@LocalBean
@Stateless
public class RecipeManagementBeanLocal implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger LOGGER = Logger.getLogger(UserManagementBeanLocal.class.getName());

    /**
     * Gets all recipes matching the criterias
     *
     * @param search   (optional) search in title
     * @param kitchen  (optional) kitchen
     * @param duration (optional) duration
     * @param calories (optional) calories
     * @return all recipes for the criterias
     */
    @SuppressWarnings("unchecked")
    public List<Recipe> getFilteredRecipes(String search, String kitchen, String duration, String calories, int limit) {

        int values = 0;
        if (search != null && search.length() > 0) values++;
        if (kitchen != null && kitchen.length() > 0) values++;
        if (duration != null && duration.length() > 0) values++;
        if (calories != null && calories.length() > 0) values++;

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = query.from(Recipe.class);

        Predicate[] predicates = new Predicate[values];
        // Search
        if (search != null && search.length() > 0) predicates[--values] = (cb.like(recipeRoot.get("recipeName"), "%" + search + "%"));

        // Kitchen
        if (kitchen != null && kitchen.length() > 0) predicates[--values] = (cb.equal(recipeRoot.get("kitchenId"), kitchen));

        // Duration
        if (duration != null && duration.length() > 0) predicates[--values] = (cb.lessThan(recipeRoot.get("duration"), duration));

        // Calories
        if (calories != null && calories.length() > 0) predicates[--values] = (cb.lessThan(recipeRoot.get("calories"), calories));

        query = query.where(predicates);
        Query readyQuery = entityManager.createQuery(query);
        if(limit != 0) readyQuery.setMaxResults(limit);
        return readyQuery.getResultList();


    }

    /**
     * Gives all recipes
     *
     * @param all         if the limit should be active
     * @param maxResults  (if all = true) the max amount of results
     * @param firstResult (if all = true)the offset
     * @return all recipes
     */
    @SuppressWarnings("unchecked")
    public List<Recipe> getAllRecipes(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Recipe.class));
        Query q = entityManager.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    /**
     * Returns a Recipe for the id
     *
     * @param id the id
     * @return the recipe
     */
    public Recipe getRecipeById(int id) {
        return entityManager.find(Recipe.class, id);
    }

    /**
     * Returns a Recipe for the name
     *
     * @param name the name
     * @return the recipe
     */
    public Recipe getRecipeByName(String name) {
        Query query = entityManager.createQuery("SELECT r FROM recipes r WHERE r.recipeName=:recipeName", Recipe.class);
        query.setParameter("recipeName", name);
        return (query.getResultList().size() == 0) ? null : (Recipe) query.getResultList().get(0);
    }

    /**
     * Returns all kitchens
     *
     * @return all kitchens
     */
    @SuppressWarnings("unchecked")
    public List<Kitchen> getAllKitchens() {
        Query query = entityManager.createQuery("SELECT k FROM kitchens k", Kitchen.class);
        return query.getResultList();
    }

    /**
     * Returns all Amount Units
     *
     * @return all amountUnits
     */
    @SuppressWarnings("unchecked")
    public List<AmountUnitTranslation> getAllAmountUnitTranslations() {
        Query query = entityManager.createQuery("SELECT a FROM amountunittranslation a", AmountUnitTranslation.class);
        return query.getResultList();
    }

    /**
     * Returns all units
     *
     * @return all units
     */
    @SuppressWarnings("unchecked")
    public List<Unit> getAllUnits() {
        Query query = entityManager.createQuery("SELECT u FROM units u", Unit.class);
        return query.getResultList();
    }

    /**
     * Gets one product by productname.
     *
     * @param productName The name of the product
     * @return A object for type product
     */
    @SuppressWarnings("unchecked")
    public Product getProductByName(String productName) {
        Query query = entityManager.createQuery("SELECT p FROM Products p WHERE p.productName=:productName", Product.class);
        query.setParameter("productName", productName);

        if (query.getResultList().size() > 0) {
            return (Product) query.getResultList().get(0);
        } else {
            return null;
        }

    }

    /**
     * Inserts a new product.
     *
     * @param product The product, which should get inserted.
     */
    public Product insertProduct(Product product) {
        entityManager.persist(product);
        entityManager.flush();

        LOGGER.info("Product added: " + product.getProductName());

        return product;

    }

    /**
     * Inserts a new ingredient into database.
     *
     * @param ingredient The ingredient, which should get inserted.
     */
    public void insertIngredient(Ingredient ingredient) {
        entityManager.persist(ingredient);
        LOGGER.info("Ingredient added: " + ingredient.getProduct().getProductName());

    }

    /**
     * Inserts new recipe.
     *
     * @param recipe: Recipe, you want to insert
     * @throws ValidationException
     */
    public Recipe insertRecipe(Recipe recipe) throws ValidationException {
        validateRecipe(recipe);

        entityManager.persist(recipe);
        entityManager.flush();
        LOGGER.info("Recipe added: " + recipe.getRecipeName() + " - ID: " + recipe.getRecipeId());

        return recipe;

    }

    /**
     * Update a recipe
     *
     * @param recipe the recipe with updated info's
     * @throws ValidationException some fields aren't correct
     */
    public void updateRecipe(Recipe recipe) throws ValidationException {

        validateRecipe(recipe);

        entityManager.merge(recipe);
        LOGGER.info("Recipe updated: " + recipe.getRecipeName());

    }

    /**
     * Validates a recipe
     *
     * @param recipe the recipe to validate
     * @throws ValidationException something isn't valide
     */
    public void validateRecipe(Recipe recipe) throws ValidationException {
        if (recipe.getRecipeName() == null || recipe.getRecipeName().length() < 3 || recipe.getRecipeName().length() > 45)
            throw new ValidationException("Recipe name must have at least 3 characters and cannot exceed length of 45 characters.");
        if (recipe.getShortDesc() == null || recipe.getShortDesc().length() < 3 || recipe.getShortDesc().length() > 200)
            throw new ValidationException("Short description must have at least 3 characters and cannot exceed length of 200 characters.");
        if (recipe.getTitleImage() == null || recipe.getTitleImage().length() < 3 || recipe.getTitleImage().length() > 150)
            throw new ValidationException("Image path must have at least 3 characters and cannot exceed length of 150 characters");
    }

}
