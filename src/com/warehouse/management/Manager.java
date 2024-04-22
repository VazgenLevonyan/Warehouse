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
        validateArguments(material, warehouse);

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

    @Override
    public void addMaterial(Warehouse warehouse, Material material) {
        validateArguments(warehouse, material);

        if (!warehouse.getMaterialsWithExistCapacity().containsKey(material)) {
            throw new ResourceManagementException("Material " + material.getType().getName() + " is not attached to the warehouse " + warehouse.getName());
        }

        int existQuantity = warehouse.getMaterialsWithExistCapacity().get(material);
        final int newQuantity = existQuantity + material.getQuantity();

        ensureCapacityWithinMax(warehouse, material, newQuantity);

        warehouse.getMaterialsWithExistCapacity().put(material, newQuantity);
        logger.info(material.getQuantity() + " " + material.getType().getName() + " added to warehouse " + warehouse.getName() + ". ");
    }

    @Override
    public void removeMaterial(Warehouse warehouse, Material material) {
        validateArguments(warehouse, material);

        Integer existQuantity = warehouse.getMaterialsWithExistCapacity().get(material);
        int remainingQuantity = existQuantity - material.getQuantity();

        ensureSufficientQuantity(warehouse, material, existQuantity, remainingQuantity);
        updateMaterialQuantity(warehouse, material, remainingQuantity);

        logger.info(material.getQuantity() + " " + material.getType().getName() + " removed from warehouse " + warehouse.getName() + ". ");
    }

    @Override
    public void transfer(Player player, Warehouse from, Warehouse to, Material material) {
        validateArguments(player, from, to, material);

        final int existQuantity = from.getMaterialQuantity(material);
        final int transferQuantity = material.getQuantity();

        ensureSufficientQuantity(from, material, existQuantity, transferQuantity);
        ensureCapacityWithinLimit(to, material, transferQuantity);
        executeTransfer(from, to, material);

        logger.info("Transfer between " + from.getName() + " and " + to.getName() + " is successfully done  done");
    }


    private void ensureSufficientQuantity(Warehouse from, Material material, int existQuantity, int transferQuantity) {
        if (existQuantity < transferQuantity) {
            logger.warning("Insufficient quantity of " + material.getType().getName() + " in warehouse " + from.getName());
            throw new ResourceManagementException("Insufficient quantity of " + material.getType().getName() + " in warehouse " + from.getName());
        }
    }

    private void ensureCapacityWithinLimit(Warehouse to, Material material, int transferQuantity) {
        if (to.getMaterialQuantity(material) + transferQuantity > material.getType().getMaxCapacity()) {
            logger.warning("Exceeds maximum capacity of " + to.getName() + " for " + material.getType().getName());
            throw new ResourceManagementException("Exceeds maximum capacity of " + to.getName() + " for " + material.getType().getName());
        }
    }

    private void executeTransfer(Warehouse from, Warehouse to, Material material) {
        removeMaterial(from, material);
        addMaterial(to, material);
    }

    private void ensureCapacityWithinMax(Warehouse warehouse, Material material, int newQuantity) {
        if (newQuantity > material.getType().getMaxCapacity()) {
            throw new ResourceManagementException("Exceeds maximum capacity for " + material.getType().getName() + " in warehouse " + warehouse.getName());
        }
    }

    private void ensureSufficientQuantity(Warehouse warehouse, Material material, Integer existQuantity, int remainingQuantity) {
        if (existQuantity == null) {
            String errorMessage = material.getType().getName() + " not found in warehouse " + warehouse.getName();
            logger.warning(errorMessage);
            throw new ResourceManagementException(errorMessage);
        }
        if (remainingQuantity < 0) {
            String errorMessage = "Insufficient quantity of " + material.getType().getName() + " in warehouse " + warehouse.getName();
            logger.warning(errorMessage);
            throw new ResourceManagementException(errorMessage);
        }
    }

    private void updateMaterialQuantity(Warehouse warehouse, Material material, int remainingQuantity) {
        warehouse.getMaterialsWithExistCapacity().put(material, remainingQuantity);
    }

    private void validateArguments(Object... objects) {
        Util.checkNotNull(objects);
    }
}
