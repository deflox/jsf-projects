package com.reecepy.ejb.models.recipe;

import javax.persistence.*;

/**
 * The persistent class for the AmountUnits database table.
 *
 * @author Leo Rudin
 */
@Entity(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "unitId")
    private int unitId;

    @Column(name = "unitName")
    private String unitName;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
