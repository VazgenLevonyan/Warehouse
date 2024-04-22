package com.warehouse.resources;

import com.warehouse.util.Util;
public class MaterialType {

    private String name;
    private String description;
    private String icon;
    private int maxCapacity;

    public MaterialType(String name, String description, String icon, int maxCapacity) {
        Util.validateInputs(name, description, icon, maxCapacity);
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.maxCapacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        Util.validateString(name);
        this.name = name;
    }

    public void setDescription(String description) {
        Util.validateString(description);
        this.description = description;
    }

    public void setIcon(String icon) {
        Util.validateString(icon);
        this.icon = icon;
    }

    public void setMaxCapacity(int maxCapacity) {
        Util.validateInteger(maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    public String getIcon() {
        return icon;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}

