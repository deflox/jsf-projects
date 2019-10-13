package com.reecepy.ejb.models.recipe;

import org.eclipse.persistence.annotations.Cache;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the kitchens database table.
 *
 * @author Patrick Stillhart
 */
@Entity(name = "kitchens")
@Cache
public class Kitchen implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "kitchenid")
    private int kitchenId;

    @Column(name = "kitchenName")
    private String kitchenName;

    public int getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(int kitchenid) {
        this.kitchenId = kitchenid;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }
}
