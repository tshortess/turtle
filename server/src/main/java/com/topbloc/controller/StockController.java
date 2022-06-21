package com.topbloc.controller;

import com.topbloc.dao.PoiStockDao;
import org.json.JSONObject;


public class StockController {

    private PoiStockDao poiStockDao;

    public StockController(PoiStockDao poiStockDao) {
        this.poiStockDao = poiStockDao;
    }

    public JSONObject getCandyStock(String store, Double percentThreshold) {
        return poiStockDao.getCandyStock(store,percentThreshold);
    }

    public JSONObject getReorderCost(String orderQuantities) {
        return poiStockDao.getReorderCost(orderQuantities);
    }

}
