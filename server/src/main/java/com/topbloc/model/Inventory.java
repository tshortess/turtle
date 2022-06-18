package com.topbloc.model;

public class Inventory {

    private int inStockAmount;

    private int maxCapacity;

    public Inventory() {
    }

    public Inventory(int inStockAmount, int maxCapacity) {
        this.inStockAmount = inStockAmount;
        this.maxCapacity = maxCapacity;
    }

    public int getInStockAmount() {
        return inStockAmount;
    }

    public void setInStockAmount(int inStockAmount) {
        this.inStockAmount = inStockAmount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
