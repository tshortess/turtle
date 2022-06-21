package com.topbloc.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class PoiStockDaoTests {

    private PoiStockDao sut;
    private String storeName = "Randy's Candies";

    @Before
    public void setup() {
        sut = new PoiStockDao();
    }

    @Test
    public void getFullCandyStockFromRandysCandiesReturns16Items() {
        //Arrange
        Double lowInventoryThreshold = 100.0;

        //Act
        JSONObject expectedJsonObject = sut.getCandyStock(storeName, lowInventoryThreshold);
        JSONArray expectedJsonArrayOfInventory = expectedJsonObject.getJSONArray("inventory");

        //Assert
        Assert.assertEquals(16, expectedJsonArrayOfInventory.length());
    }

    @Test
    public void get25PercentThresholdCandyStockFromRandysCandiesReturns5Items() {
        //Arrange
        Double lowInventoryThreshold = 25.0;

        //Act
        JSONObject expectedJsonObject = sut.getCandyStock(storeName, lowInventoryThreshold);
        JSONArray expectedJsonArrayOfInventory = expectedJsonObject.getJSONArray("inventory");

        //Assert
        Assert.assertEquals(5, expectedJsonArrayOfInventory.length());
    }

    @Test
    public void get0PercentThresholdCandyStockFromRandysCandiesReturns0Items() {
        //Arrange
        Double lowInventoryThreshold = 0.0;

        //Act
        JSONObject expectedJsonObject = sut.getCandyStock(storeName, lowInventoryThreshold);
        JSONArray expectedJsonArrayOfInventory = expectedJsonObject.getJSONArray("inventory");

        //Assert
        Assert.assertEquals(0, expectedJsonArrayOfInventory.length());
    }

    @Test
    public void getReorderCostReturnsLowestCost() {
        //Arrange
        String orderQuantities = "{\n" +
                "  \"storeName\": \"Randy's Candies\",\n" +
                "  \"orderQuantities\": [\n" +
                "    {\n" +
                "      \"sku\": \"520745\",\n" +
                "      \"candyName\": \"Sour Patch Kids\",\n" +
                "      \"orderQuantity\": \"1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"sku\": \"601091\",\n" +
                "      \"candyName\": \"Butterfinger\",\n" +
                "      \"orderQuantity\": \"1\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        String expectedTotalPrice = "1.780";

        //Act
        JSONObject expectedJsonObject = sut.getReorderCost(orderQuantities);

        //Assert
        Assert.assertEquals(expectedTotalPrice, expectedJsonObject.getString("totalPrice"));
    }

    @Test
    public void getReorderCostWithOrderExceedingCapacityReturnsLowestCostCappedAtMaxCapacity() {
        //Arrange
        String orderQuantities = "{\n" +
                "  \"storeName\": \"Randy's Candies\",\n" +
                "  \"orderQuantities\": [\n" +
                "    {\n" +
                "      \"sku\": \"520745\",\n" +
                "      \"candyName\": \"Sour Patch Kids\",\n" +
                "      \"orderQuantity\": \"100\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"sku\": \"601091\",\n" +
                "      \"candyName\": \"Butterfinger\",\n" +
                "      \"orderQuantity\": \"1\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        String expectedTotalPrice = "40.030";

        //Act
        JSONObject expectedJsonObject = sut.getReorderCost(orderQuantities);

        //Assert
        Assert.assertEquals(expectedTotalPrice, expectedJsonObject.getString("totalPrice"));
    }

}
