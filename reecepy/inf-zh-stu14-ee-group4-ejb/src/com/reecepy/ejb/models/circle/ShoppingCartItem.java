package com.reecepy.ejb.models.circle;

import com.reecepy.ejb.models.recipe.Unit;

/**
 * Model to save data for the shopping list.
 *
 * @author Leo Rudin
 */
public class ShoppingCartItem {

    private String productName;
    private Unit unit;
    private int amount;

    public ShoppingCartItem(String productName, Unit unit, int amount) {
        this.productName = productName;
        this.unit = unit;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

}
