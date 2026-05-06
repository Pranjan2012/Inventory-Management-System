/**
 * Name(s) of coder: Laxman Kharel, Punjan Subedi, Cedric Lucas
 * Date created: 11/01/2024
 * Date last changed: 01/04/2026
 * Name of the code artifact: InventoryController Class
 *
 * Description:
 * This class manages inventory operations, including adding, removing, and viewing products.
 * It supports persistent storage by saving the inventory to a file and loading it when the program starts.
 *
 * Fixes/Updates (01/04/2026):
 * - Added printLowQuantityReport() method (correct name to match menu/UI)
 * - Kept printLowQualityReport() for backward compatibility (calls printLowQuantityReport)
 * - Added role-based overload: printLowQuantityReport(User user)
 */

import java.io.*;
import java.util.ArrayList;

public class InventoryController implements Serializable {
    private static final long serialVersionUID = 1L;

    // List of products in the inventory
    private final ArrayList<ProductClass> products;

    // List of cycle counts
    private final ArrayList<CycleCountClass> cycleCounts;

    // Constructor: Initializes the inventory and cycle counts
    public InventoryController() {
        products = loadInventory();
        cycleCounts = new ArrayList<>();
    }

    /**
     * Adds a product to the inventory.
     */
    public void addProduct(ProductClass product) {
        products.add(product);
        System.out.println("Product added successfully.");
    }

    /**
     * Removes a product from the inventory by SKU.
     */
    public void removeProduct(int sku) {
        boolean removed = products.removeIf(product -> product.getSkuNum() == sku);
        if (removed) {
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("No product found with the given SKU.");
        }
    }

    /**
     * Displays all products in the inventory.
     */
    public void viewInventory() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            for (ProductClass product : products) {
                System.out.println(product);
                System.out.println("--------------------------------");
            }
        }
    }

    /**
     * Adds a cycle count to the list.
     */
    public void addCycleCount(CycleCountClass cycleCount) {
        cycleCounts.add(cycleCount);
        System.out.println("Cycle count added successfully.");
    }

    /**
     * Removes a cycle count by counted SKU.
     */
    public boolean removeCycleCount(int countedSku) {
        boolean removed = cycleCounts.removeIf(cycleCount -> cycleCount.getCountedSku() == countedSku);
        if (removed) {
            System.out.println("Cycle count removed successfully.");
        } else {
            System.out.println("No cycle count found with the provided SKU.");
        }
        return removed;
    }

    /**
     * Displays all cycle counts.
     */
    public void viewCycleCounts() {
        if (cycleCounts.isEmpty()) {
            System.out.println("No cycle counts available.");
        } else {
            System.out.println("Cycle Counts:");
            for (CycleCountClass cycleCount : cycleCounts) {
                System.out.println(cycleCount);
                System.out.println("--------------------------------");
            }
        }
    }

    /**
     * Generates and prints the shrink report.
     */
    public void printShrinkReport() {
        System.out.println("Shrink Report:");
        boolean found = false;

        for (ProductClass product : products) {
            int totalQuantity =
                    product.getNumItemsWarehouse()
                            + product.getNumItemsMainLocation()
                            + product.getNumItemsOtherLocation();

            if (totalQuantity <= 0) {
                System.out.println(product);
                System.out.println("--------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No shrinkage detected in the inventory.");
        }
    }

    /**
     * Correct method name for the menu/UI:
     * Generates and prints the low-quantity report (items below threshold).
     */
    public void printLowQuantityReport() {
        System.out.println("Low-Quantity Report:");
        boolean found = false;

        for (ProductClass product : products) {
            int totalQuantity =
                    product.getNumItemsWarehouse()
                            + product.getNumItemsMainLocation()
                            + product.getNumItemsOtherLocation();

            if (totalQuantity < product.getLowQuantityThreshold()) {
                System.out.println(product);
                System.out.println("--------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No items below the low quantity threshold.");
        }
    }

    /**
     * Role-based version (recommended).
     * Only allows Middle management or Owner/General Manager to run report.
     */
    public void printLowQuantityReport(User user) {
        if (user == null || user.getRole() == null) {
            System.out.println("You do not have permission to generate this report.");
            return;
        }

        String role = user.getRole();
        boolean allowed = role.equalsIgnoreCase("Middle management")
                || role.equalsIgnoreCase("Owner/General Manager");

        if (!allowed) {
            System.out.println("You do not have permission to generate this report.");
            return;
        }

        // If allowed, run the report
        printLowQuantityReport();
    }

    /**
     * Backward compatibility:
     * Your old code called this "Low-Quality", but it is really "Low-Quantity".
     * Keeping this avoids breaking other code—internally it calls the correct method.
     */
    public void printLowQualityReport() {
        printLowQuantityReport();
    }

    /**
     * Saves the inventory to a file for persistent storage.
     */
    public void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("inventory.dat"))) {
            oos.writeObject(products);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads the inventory from a file during program initialization.
     */
    @SuppressWarnings("unchecked")
    private ArrayList<ProductClass> loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("inventory.dat"))) {
            return (ArrayList<ProductClass>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved inventory found. Starting with an empty inventory.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
