/*
 * Name(s) of coder:Punjan Subedi
 * Date created: 11/21/2024
 * Date last changed: 12/1/2024
 * Name of the code artifact: ProductClass
 * Description: A class named ProductClass that has the necessary attributes for
 *              items(such as name, upc, sku, ...) and their respective getters and setters.
 */


 import java.io.Serializable;

public class ProductClass implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization consistency

    private String itemName;
    private long upcNum;
    private int skuNum;
    private int lowQuantityThreshold;
    private int numItemsWarehouse;
    private int numItemsMainLocation;
    private int numItemsOtherLocation;

    // Constructor
    public ProductClass(String itemName, long upcNum, int skuNum, int lowQuantityThreshold,
                        int numItemsWarehouse, int numItemsMainLocation, int numItemsOtherLocation) {
        this.itemName = itemName;
        this.upcNum = upcNum;
        this.skuNum = skuNum;
        this.lowQuantityThreshold = lowQuantityThreshold;
        this.numItemsWarehouse = numItemsWarehouse;
        this.numItemsMainLocation = numItemsMainLocation;
        this.numItemsOtherLocation = numItemsOtherLocation;
    }

    // Getters and Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getUpcNum() {
        return upcNum;
    }

    public void setUpcNum(long upcNum) {
        this.upcNum = upcNum;
    }

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public int getLowQuantityThreshold() {
        return lowQuantityThreshold;
    }

    public void setLowQuantityThreshold(int lowQuantityThreshold) {
        this.lowQuantityThreshold = lowQuantityThreshold;
    }

    public int getNumItemsWarehouse() {
        return numItemsWarehouse;
    }

    public void setNumItemsWarehouse(int numItemsWarehouse) {
        this.numItemsWarehouse = numItemsWarehouse;
    }

    public int getNumItemsMainLocation() {
        return numItemsMainLocation;
    }

    public void setNumItemsMainLocation(int numItemsMainLocation) {
        this.numItemsMainLocation = numItemsMainLocation;
    }

    public int getNumItemsOtherLocation() {
        return numItemsOtherLocation;
    }

    public void setNumItemsOtherLocation(int numItemsOtherLocation) {
        this.numItemsOtherLocation = numItemsOtherLocation;
    }

    @Override
    public String toString() {
        return "Product Name: " + itemName +
               "\nUPC: " + upcNum +
               "\nSKU: " + skuNum +
               "\nLow Quantity Threshold: " + lowQuantityThreshold +
               "\nItems in Warehouse: " + numItemsWarehouse +
               "\nItems in Main Location: " + numItemsMainLocation +
               "\nItems in Other Location: " + numItemsOtherLocation;
    }
}
