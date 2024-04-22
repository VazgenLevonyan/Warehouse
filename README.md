Warehouse Management System

Description

Warehouse Management System is a pure Java project that allows players to manage their materials in the warehouses they own. It provides functionalities for creating players, warehouses, material types, and materials, as well as transferring materials between warehouses. The system implements the observer pattern for notification purposes and ensures proper validation and exception handling to maintain valid warehouse data.

Note: This README corresponds to the version of the application located in the warehouse branch.

Usage

Step 1: Setting up the Manager


Step 2: Creating a Player

Step 3: Creating and Attaching a Warehouse to a Player

Step 4: Registering Observers (Optional)

Observers are components of the observer design pattern used to provide a subscription mechanism to an object (the subject) so that multiple dependent objects (the observers) can be notified of any state changes. In the context of the Warehouse Management System, observers are entities interested in receiving notifications about changes in the warehouses.

By registering observers with a warehouse, players can stay informed about any updates or events occurring within that warehouse. For example, a player might want to receive notifications when new materials are added/removed to/from their warehouse.

Registering observers is optional but can be useful for players who want to stay informed about the state of their warehouses without actively monitoring them.

To register an observer with a warehouse, use the registerObserver() method provided by the Observable interface implemented by the warehouse class.

Step 5: Creating Material Types and Materials

Step 6: Attaching Materials to Warehouses

Step 7: Adding Materials to Warehouses

Step 8: Transferring Materials Between Player's Warehouses

Transferring materials between a player's warehouses involves moving materials from one warehouse to another. Here's a more detailed breakdown of how to perform this action:

Create and Attach Another Warehouse to the Player: Before transferring materials, you need to create another warehouse and attach it to the player who owns both the source and destination warehouses. You can use the createWarehouse() method of the Manager class to create a new warehouse, and then attach it to the player using the attachWarehouseToPlayer() method.

Attach Appropriate Materials to the New Warehouse: Once you have the destination warehouse ready, you need to attach the appropriate materials to it. Use the attachMaterialToWarehouse() method to associate the desired materials with the new warehouse.

Initiate the Transfer: With both the source and destination warehouses prepared, you can initiate the transfer using the transfer() method provided by the Manager class. This method requires specifying the player, the source warehouse, the destination warehouse, and the material to be transferred.


Dependencies

Warehouse Management System has no external dependencies. It is written in pure Java. The project is compatible with Java SE 8 and later versions. Make sure you have the appropriate version of Java installed to run the project.
