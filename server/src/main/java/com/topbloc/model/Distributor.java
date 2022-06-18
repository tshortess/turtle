package com.topbloc.model;

import java.math.BigDecimal;
import java.util.HashMap;

public class Distributor {

    private HashMap<Candy, BigDecimal> distributorCandyPrices;

    public Distributor(HashMap<Candy, BigDecimal> distributorCandyPrices) {
        this.distributorCandyPrices = distributorCandyPrices;
    }

    public HashMap<Candy, BigDecimal> getDistributorCandyPrices() {
        return distributorCandyPrices;
    }

    public void setDistributorCandyPrices(HashMap<Candy, BigDecimal> distributorCandyPrices) {
        this.distributorCandyPrices = distributorCandyPrices;
    }
}
