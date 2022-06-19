package com.topbloc.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private String name;

    private HashMap<Candy, Inventory> storeCandyInventory;

    public Store(){
    }

    public Store(String name, HashMap<Candy, Inventory> storeCandyInventory) {
        this.name = name;
        this.storeCandyInventory = storeCandyInventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Candy, Inventory> getStoreCandyInventory() {
        return storeCandyInventory;
    }

    public void setStoreCandyInventory(HashMap<Candy, Inventory> storeCandyInventory) {
        this.storeCandyInventory = storeCandyInventory;
    }

    public JSONObject toJsonObject() {
        JSONObject returnJsonObject = new JSONObject();
        returnJsonObject.put("storeName", getName());

        if (getStoreCandyInventory() != null) {
            JSONArray candyInventoryArray = new JSONArray();

            getStoreCandyInventory().forEach((key, value) -> {
                JSONObject candyItemObject = new JSONObject();
                candyItemObject.put("candyName", key.getName());
                candyItemObject.put("sku", String.valueOf(key.getSku()));
                candyItemObject.put("inStockAmount", String.valueOf(value.getInStockAmount()));
                candyItemObject.put("maxCapacity", String.valueOf(value.getMaxCapacity()));
                candyInventoryArray.put(candyItemObject);
            });

            returnJsonObject.put("inventory", candyInventoryArray);
        }
        return returnJsonObject;
    }

    public Inventory getStoreInventoryByCandy(Candy candy) {
        Inventory candyInventory = new Inventory();

        for (Map.Entry<Candy, Inventory> entry : storeCandyInventory.entrySet()) {
            if (entry.getKey().getName().equals(candy.getName())) {
                candyInventory.setInStockAmount(entry.getValue().getInStockAmount());
                candyInventory.setMaxCapacity(entry.getValue().getMaxCapacity());
                break;
            }
        }

        return candyInventory;
    }
}
