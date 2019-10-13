package com.reecepy.ejb.models.circle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A holder class for a day, contains the date and all suggestions for that day
 *
 * @author Patrick Stillhart
 */
public class Day implements Serializable {

    private Date date;

    private List<Suggestion> suggestions;

    public Day(Date date, Suggestion suggestion) {
        this.date = date;
        this.suggestions = new ArrayList<>();
        this.suggestions.add(suggestion);
    }

    public boolean hasSelectedSuggestion() {
        for(Suggestion suggestion : suggestions) if(suggestion.isSelected()) return true;
        return false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDayName() {
        return suggestions.get(0).getDayName();
    }

    public String getFormatedDate() {
        return suggestions.get(0).getFormattedDate();
    }

    public void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

}

