package com.reecepy.web.controller;

import com.reecepy.ejb.controller.RecipeManagementBeanLocal;
import com.reecepy.ejb.models.recipe.Kitchen;
import com.reecepy.ejb.models.recipe.Recipe;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Bean for the browse page
 * Provides and prepares data for filtering
 *
 * @author  Patrick Stillhart
 */
@ManagedBean
@RequestScoped
public class BrowseBean implements Serializable {

    @EJB
    private RecipeManagementBeanLocal recipeManagementBeanLocal;

    /**
     * A list with the filter options and there names for durations
     */
    private static final String[][] durationValues = new String[][]{
            new String[]{"less than 10 min", "10"},
            new String[]{"less than 20 min", "20"},
            new String[]{"less than 40 min", "40"},
            new String[]{"less than 1 hour", "60"},
            new String[]{"less than 1' 20 min", "80"},
            new String[]{"less than 1' 40 min", "100"},
    };

    /**
     * A list with the filter options and there names for calories
     */
    private static final String[][] caloriesValues = new String[][]{
            new String[]{"less than 200", "200"},
            new String[]{"less than 300", "300"},
            new String[]{"less than 400", "400"},
            new String[]{"less than 600", "600"},
            new String[]{"less than 800", "800"},
            new String[]{"less than 1000", "1000"}
    };

    private List<Kitchen> kitchens;

    private String query;
    private int kitchen;
    private String duration;
    private String calories;

    private List<Recipe> results;

    @PostConstruct
    public void init() {
        results = recipeManagementBeanLocal.getAllRecipes(false, 10, 0);
        kitchens = recipeManagementBeanLocal.getAllKitchens();

        kitchen = -1;
    }

    /**
     * loads all recipes for the supplied parameters
     */
    public void loadResults() {
        results.clear();

        String kitchen = (this.kitchen == -1) ? null : this.kitchens.get(this.kitchen).getKitchenId()+"";
        String duration = (this.duration == null) ? null : getValueForDuration(this.duration);
        String calories = (this.calories == null) ? null : getValueForCalories(this.calories);

        // ToDo: ajax dynamic loading
        results = recipeManagementBeanLocal.getFilteredRecipes(query, kitchen, duration, calories, 10);

    }

    /*
    Make 2 separate methods just to not always copy the arrays around when filtering again
     */
    private static String getValueForDuration(String query) {
        for (String[] row : durationValues) {
            if (row[0].equals(query)) return row[1];
        }
        return null;
    }

    private static String getValueForCalories(String query) {
        for (String[] row : caloriesValues) {
            if (row[0].equals(query)) return row[1];
        }
        return null;
    }



    /*
    JSF Getter & setter
   */

    public void setRecipeManagementBeanLocal(RecipeManagementBeanLocal recipeManagementBeanLocal) {
        this.recipeManagementBeanLocal = recipeManagementBeanLocal;
    }

    public List<Kitchen> getKitchens() {
        return kitchens;
    }

    public String[][] getDurationValues() {
        return durationValues;
    }

    public String[][] getCaloriesValues() {
        return caloriesValues;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getKitchen() {
        return kitchen;
    }

    public void setKitchen(int kitchen) {
        this.kitchen = kitchen;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}
