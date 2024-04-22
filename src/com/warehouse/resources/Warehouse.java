package com.warehouse.resources;

import com.warehouse.util.Util;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    public Warehouse(String name) {
        Util.validateString(name);
        this.name = name;
        this.isAttachedToPlayer = false;
        this.materialsWithExistCapacity = new HashMap<>();
    }

    private String name;
    private final Map<Material, Integer> materialsWithExistCapacity;

    private boolean isAttachedToPlayer;
    ;

    public boolean isAttachedToPlayer() {
        return isAttachedToPlayer;
    }

    public void setAttachedToPlayer(boolean attachedToPlayer) {
        isAttachedToPlayer = attachedToPlayer;
    }

    public Map<Material, Integer> getMaterialsWithExistCapacity() {
        return materialsWithExistCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Util.validateString(name);
        this.name = name;
    }

    public int getMaterialQuantity(Material material) {
        return materialsWithExistCapacity.getOrDefault(material, 0);
    }

    public String displayMaterialsInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Materials in warehouse ").append(name).append(":\n");
        for (Map.Entry<Material, Integer> entry : materialsWithExistCapacity.entrySet()) {
            Material material = entry.getKey();
            int quantity = entry.getValue();
            info.append(material.getType().getName()).append(": ").append(quantity).append("\n");
        }
        return info.toString();
    }
}
