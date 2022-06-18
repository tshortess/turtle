package com.topbloc.model;

import java.util.HashMap;

public class Store {

    private HashMap<Candy, Inventory> storeCandyInventory;

    public Store(HashMap<Candy, Inventory> hashMapOfInventory) {
        this.storeCandyInventory = hashMapOfInventory;
    }

    public HashMap<Candy, Inventory> getStoreCandyInventory() {
        return storeCandyInventory;
    }

    public void setStoreCandyInventory(HashMap<Candy, Inventory> storeCandyInventory) {
        this.storeCandyInventory = storeCandyInventory;
    }
}
