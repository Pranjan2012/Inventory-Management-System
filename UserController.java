//public class UserController {
//
//    /**
//     * Authenticates a user based on username and password.
//     *
//     * @param username the username entered
//     * @param password the password entered
//     * @return User object if authentication succeeds, otherwise null
//     */
//    public User authenticate(String username, String password) {
//
//        // Hardcoded admin credentials (placeholder logic)
//        if ("admin".equalsIgnoreCase(username) && "password".equals(password)) {
//            return new User(username, "Owner/General Manager");
//        }
//
//        return null; // authentication failed
//    }
//}


import java.io.*;
import java.util.HashMap;

public class UserController {

    private HashMap<String, User> users = new HashMap<>();
    private static final String USER_FILE = "users.dat";

    public UserController() {
        loadUsers();
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void addUser(String username, String password, String role) {
        users.put(username, new User(username, password, role));
        saveUsers();
    }

    private void loadUsers() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(USER_FILE))) {
            users = (HashMap<String, User>) ois.readObject();
        } catch (Exception e) {
            // Create default admin if no file exists
            users.put("admin", new User("admin", "password", "Owner/General Manager"));
            saveUsers();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }
}
