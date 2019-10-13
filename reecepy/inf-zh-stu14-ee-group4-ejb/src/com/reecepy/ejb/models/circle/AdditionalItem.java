package com.reecepy.ejb.models.circle;

import com.reecepy.ejb.models.recipe.AmountUnitTranslation;
import com.reecepy.ejb.models.recipe.Unit;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the additionalItems database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "additionalitems")
public class AdditionalItem implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "additionalItemId")
    private int additionalItemId;

    @Column(name = "circleId")
    private int circleId;

    @Column(name = "additionalItemName")
    private String additionalItemName;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "translationId")
    private AmountUnitTranslation amountUnitTranslation;

    public int getAdditionalItemId() {
        return additionalItemId;
    }

    public void setAdditionalItemId(int additionalItemId) {
        this.additionalItemId = additionalItemId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getAdditionalItemName() {
        return additionalItemName;
    }

    public void setAdditionalItemName(String additionalItemName) {
        this.additionalItemName = additionalItemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AmountUnitTranslation getAmountUnitTranslation() {
        return amountUnitTranslation;
    }

    public void setAmountUnitTranslation(AmountUnitTranslation amountUnitTranslation) {
        this.amountUnitTranslation = amountUnitTranslation;
    }
}
