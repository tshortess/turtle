package com.topbloc.dao;

import com.topbloc.model.Candy;
import com.topbloc.model.Inventory;
import com.topbloc.model.Store;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.*;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

public class PoiStockDao {

    public JSONObject getLowStockCandy(String request, Double percentThreshold) {
        Store store = mapRequestToStore(request, percentThreshold);
        return store.toJsonObject();
    }

    //TODO: Return JSON containing the total cost of restocking candy
    public BigDecimal getReorderCost(String orderQuantities) {
        return null;
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
            Iterator<Row> rowIterator = storeSheet.iterator();

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
}
