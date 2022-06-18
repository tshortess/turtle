package com.topbloc.dao;

import com.topbloc.model.Candy;
import com.topbloc.model.Inventory;
import com.topbloc.model.Store;

import java.math.BigDecimal;
import java.util.HashMap;

public class PoiStockDao implements StockDao {


    @Override
    public HashMap<Candy, Inventory> getLowStockCandy(Store store) {
        return null;
    }

    @Override
    public BigDecimal getReorderCost(HashMap<Candy, Integer> orderQuantities) {
        return null;
    }


}
