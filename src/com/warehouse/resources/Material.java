package com.warehouse.resources;

import com.warehouse.util.Util;
public class Material {

    private MaterialType type;
    private int quantity;

    public Material(MaterialType type, int quantity) {
        Util.checkNotNull(type);
        Util.validateInteger(quantity);

        this.type = type;
        this.quantity = quantity;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        Util.checkNotNull(type);
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        Util.validateInteger(quantity);
        this.quantity = quantity;
    }
}
