package com.topbloc.dao;

import com.topbloc.model.Candy;
import com.topbloc.model.Inventory;
import com.topbloc.model.Store;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.*;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

public class PoiStockDao {

    public JSONObject getLowStockCandy(String request, Double percentThreshold) {
        Store store = mapRequestToStore(request, percentThreshold);
        return store.toJsonObject();
    }

    public JSONObject getReorderCost(String orderQuantities, Double percentThreshold) {
        JSONObject jsonOrderRequest = new JSONObject(orderQuantities);
        String jsonStoreName = jsonOrderRequest.getString("storeName");
        Store store = mapRequestToStore(jsonStoreName, percentThreshold);
        JSONArray jsonOrderQuantities = jsonOrderRequest.getJSONArray("orderQuantities");
        BigDecimal bigDecimalTotalPrice = BigDecimal.ZERO;
        Candy candy = new Candy();

        for (int i = 0; i < jsonOrderQuantities.length(); i++) {
            try {
                candy.setName(jsonOrderQuantities.getJSONObject(i).getString("candyName"));
                System.out.println(candy.getName());
                candy.setSku(jsonOrderQuantities.getJSONObject(i).getInt("sku"));
                System.out.println(candy.getSku());
                Double orderQuantity = jsonOrderQuantities.getJSONObject(i).getDouble("orderQuantity");

                if (orderQuantity < 0) {
                    orderQuantity = 0.0;
                }

                if (orderQuantity > (store.getStoreInventoryByCandy(candy).getMaxCapacity() - store.getStoreInventoryByCandy(candy).getInStockAmount())) {
                    orderQuantity = store.getStoreInventoryByCandy(candy).getMaxCapacity() - store.getStoreInventoryByCandy(candy).getInStockAmount();
                }

                Double cheapestPrice = getCheapestDistributor(candy).orElseThrow(IllegalStateException::new);

                bigDecimalTotalPrice = bigDecimalTotalPrice.add(BigDecimal.valueOf(cheapestPrice).multiply(BigDecimal.valueOf(orderQuantity)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        JSONObject totalPrice = new JSONObject();
        totalPrice.put("totalPrice", String.valueOf(bigDecimalTotalPrice));

        return totalPrice;
    }

    private Store mapRequestToStore(String storeName, Double percentThreshold) {
        Store store = new Store();
        store.setName(storeName);
        HashMap<Candy, Inventory> currentStoreInventory = new HashMap<>();

        try {
            File inventoryFile = new File("resources/Inventory.xlsx");
            FileInputStream inventoryStream = new FileInputStream(inventoryFile);
            XSSFWorkbook inventoryWorkbook = new XSSFWorkbook(inventoryStream);
            XSSFSheet storeSheet = inventoryWorkbook.getSheet(storeName);
            Iterator<Row> rowIterator = storeSheet.rowIterator();

            //Start on the second line of worksheet
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
                while (cellIterator.hasNext()) {
                    Candy candy = new Candy();
                    Inventory inventory = new Inventory();
                    Cell cell = cellIterator.next();
                    candy.setName(cell.toString());
                    cell = cellIterator.next();
                    inventory.setInStockAmount(Double.parseDouble((cell.toString())));
                    cell = cellIterator.next();
                    inventory.setMaxCapacity(Double.parseDouble((cell.toString())));
                    cell = cellIterator.next();
                    candy.setSku((int) Double.parseDouble((cell.toString())));
                    if (((inventory.getInStockAmount() / inventory.getMaxCapacity()) * 100) < percentThreshold) {
                        currentStoreInventory.put(candy, inventory);
                    }

                }
            }
            store.setStoreCandyInventory(currentStoreInventory);
            inventoryWorkbook.close();
            inventoryStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return store;
    }

    private OptionalDouble getCheapestDistributor(Candy candy) {
        List<Double> distributorPrices = new ArrayList<>();

        try {
            File distributorsFile = new File("resources/Distributors.xlsx");
            FileInputStream distributorsStream = new FileInputStream(distributorsFile);
            XSSFWorkbook distributorsWorkbook = new XSSFWorkbook(distributorsStream);
            Iterator<Sheet> distributorsSheetIterator = distributorsWorkbook.sheetIterator();

            while (distributorsSheetIterator.hasNext()) {
                XSSFSheet distributorSheet = (XSSFSheet) distributorsSheetIterator.next();
                System.out.println(distributorSheet.getSheetName());
                Iterator<Row> rowIterator = distributorSheet.rowIterator();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cell.toString().equals(candy.getName())) {
                            cell = cellIterator.next();
                            cell = cellIterator.next();
                            distributorPrices.add(Double.parseDouble((cell.toString())));
                        }
                    }
                }
            }
            distributorsWorkbook.close();
            distributorsStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return distributorPrices
                .stream()
                .mapToDouble(v -> v)
                .min();
    }

}
