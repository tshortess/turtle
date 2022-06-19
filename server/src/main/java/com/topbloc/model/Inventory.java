package com.topbloc.model;

public class Inventory {

    private Double inStockAmount;

    private Double maxCapacity;

    public Inventory() {
    }

    public Inventory(Double inStockAmount, Double maxCapacity) {
        this.inStockAmount = inStockAmount;
        this.maxCapacity = maxCapacity;
    }

    public Double getInStockAmount() {
        return inStockAmount;
    }

    public void setInStockAmount(Double inStockAmount) {
        this.inStockAmount = inStockAmount;
    }

    public Double getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
