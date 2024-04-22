package com.warehouse.management;

import com.warehouse.resources.Material;
import com.warehouse.resources.MaterialType;
import com.warehouse.resources.Player;
import com.warehouse.resources.Warehouse;

public interface TransactionInterface {

    Player createPlayer(String name);

    Warehouse createWarehouse(String name);

    Material createMaterial(MaterialType materialType, int quantity);

    void attachWarehouseToPlayer(Warehouse warehouse, Player player);

    void detachWarehouseFromPlayer(Warehouse warehouse, Player player);
}
