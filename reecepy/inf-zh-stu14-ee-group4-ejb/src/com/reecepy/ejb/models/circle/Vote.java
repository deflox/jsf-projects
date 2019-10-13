package com.reecepy.ejb.models.circle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The persistent class for the votes database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "votes")
public class Vote implements Serializable {

    @Id
    @Column(name = "suggestionId")
    private int suggestionId;

    @Id
    @Column(name = "userId")
    private int userId;

    public Vote() {
    }

    public Vote(int suggestionId, int userId) {
        this.suggestionId = suggestionId;
        this.userId = userId;
    }

    public int getDayId() {
        return suggestionId;
    }

    public void setDayId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
