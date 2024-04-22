package com.warehouse.management;

import com.warehouse.exceptions.ResourceManagementException;
import com.warehouse.resources.Material;
import com.warehouse.resources.MaterialType;
import com.warehouse.resources.Player;
import com.warehouse.resources.Warehouse;
import com.warehouse.util.Util;

import java.util.logging.Logger;

public class Manager implements TransactionInterface {
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
        logger.info("Warehouse " + name + " successfully created");
        return warehouse;
    }

    @Override
    public Material createMaterial(MaterialType materialType, int quantity) {
        validateArguments(materialType);
        if (quantity > materialType.getMaxCapacity())
            throw new ResourceManagementException("Exceeds maximum capacity for " + materialType.getName());
        final Material material = new Material(materialType, quantity);
        logger.info("Material " + material.getType().getName() + " successfully created");
        return material;
    }

    @Override
    public void attachWarehouseToPlayer(Warehouse warehouse, Player player) {
        validateArguments(warehouse, player);

        if (Util.isWarehouseAttached(warehouse)) {
            throw new ResourceManagementException("Warehouse " + warehouse.getName() + " is already attached");
        }

        player.getWarehouseMap().put(warehouse.getName(), warehouse);
        warehouse.setAttachedToPlayer(true);
        logger.info("Warehouse " + warehouse.getName() + " successfully attached to player " + player.getName());
    }

    @Override
    public void detachWarehouseFromPlayer(Warehouse warehouse, Player player) {
        validateArguments(warehouse, player);

        if (player.getWarehouse(warehouse.getName()) == null)
            throw new ResourceManagementException("Player " + player.getName() + " does not have warehouse " + warehouse.getName() + " in his warehouse list");
        player.getWarehouseMap().remove(warehouse.getName());
        logger.info("Warehouse " + warehouse.getName() + " successfully detached from player " + player.getName());
    }

    @Override
    public void attachMaterialToWarehouse(Material material, Warehouse warehouse) {
        validateArguments(material,warehouse);

        if (warehouse.getMaterialsWithExistCapacity().containsKey(material)) {
            throw new ResourceManagementException("Material " + material.getType().getName() + " is already attached to the warehouse " + warehouse.getName());
        }

        warehouse.getMaterialsWithExistCapacity().put(material, 0);
        logger.info("Material " + material.getType().getName() + " successfully attached to warehouse " + warehouse.getName());
    }

    @Override
    public void detachMaterialFromWarehouse(Material material, Warehouse warehouse) {
        validateArguments(material, warehouse);

        if (!warehouse.getMaterialsWithExistCapacity().containsKey(material)) {
            throw new ResourceManagementException("Material " + material.getType().getName() + " does not attached to the warehouse " + warehouse.getName());
        }
        warehouse.getMaterialsWithExistCapacity().remove(material);
        logger.info("Material " + material.getType().getName() + " successfully detached from warehouse " + warehouse.getName());
    }

    private void validateArguments(Object... objects) {
        Util.checkNotNull(objects);
    }
}
