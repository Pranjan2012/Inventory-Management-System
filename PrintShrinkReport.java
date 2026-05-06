/*
 * Name(s) of coder: Punjan Subedi
 * Date created: 11/26/2024
 * Date last changed: 11/26/2024
 * Name of the code artifact: PrintShrinkReportClass
 * Description: A class named PrintShrinkReportClass with necessary attributes for 
 * printing shrink reports and their respective getters and setters.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PrintShrinkReport {
    // Attributes
    private String reportID;                 // Unique ID for the report
    private List<String> shrinkageDetails;   // List to store shrinkage details
    private Date generatedDate;              // The date the report was generated
    private String userRole;                 // Role of the user accessing the report

    // Constructor
    public PrintShrinkReport(String userRole) {
        this.reportID = "";
        this.shrinkageDetails = new ArrayList<>();
        this.generatedDate = null;
        this.userRole = userRole;
    }

    // Getters and Setters
    public String getReportID() {
        return reportID;
    }

    public void setReportID(String id) {
        this.reportID = id;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date date) {
        this.generatedDate = date;
    }

    public List<String> getShrinkageDetails() {
        return shrinkageDetails;
    }

    public void setShrinkageDetails(List<String> details) {
        this.shrinkageDetails = details;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }

    // Check Permission
    public Boolean checkPermission() {
        return userRole.equalsIgnoreCase("Middle management") || userRole.equalsIgnoreCase("Owner/General Manager");
    }

    // Generate Report
    public void generateReport(Date startDate, Date endDate) {
        if (checkPermission()) {
            System.out.println("Generating shrinkage report...");
            shrinkageDetails = queryDatabaseForShrinkage(startDate, endDate);
            generatedDate = getCurrentDate();
            reportID = generateUniqueReportID();
            System.out.println("Report generated successfully for the date range: " + startDate + " to " + endDate);
        } else {
            System.out.println("You do not have permission to generate this report.");
        }
    }

    // Display Report
    public void displayReport() {
        if (shrinkageDetails != null && !shrinkageDetails.isEmpty()) {
            System.out.println("Shrinkage Report");
            System.out.println("Report ID: " + reportID);
            System.out.println("Generated Date: " + generatedDate);
            System.out.println("Details:");
            for (String detail : shrinkageDetails) {
                System.out.println("- " + detail);
            }
        } else {
            System.out.println("No shrinkage details to display. Please generate a report first.");
        }
    }

    // Confirm Print
    public Boolean confirmPrint() {
        System.out.println("Do you want to print the report? (Yes/No)");
        try (Scanner scanner = new Scanner(System.in)) {
            String userResponse = scanner.nextLine();
            return userResponse.equalsIgnoreCase("Yes");
        }
    }

    // Print Report
    public void printReport() {
        if (shrinkageDetails == null || shrinkageDetails.isEmpty()) {
            System.out.println("No report available to print. Please generate a report first.");
            return;
        }

        if (confirmPrint()) {
            sendToPrinter(shrinkageDetails);
            logPrintAction();
            System.out.println("Report printed successfully.");
        } else {
            System.out.println("Print canceled.");
        }
    }

    // Log Print Action
    public void logPrintAction() {
        System.out.println("Log Entry: Printed Shrink Report with ID: " + reportID + " on " + generatedDate);
        // Here you could save the log entry to a database or a file
    }

    // Helper Methods (Stub Implementations)
    private List<String> queryDatabaseForShrinkage(Date startDate, Date endDate) {
        // Simulate querying the database for shrinkage data
        System.out.println("Querying database for shrinkage details between " + startDate + " and " + endDate + "...");
        List<String> details = new ArrayList<>();
        details.add("Item A: 5 units lost");
        details.add("Item B: 3 units damaged");
        details.add("Item C: 7 units stolen");
        return details;
    }

    private Date getCurrentDate() {
        return new Date(); // Returns the current date and time
    }

    private void sendToPrinter(List<String> details) {
        System.out.println("Sending report to printer...");
        for (String detail : details) {
            System.out.println("Printing: " + detail);
        }
    }

    private String generateUniqueReportID() {
        // Simulates the creation of a unique report ID
        return "SR" + System.currentTimeMillis();
    }
}
