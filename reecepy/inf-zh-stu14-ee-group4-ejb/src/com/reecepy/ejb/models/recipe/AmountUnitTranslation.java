package com.reecepy.ejb.models.recipe;

import javax.persistence.*;

/**
 * The persistent class for the AmountUnitTranslation database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "amountunittranslation")
public class AmountUnitTranslation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "amountUnitTranslationId")
    private int amountUnitTranslationId;

    @Column(name = "amountUnit")
    private int amountUnit;

    @Column(name = "amountUnitSize")
    private int amountUnitSize;

    @Column(name = "amountUnitTranslationName")
    private String amountUnitTranslationName;

    public int getAmountUnitTranslationId() {
        return amountUnitTranslationId;
    }

    public void setAmountUnitTranslationId(int amountUnitTranslationId) {
        this.amountUnitTranslationId = amountUnitTranslationId;
    }

    public int getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(int amountUnit) {
        this.amountUnit = amountUnit;
    }

    public int getAmountUnitSize() {
        return amountUnitSize;
    }

    public void setAmountUnitSize(int amountUnitSize) {
        this.amountUnitSize = amountUnitSize;
    }

    public String getAmountUnitTranslationName() {
        return amountUnitTranslationName;
    }

    public void setAmountUnitTranslationName(String amountUnitTranslationName) {
        this.amountUnitTranslationName = amountUnitTranslationName;
    }
}
