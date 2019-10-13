package com.reecepy.web.controller;

import com.reecepy.ejb.controller.CircleManagementBeanLocal;
import com.reecepy.ejb.controller.RecipeManagementBeanLocal;
import com.reecepy.ejb.models.circle.Day;
import com.reecepy.ejb.models.recipe.Recipe;
import com.reecepy.ejb.models.circle.Suggestion;
import com.reecepy.web.helper.SessionUtils;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages a dashboard view
 * Prepare data for the dashboard
 *
 * @author  Leo Rudin & Patrick Stillhart
 */
@ManagedBean
@RequestScoped
public class DashboardBean implements Serializable {

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    @EJB
    private CircleManagementBeanLocal circleManagementBeanLocal;

    @EJB
    private RecipeManagementBeanLocal recipeManagementBeanLocal;

    private Map<Date, Day> suggestions;
    private List<String> dates;

    private String date;
    private String inputRecipe;

    private boolean recipesSuggestionsFound;
    private List<Recipe> recipesSuggestion;

    private String response;

    @PostConstruct
    public void init() {

        fillPlan();

        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE - MM/dd", Locale.ENGLISH);
        dates = new ArrayList<>(14);
        for(int i = 0; i < 14; i++) {
            dates.add(LocalDateTime.from(today.plusDays(i)).format(formatter));
        }
        date = dates.get(0);

        recipesSuggestionsFound = false;
        recipesSuggestion = new ArrayList<>();
    }

    /**
     * loads current the plan in suggestions
     */
    private void fillPlan() {
        suggestions = new LinkedHashMap<>(14); // 2 weeks long

        List<Suggestion> rawSuggestions = circleManagementBeanLocal.getTableForCircle(userSessionBean.getUser().getCircleId(), getDate(0), getDate(14));

        for(Suggestion suggestion : rawSuggestions) {
            Day day = suggestions.get(suggestion.getDate());
            if(day == null) suggestions.put(suggestion.getDate(), new Day(suggestion.getDate(), suggestion));
            else day.addSuggestion(suggestion);
        }

        suggestions.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(d -> {
                    d.setSuggestions(d.getSuggestions().stream()
                            .sorted((v1, v2) -> Integer.compare(v2.getVoteCount(), v1.getVoteCount()))
                            .collect(Collectors.toList()));
                    d.getSuggestions().get(0).setSelected(true);
                });

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

    /**
     * Switches the user vote of the current user
     *
     * @param suggestion the suggestion vote
     */
    public void switchVote(Suggestion suggestion) {
        circleManagementBeanLocal.switchVote(userSessionBean.getUser(), suggestion);
        // Suggestion needs to be updated
        init();
    }

    /**
     * *PopUp* Updates the suggestions
     */
    public void updateSuggestions() {
        recipesSuggestion.clear();
        if(inputRecipe != null && !inputRecipe.isEmpty()) {
            recipesSuggestion = recipeManagementBeanLocal.getFilteredRecipes(inputRecipe, null, null, null, 7);
            recipesSuggestionsFound = recipesSuggestion.size() != 0;
        }
    }

    /**
     * *PopUp* adds a suggestion
     */
    public void addSuggestion() {
        Recipe recipe = recipeManagementBeanLocal.getRecipeByName(inputRecipe);
        // Check if ok
        if(recipe == null) {
            SessionUtils.createMessage("Sorry, we don't have this recipe");
            response = "no";
            updateSuggestions();
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

        // Prepare the new values
        inputRecipe = "";
        recipesSuggestionsFound = false;
        fillPlan();

    }

    /*
    JSF Getter & setter
     */

    public Map<Date, Day> getSuggestions() {
        return suggestions;
    }

    public void setCircleManagementBeanLocal(CircleManagementBeanLocal circleManagementBeanLocal) {
        this.circleManagementBeanLocal = circleManagementBeanLocal;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public String getInputRecipe() {
        return inputRecipe;
    }

    public void setInputRecipe(String inputRecipe) {
        this.inputRecipe = inputRecipe;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getDates() {
        return dates;
    }

    public List<Recipe> getRecipesSuggestion() {
        return recipesSuggestion;
    }

    public boolean isRecipesSuggestionsFound() {
        return recipesSuggestionsFound;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
