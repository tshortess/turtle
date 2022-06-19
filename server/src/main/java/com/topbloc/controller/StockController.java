package com.topbloc.controller;

import com.topbloc.dao.PoiStockDao;
import org.json.JSONObject;


public class StockController {

    private PoiStockDao poiStockDao;

    public StockController(PoiStockDao poiStockDao) {
        this.poiStockDao = poiStockDao;
    }

    public JSONObject getLowStockCandy(String store, Double percentThreshold) {
        return poiStockDao.getLowStockCandy(store,percentThreshold);
    }

    public JSONObject getReorderCost(String orderQuantities, Double percentThreshold) {
        return poiStockDao.getReorderCost(orderQuantities, percentThreshold);
    }

}
