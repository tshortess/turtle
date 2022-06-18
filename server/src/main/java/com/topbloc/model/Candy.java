package com.topbloc.model;

public class Candy {

    private String name;

    private int sku;

    private int capacity;

    public Candy() {
    }

    public Candy(String name, int id, int maxCapacity) {
        this.name = name;
        this.sku = id;
        this.capacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
