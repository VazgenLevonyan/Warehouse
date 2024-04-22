package com.warehouse.resources;

import com.warehouse.util.Util;

import java.util.*;

public class Player implements com.warehouse.observer.Observer {
    public Player(String name) {
        Util.validateString(name);
        this.name = name;
        this.warehouseMap = new HashMap<>();
    }

    private String name;
    private Map<String, Warehouse> warehouseMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Util.validateString(name);
        this.name = name;
    }

    public Map<String, Warehouse> getWarehouseMap() {
        return warehouseMap;
    }

    public Warehouse getWarehouse(String warehouseName) {
        return warehouseMap.get(warehouseName);
    }

    public List<Warehouse> getAllWarehouses() {
        return new ArrayList<>(warehouseMap.values());
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for Player " + name + ": " + message);
    }
}
