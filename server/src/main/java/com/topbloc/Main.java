package com.topbloc;

import com.topbloc.controller.StockController;
import com.topbloc.dao.PoiStockDao;

import static spark.Spark.*;

public class Main {

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

                    response.header("Access-Control-Allow-Methods",
                            "GET, POST");


                    return "OK";
                });

        //This is required to allow the React app to communicate with this API
        before((request, response) -> response.header("Access-Control-Allow-Origin", "http://localhost:3000"));

        get("/low-stock", (request, response) -> {
            try {
                return stockController.getLowStockCandy(request.body());
            } catch(Exception e) {
                return "Oops...something went wrong!";
            }
        });

        post("/restock-cost", (request, response) -> {
            try {
            return stockController.getReorderCost(request.body());
            } catch (Exception e) {
                return "Oops...something went wrong!";
            }
        });
    }
}
