/**
 * Name(s) of coder:Punjan Subedi
 * Date created: 11/01/2024
 * Date last changed: 12/01/2024
 * Name of the code artifact: CycleCountClass
 * Description:
 * This class represents a cycle count entry, including details such as 
 * the cycle difference, counting user, date changed, and the counted SKU.
 */
public class CycleCountClass {
    private int cycleDifference;
    private String countingUser;
    private String dateChanged;
    private final int countedSku;

    // Constructor
    public CycleCountClass(int cycleDifference, String countingUser, String dateChanged, int countedSku) {
        this.cycleDifference = cycleDifference;
        this.countingUser = countingUser;
        this.dateChanged = dateChanged;
        this.countedSku = countedSku;
    }

    // Override toString for meaningful output
    @Override
    public String toString() {
        return "Cycle Difference: " + cycleDifference +
               "\nCounting User: " + countingUser +
               "\nDate Changed: " + dateChanged +
               "\nCounted SKU: " + countedSku;
    }

    // Getter and setter for cycle count's difference
    public int getCycleDifference() {
        return this.cycleDifference;
    }

    public void setCycleDifference(int newCycleDifference) {
        this.cycleDifference = newCycleDifference;
    }

    // Getter and setter for the counting user
    public String getCountingUser() {
        return this.countingUser;
    }

    public void setCountingUser(String newCountingUser) {
        this.countingUser = newCountingUser;
    }

    // Getter and setter for the date changed
    public String getDateChanged() {
        return this.dateChanged;
    }

    public void setDateChanged(String newDateChanged) {
        this.dateChanged = newDateChanged;
    }

    // Getter for counted SKU (no setter since it is final)
    public int getCountedSku() {
        return this.countedSku;
    }

    // Method to simulate running a cycle count
    public void runCycleCount() {
        System.out.println("Running cycle count...");
        // Add logic for cycle count processing if necessary
    }
}
