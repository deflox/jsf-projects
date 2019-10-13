package com.reecepy.web.controller;

import com.reecepy.ejb.controller.CircleManagementBeanLocal;
import com.reecepy.ejb.controller.RecipeManagementBeanLocal;
import com.reecepy.ejb.models.circle.Suggestion;
import com.reecepy.ejb.models.recipe.Recipe;
import com.reecepy.web.helper.SessionUtils;
import com.reecepy.web.helper.UrlUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Manages a recipe detail view
 * Prepare data for recipe detail view
 *
 * @author  Leo Rudin & Patrick Stillhart
 */
@ManagedBean
@ViewScoped
public class RecipeBean implements Serializable {

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    @EJB
    private RecipeManagementBeanLocal recipeManagementBeanLocal;

    @EJB
    private CircleManagementBeanLocal circleManagementBeanLocal;

    private Recipe recipe;

    private List<String> dates;

    private String date;

    private List<Recipe> recipesSuggestion;

    private String response;

    @PostConstruct
    public void init() {
        try {
            int id = Integer.parseInt(UrlUtils.getDomainParameter("id"));
            recipe = recipeManagementBeanLocal.getRecipeById(id);
            if(recipe == null) throw new NullPointerException();

        } catch (NumberFormatException | NullPointerException  e) {
            // Not valid id
            try {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(UrlUtils.forward(UrlUtils.BROWSE));
            } catch (IOException ex) {
                // back to home if no code
            }
        }

        // Preparation for adding recipe
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE - MM/dd", Locale.ENGLISH);
        dates = new ArrayList<>(14);
        for(int i = 0; i < 14; i++) {
            dates.add(LocalDateTime.from(today.plusDays(i)).format(formatter));
        }
        date = dates.get(0);

        recipesSuggestion = new ArrayList<>();

    }

    /**
     * adds a new suggestion
     */
    public void addSuggestion() {

        // Check if ok
        if(recipe == null) {
            SessionUtils.createMessage("Sorry, we don't have this recipe");
            response = "no";
            return;
        }

        int dayIndex = dates.indexOf(date);
        if(dayIndex == -1) dayIndex = 0;

        // Create a new suggestion
        Suggestion suggestion = new Suggestion(userSessionBean.getUser().getCircleId(),
                getDate(dayIndex),
                false,
                false,
                recipe.getRecipeId());

        int suggestionId = circleManagementBeanLocal.addSuggestion(suggestion);
        response = "ok";

        circleManagementBeanLocal.addVote(userSessionBean.getUser(), suggestionId);

    }

    /**
     * Get a date
     *
     * @param offset how many days in the future (0 = current day)
     * @return the date
     */
    public Date getDate(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Calendar.getInstance().getTime());
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    /*
    JSF Getter & setter
     */

    public Recipe getRecipe() {
        return recipe;
    }

    public UserSessionBean getUserSessionBean() {
        return userSessionBean;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public RecipeManagementBeanLocal getRecipeManagementBeanLocal() {
        return recipeManagementBeanLocal;
    }

    public void setRecipeManagementBeanLocal(RecipeManagementBeanLocal recipeManagementBeanLocal) {
        this.recipeManagementBeanLocal = recipeManagementBeanLocal;
    }

    public CircleManagementBeanLocal getCircleManagementBeanLocal() {
        return circleManagementBeanLocal;
    }

    public void setCircleManagementBeanLocal(CircleManagementBeanLocal circleManagementBeanLocal) {
        this.circleManagementBeanLocal = circleManagementBeanLocal;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Recipe> getRecipesSuggestion() {
        return recipesSuggestion;
    }

    public void setRecipesSuggestion(List<Recipe> recipesSuggestion) {
        this.recipesSuggestion = recipesSuggestion;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
