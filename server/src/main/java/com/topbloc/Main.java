package com.topbloc;

import com.topbloc.controller.StockController;
import com.topbloc.dao.PoiStockDao;

import static spark.Spark.*;

public class Main {

    private static final Double FULL_STOCK = 100.0;

    public static void main(String[] args) {
        Main main = new Main();
        main.setup();
    }

    private void setup() {
        PoiStockDao poiStockDao = new PoiStockDao();
        StockController stockController = new StockController(poiStockDao);
        //This is required to allow GET and POST requests with the header 'content-type'
        options("/*",
                (request, response) -> {
                    response.header("Access-Control-Allow-Headers",
                            "content-type");

                    response.header("Access-Control-Allow-Headers",
                            "storeName");

                    response.header("Access-Control-Allow-Headers",
                            "lowStockThreshold");

                    response.header("Access-Control-Allow-Methods",
                            "GET, POST");

                    return "OK";
                });

        //This is required to allow the React app to communicate with this API
        before((request, response) -> response.header("Access-Control-Allow-Origin", "http://localhost:3000"));

        get("/low-stock", (request, response) -> {
            try {
                return stockController.getCandyStock(request.headers("storeName"), Double.parseDouble(request.headers("lowStockThreshold")));
            } catch(Exception e) {
                return "Oops...an error occurred. Please reference error message: " + e.getMessage();
            }
        });

        get("/full-stock", (request, response) -> {
            try {
                return stockController.getCandyStock(request.headers("storeName"), FULL_STOCK);
            } catch(Exception e) {
                return "Oops...an error occurred. Please reference error message: " + e.getMessage();
            }
        });

        post("/restock-cost", (request, response) -> {
            try {
                return stockController.getReorderCost(request.body());
            } catch (Exception e) {
                return "Oops...an error occurred. Please reference error message: " + e.getMessage();
            }
        });
    }
}
