package com.topbloc.dao;

import com.topbloc.model.Candy;
import com.topbloc.model.Inventory;
import com.topbloc.model.Store;

import java.math.BigDecimal;
import java.util.HashMap;

public interface StockDao {

    HashMap<Candy, Inventory> getLowStockCandy(Store store);

    BigDecimal getReorderCost(HashMap<Candy, Integer> orderQuantities);

}
