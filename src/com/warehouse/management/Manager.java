package com.warehouse.management;

import com.warehouse.exceptions.ResourceManagementException;
import com.warehouse.resources.Material;
import com.warehouse.resources.MaterialType;
import com.warehouse.resources.Player;
import com.warehouse.resources.Warehouse;
import com.warehouse.util.Util;

import java.util.logging.Logger;

public class Manager implements TransactionInterface{
    private static final Logger logger = Logger.getLogger(Manager.class.getName());

    @Override
    public Player createPlayer(String name) {
        final Player player = new Player(name);
        logger.info("Player " + name + " successfully created");
        return player;
    }

    @Override
    public Warehouse createWarehouse(String name) {
        final Warehouse warehouse = new Warehouse(name);
        logger.info("dal.Warehouse " + name + " successfully created");
        return warehouse;
    }

    @Override
    public Material createMaterial(MaterialType materialType, int quantity) {
        validateArguments(materialType);
        if (quantity > materialType.getMaxCapacity())
            throw new ResourceManagementException("Exceeds maximum capacity for " + materialType.getName());
        final Material material = new Material(materialType, quantity);
        logger.info("dal.Material " + material.getType().getName() + " successfully created");
        return material;
    }

    private void validateArguments(Object... objects) {
        Util.checkNotNull(objects);
    }
}
