package com.topbloc.controller;

import com.topbloc.dao.PoiStockDao;
import com.topbloc.model.Store;
import org.json.JSONObject;

import java.math.BigDecimal;

public class StockController {

    private PoiStockDao poiStockDao;

    public StockController(PoiStockDao poiStockDao) {
        this.poiStockDao = poiStockDao;
    }

    public JSONObject getLowStockCandy(String store) {
        return poiStockDao.getLowStockCandy(store);
    }

    public BigDecimal getReorderCost(String orderQuantities) {
        return poiStockDao.getReorderCost(orderQuantities);
    }



}
