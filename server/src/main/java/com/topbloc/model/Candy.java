package com.topbloc.model;

public class Candy {

    private String name;

    private int sku;

    public Candy() {
    }

    public Candy(String name, int id) {
        this.name = name;
        this.sku = id;
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

}
