/*
 * Name(s) of coder: Punjan Subedi
 * Date created: 11/26/2024
 * Date last changed: 01/04/2026
 * Name of the code artifact: User Class
 *
 * Description:
 * Represents a system user with authentication credentials and role-based permissions.
 * FIXED:
 * - Implements Serializable (required for file persistence)
 * - Added serialVersionUID
 */

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
    private String username;
    private String password;
    private String role; // "Employee", "Middle Management", "Owner/General Manager"

    // --------------------
    // Constructors
    // --------------------

    // Constructor with username and role
    public User(String username, String role) {
        this.username = username;
        this.role = role;
        this.password = "";
    }

    // Constructor with username, password, and role
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        setRole(role); // validate role
    }

    // Default constructor
    public User() {
        this.username = "";
        this.password = "";
        this.role = "";
    }

    // --------------------
    // Getters & Setters
    // --------------------

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    // NOTE: In real systems passwords should be hashed
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role.equals("Employee")
                || role.equals("Middle Management")
                || role.equals("Owner/General Manager")) {
            this.role = role;
        } else {
            System.out.println(
                    "Invalid role. Role must be 'Employee', 'Middle Management', or 'Owner/General Manager'."
            );
        }
    }

    // --------------------
    // Authentication
    // --------------------

    public boolean checkLogin(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // --------------------
    // Permission Checks
    // --------------------

    public boolean checkPermissionPerformCycleCount() {
        return true; // Allowed for all roles
    }

    public boolean checkPermissionAddItem() {
        return isManagement();
    }

    public boolean checkPermissionUpdateItem() {
        return isManagement();
    }

    public boolean checkPermissionRemoveItem() {
        return isManagement();
    }

    public boolean checkPermissionPrintShrinkReports() {
        return isManagement();
    }

    public boolean checkPermissionPrintLowQuantityReports() {
        return isManagement();
    }

    public boolean checkPermissionAddUser() {
        return isOwner();
    }

    public boolean checkPermissionUpdateUserPermission() {
        return isOwner();
    }

    public boolean checkPermissionRemoveUser() {
        return isOwner();
    }

    // --------------------
    // Helper Role Checks
    // --------------------

    private boolean isManagement() {
        return role.equals("Middle Management")
                || role.equals("Owner/General Manager");
    }

    private boolean isOwner() {
        return role.equals("Owner/General Manager");
    }
}
