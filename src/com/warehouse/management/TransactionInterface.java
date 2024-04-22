package com.warehouse.management;

import com.warehouse.resources.Player;
import com.warehouse.resources.Warehouse;

public interface TransactionInterface {

    Player createPlayer(String name);

    Warehouse createWarehouse(String name);
}
