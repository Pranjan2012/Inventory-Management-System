import java.io.Console;
import java.util.Scanner;

public class UserInterface {
    private final InventoryController inventoryController;
    private final UserController userController;
    private final Scanner scanner;
    private User currentUser;

    public UserInterface() {
        inventoryController = new InventoryController();
        userController = new UserController();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.displayMenu();
    }

    public void displayMenu() {
        currentUser = login();
        if (currentUser == null) {
            System.out.println("Exiting system. Goodbye!");
            return;
        }

        while (true) {
            System.out.println("\nInventory Management System");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Item to Inventory");
            System.out.println("3. Remove Item from Inventory");
            System.out.println("4. Print Shrink Report");
            System.out.println("5. Print Low-Quantity Report");
            System.out.println("6. View Cycle Counts");
            System.out.println("7. Add Cycle Count");
            System.out.println("8. Remove Cycle Count");
            System.out.println("9. Logout");
            System.out.println("10. Exit");
            System.out.println("11. Create New User"); // ✅ NEW

            int choice = getUserInput("Enter your choice: ");
            processMenuChoice(choice);
        }
    }

    private User login() {
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();

            String password = readPasswordHidden("Enter password: ");

            User user = userController.authenticate(username, password);
            if (user != null) {
                System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
                return user;
            }

            attempts++;
            System.out.println("Invalid credentials. You have " + (3 - attempts) + " attempt(s) remaining.");
        }

        System.out.println("Login failed. Exiting system.");
        return null;
    }

    private String readPasswordHidden(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] pwdChars = console.readPassword(prompt);
            return (pwdChars == null) ? "" : new String(pwdChars);
        }

        // Fallback when IntelliJ doesn't provide a real Console
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private void processMenuChoice(int choice) {
        switch (choice) {
            case 1 -> inventoryController.viewInventory();
            case 2 -> addItem();
            case 3 -> removeItem();
            case 4 -> inventoryController.printShrinkReport();
            case 5 -> inventoryController.printLowQuantityReport();
            case 6 -> inventoryController.viewCycleCounts();
            case 7 -> addCycleCount();
            case 8 -> removeCycleCount();
            case 9 -> logout();
            case 10 -> exitSystem();
            case 11 -> createUser(); // ✅ NEW
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    // ✅ NEW: create users from the program
    private void createUser() {
        if (currentUser == null || !currentUser.checkPermissionAddUser()) {
            System.out.println("You do not have permission to add users.");
            return;
        }

        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine().trim();

        String newPassword = readPasswordHidden("Enter new password: ");

        System.out.print("Enter role (Employee / Middle Management / Owner/General Manager): ");
        String role = scanner.nextLine().trim();

        userController.addUser(newUsername, newPassword, role);
        System.out.println("User created successfully!");
    }

    private void addItem() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();

        long upcNum = getUserInputLong("Enter UPC: ");
        int skuNum = getUserInput("Enter SKU: ");
        int lowQuantityThreshold = getUserInput("Enter low quantity threshold: ");
        int numItemsWarehouse = getUserInput("Enter items in warehouse: ");
        int numItemsMainLocation = getUserInput("Enter items in main location: ");
        int numItemsOtherLocation = getUserInput("Enter items in other location: ");

        ProductClass product = new ProductClass(
                itemName, upcNum, skuNum, lowQuantityThreshold,
                numItemsWarehouse, numItemsMainLocation, numItemsOtherLocation
        );

        inventoryController.addProduct(product);
        System.out.println("Item added successfully!");
    }

    private void removeItem() {
        int sku = getUserInput("Enter SKU to remove: ");
        inventoryController.removeProduct(sku);
        System.out.println("Item removed successfully!");
    }

    private void addCycleCount() {
        int countedSku = getUserInput("Enter counted SKU: ");
        System.out.print("Enter counting user: ");
        String countingUser = scanner.nextLine();
        int cycleDifference = getUserInput("Enter cycle difference: ");
        System.out.print("Enter date changed (e.g., YYYY-MM-DD): ");
        String dateChanged = scanner.nextLine();

        CycleCountClass cycleCount = new CycleCountClass(cycleDifference, countingUser, dateChanged, countedSku);
        inventoryController.addCycleCount(cycleCount);
        System.out.println("Cycle count added successfully!");
    }

    private void removeCycleCount() {
        int countedSku = getUserInput("Enter counted SKU to remove: ");
        boolean success = inventoryController.removeCycleCount(countedSku);

        if (success) System.out.println("Cycle count removed successfully!");
        else System.out.println("No cycle count found with the provided SKU.");
    }

    private void logout() {
        System.out.println("Logging out...");
        currentUser = login();
        if (currentUser == null) {
            System.out.println("Exiting system. Goodbye!");
            System.exit(0);
        }
    }

    private void exitSystem() {
        System.out.println("Exiting system. Saving inventory...");
        inventoryController.saveInventory();
        System.out.println("Goodbye!");
        System.exit(0);
    }

    private long getUserInputLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private int getUserInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
