package com.warehouse.management;

import com.warehouse.resources.Player;

import java.util.logging.Logger;

public class Manager implements TransactionInterface{
    private static final Logger logger = Logger.getLogger(Manager.class.getName());

    @Override
    public Player createPlayer(String name) {
        final Player player = new Player(name);
        logger.info("Player " + name + " successfully created");
        return player;
    }
}
