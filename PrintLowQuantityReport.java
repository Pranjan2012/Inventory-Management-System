/**
 * Name(s) of coder: Abdullah Ahmad (original), Updated by: Laxman Kharel / Team
 * Date created: 11/28/2024
 * Date last changed: 01/04/2026
 * Name of the code artifact: PrintLowQuantityReport
 *
 * Description:
 * The system allows users to print low quantity reports, which lists items whose running quantities
 * have fallen below their set low quantity thresholds.
 *
 * Fixes/Improvements (01/04/2026):
 * - Added setUserRole() (so InventoryController/UserInterface can pass the logged-in user's role)
 * - Made permission check null-safe (prevents NullPointerException)
 * - Removed try-with-resources Scanner on System.in (prevents closing System.in and breaking later input)
 * - Added simple reportID auto-generation if not set
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrintLowQuantityReport {

    private String reportID;
    private Date startDate; // Start date of the report week
    private Date endDate;   // End date of the report week
    private List<String> lowQuantityItems = new ArrayList<>(); // Stores item details as strings
    private Date generatedDate;
    private String userRole;

    // --------------------
    // Getters and Setters
    // --------------------

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String id) {
        this.reportID = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public String getUserRole() {
        return userRole;
    }

    /**
     * IMPORTANT: Set this from the logged-in user before generating the report.
     * Example: report.setUserRole(currentUser.getRole());
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    // --------------------
    // Permission Check
    // --------------------

    private boolean checkPermission() {
        if (userRole == null) return false;

        return userRole.equalsIgnoreCase("Middle management")
                || userRole.equalsIgnoreCase("Owner/General Manager");
    }

    // --------------------
    // Report Generation
    // --------------------

    /**
     * Generate a low quantity report for the date range.
     * NOTE: queryDatabaseForLowQuantityItems() is still placeholder logic.
     */
    public void generateReport(Date startDate, Date endDate) {
        if (!checkPermission()) {
            System.out.println("You do not have permission to generate this report.");
            return;
        }

        setStartDate(startDate);
        setEndDate(endDate);

        // Placeholder logic: replace later with real inventory query
        lowQuantityItems = queryDatabaseForLowQuantityItems(startDate, endDate);

        generatedDate = getCurrentDate();

        // If reportID wasn't set, auto-generate one
        if (reportID == null || reportID.trim().isEmpty()) {
            reportID = "LQ-" + generatedDate.getTime();
        }

        System.out.println("Report generated successfully for the date range: " + startDate + " to " + endDate);
    }

    private Date getCurrentDate() {
        return new Date();
    }

    // --------------------
    // Display / Print
    // --------------------

    public void displayReport() {
        System.out.println("\n==== Low Quantity Report ====");
        System.out.println("Report ID: " + (reportID == null ? "(not set)" : reportID));
        System.out.println("Generated: " + (generatedDate == null ? "(not generated yet)" : generatedDate));
        System.out.println("Period: " + (startDate == null ? "(not set)" : startDate) + " to " + (endDate == null ? "(not set)" : endDate));
        System.out.println("-----------------------------");

        if (lowQuantityItems == null || lowQuantityItems.isEmpty()) {
            System.out.println("No low-quantity items found.");
            return;
        }

        for (String item : lowQuantityItems) {
            System.out.println(item);
        }
    }

    /**
     * Confirm if the user wants to print the report.
     * NOTE: Do NOT close System.in here (no try-with-resources), otherwise your whole program breaks later.
     */
    private boolean confirmPrint() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Do you want to print the report? (Yes/No): ");
        String userResponse = scanner.nextLine().trim();
        return "Yes".equalsIgnoreCase(userResponse) || "Y".equalsIgnoreCase(userResponse);
    }

    public void printReport() {
        if (lowQuantityItems == null) lowQuantityItems = new ArrayList<>();

        if (confirmPrint()) {
            sendToPrinter(lowQuantityItems);
            logPrintAction();
            System.out.println("Report printed successfully.");
        } else {
            System.out.println("Print canceled.");
        }
    }

    private void logPrintAction() {
        System.out.println("Log: Printed Low Quantity Report with ID: " + reportID + " on " + generatedDate);
    }

    // --------------------
    // Placeholder data logic (replace later)
    // --------------------

    /**
     * Placeholder logic. Replace with real inventory logic:
     * - Loop through products
     * - Calculate running quantity
     * - Compare to low threshold
     * - Build item strings
     */
    private List<String> queryDatabaseForLowQuantityItems(Date startDate, Date endDate) {
        List<String> items = new ArrayList<>();
        items.add("Item A, SKU: 1001, Quantity: 5, Threshold: 10");
        items.add("Item B, SKU: 1002, Quantity: 8, Threshold: 15");
        return items;
    }

    private void sendToPrinter(List<String> items) {
        System.out.println("Sending report to printer...");
        for (String item : items) {
            System.out.println("Printing: " + item);
        }
    }
}
