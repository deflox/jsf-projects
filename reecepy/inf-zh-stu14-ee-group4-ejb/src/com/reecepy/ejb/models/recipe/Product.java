package com.reecepy.ejb.models.recipe;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the products database table.
 *
 * @author Leo Rudin
 */
@Entity(name = "Products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;

    @Column(name = "productName")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "amountUnit")
    private Unit unit;

    public Product() {
        this.unit = new Unit();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
}
