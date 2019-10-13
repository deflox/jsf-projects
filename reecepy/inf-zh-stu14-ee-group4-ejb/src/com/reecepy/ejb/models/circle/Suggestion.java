package com.reecepy.ejb.models.circle;

import com.reecepy.ejb.models.recipe.Recipe;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the attempt day table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "suggestions")
public class Suggestion implements Serializable {

    private static final String[] WEEKDAYS = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "suggestionId")
    private int suggestionId;

    @Column(name = "circleId")
    private int circleId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "isBought")
    private boolean bought;

    @Column(name = "isSelected")
    private boolean selected;

    @ManyToOne
    @JoinColumn(name = "recipeId")
    private Recipe recipe;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "suggestionId")
    private List<Vote> votes;

    public Suggestion() {
    }

    public Suggestion(int circleId, Date date, boolean bought, boolean selected, int recipeId) {
        this.circleId = circleId;
        this.date = date;
        this.bought = bought;
        this.selected = selected;
        this.recipe = new Recipe();
        this.recipe.setRecipeId(recipeId);
    }

    public String getDayName() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.getDate());
        int day = c.get(Calendar.DAY_OF_WEEK) - 2;
        return WEEKDAYS[(day > -1) ? day : 6];
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        return sdf.format(date);
    }

    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int dayId) {
        this.suggestionId = dayId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean isBought) {
        this.bought = isBought;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public int getVoteCount() {
        return (this.votes == null) ? 0 : this.votes.size();
    }

    public boolean hasUserVoted(int userId) {
        for (Vote vote : votes) if (vote.getUserId() == userId) return true;
        return false;
    }

}

