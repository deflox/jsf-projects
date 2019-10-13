package com.reecepy.web.controller;

import com.reecepy.ejb.controller.CircleManagementBeanLocal;
import com.reecepy.ejb.controller.RecipeManagementBeanLocal;
import com.reecepy.ejb.exceptions.ValidationException;
import com.reecepy.ejb.models.circle.AdditionalItem;
import com.reecepy.ejb.models.circle.Day;
import com.reecepy.ejb.models.circle.ShoppingCartItem;
import com.reecepy.ejb.models.circle.Suggestion;
import com.reecepy.ejb.models.recipe.*;
import com.reecepy.web.helper.SessionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Creates the shopping card for 2 weeks
 *
 * @author  Leo Rudin
 */
@ManagedBean
@RequestScoped
public class ShoppingBean implements Serializable {

    @ManagedProperty("#{userSessionBean}")
    private UserSessionBean userSessionBean;

    @EJB
    private CircleManagementBeanLocal circleManagementBeanLocal;

    @EJB
    private RecipeManagementBeanLocal recipeManagementBeanLocal;

    private Map<Date, Day> suggestions;
    private Map<String, ShoppingCartItem> shoppingItems;

    private List<AmountUnitTranslation> amountUnitTranslations;
    private AdditionalItem newItem;
    private int newItemAmountUnitTranslationIndex;
    private List<AdditionalItem> additionalItems;

    private String response;

    @PostConstruct
    public void init() {

        generateShoppingList();
        amountUnitTranslations = recipeManagementBeanLocal.getAllAmountUnitTranslations();

        loadAdditionalItems();
        response = "no";
    }

    /**
     * generates the shopping list, also sorts the list
     */
    private void generateShoppingList() {
        suggestions = new LinkedHashMap<>(14); // 2 weeks long
        List<Suggestion> rawSuggestions = circleManagementBeanLocal.getTableForCircle(userSessionBean.getUser().getCircleId(), getDate(0), getDate(14));

        // Map the suggestions to days
        for(Suggestion suggestion : rawSuggestions) {
            Day day = suggestions.get(suggestion.getDate());
            if(day == null) suggestions.put(suggestion.getDate(), new Day(suggestion.getDate(), suggestion));
            else day.addSuggestion(suggestion);
        }

        // Sort the new list
        suggestions.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(d -> d.setSuggestions(d.getSuggestions().stream()
                            .sorted((v1, v2) -> Integer.compare(v2.getVoteCount(), v1.getVoteCount()))
                            .collect(Collectors.toList()))
                );

        // Create a shopping list
        shoppingItems = new HashMap<>();
        // Collect all products which are selected and sort duplicates out and merge product amounts
        for (Day day : new ArrayList<>(this.suggestions.values())) {
            for (Suggestion suggestion : day.getSuggestions()) {
                if (suggestion.isSelected()) {
                    for (Ingredient i : suggestion.getRecipe().getIngredients()) {
                        ShoppingCartItem shoppingCartItem = this.shoppingItems.get(i.getProduct().getProductName());
                        if(shoppingCartItem == null) this.shoppingItems.put(i.getProduct().getProductName(), new ShoppingCartItem(i.getProduct().getProductName(), i.getProduct().getUnit(), i.getAmount() * userSessionBean.getUserCountForCircle()));
                        else shoppingCartItem.addAmount(i.getAmount() * userSessionBean.getUserCountForCircle());
                    }
                }
            }
        }
    }

    /**
     * loads the additional items
     */
    private void loadAdditionalItems() {
        newItem = new AdditionalItem();
        newItem.setCircleId(userSessionBean.getUser().getCircleId());
        additionalItems = circleManagementBeanLocal.getAdditionalItemsForCircle(userSessionBean.getUser().getCircleId());
    }

    /**
     * adds a suggestion and then loads the shopping-list
     *
     * @param suggestion the suggestions to update
     */
    public void loadShoppingList(Suggestion suggestion) {
        circleManagementBeanLocal.updateSuggestion(suggestion);
        init();
    }

    /**
     * Adds a new additional item (newItem) to the db
     */
    public void addItem() {
        newItem.setAmountUnitTranslation(amountUnitTranslations.get(newItemAmountUnitTranslationIndex));
        try {
            circleManagementBeanLocal.addAdditionalItem(newItem);
            loadAdditionalItems();
            response = "ok";
        } catch (ValidationException e) {
            SessionUtils.createMessage(e.getMessage());
            response = "no";
        }
    }

    /**
     * Removes an additional item
     *
     * @param additionalItem the item to remove
     */
    public void removeItem(AdditionalItem additionalItem) {
        circleManagementBeanLocal.removeAdditionalItem(additionalItem);
        loadAdditionalItems();
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

    public CircleManagementBeanLocal getCircleManagementBeanLocal() {
        return circleManagementBeanLocal;
    }

    public void setCircleManagementBeanLocal(CircleManagementBeanLocal circleManagementBeanLocal) {
        this.circleManagementBeanLocal = circleManagementBeanLocal;
    }

    public Map<String, ShoppingCartItem> getShoppingItems() {
        return shoppingItems;
    }

    public UserSessionBean getUserSessionBean() {
        return userSessionBean;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

    public Map<Date, Day> getSuggestions() {
        return suggestions;
    }

    public List<AmountUnitTranslation> getAmountUnitTranslations() {
        return amountUnitTranslations;
    }

    public AdditionalItem getNewItem() {
        return newItem;
    }

    public void setNewItem(AdditionalItem newItem) {
        this.newItem = newItem;
    }

    public int getNewItemAmountUnitTranslationIndex() {
        return newItemAmountUnitTranslationIndex;
    }

    public void setNewItemAmountUnitTranslationIndex(int newItemAmountUnitTranslationIndex) {
        this.newItemAmountUnitTranslationIndex = newItemAmountUnitTranslationIndex;
    }

    public List<AdditionalItem> getAdditionalItems() {
        return additionalItems;
    }

    public void setAdditionalItems(List<AdditionalItem> additionalItems) {
        this.additionalItems = additionalItems;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
