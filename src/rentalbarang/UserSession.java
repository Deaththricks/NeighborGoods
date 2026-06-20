package rentalbarang;

public class UserSession {
    // 1. Static variables (The fields printed on our memory ID card)
    private static int userId;
    private static String username;
    private static String email;
    private static String password;
    private static String phone;
    private static String address;
    // 2. The method called by the Login frame to fill out the card details
    public static void startSession(int id, String user, String mail, String pass, String phoneNum, String addr) {
        userId = id;
        username = user;
        email = mail;
        password = pass;
        phone = phoneNum;
        address = addr;
    }

    // 3. Getter methods so other screens can read who is logged in
    public static int getUserId() { 
        return userId; 
    }
    public static String getUsername() { 
        return username; 
    }
    public static String getEmail() { 
        return email; 
    }
    public static String getPass(){
        return password;
    }
    public static String getPhone(){
        return phone;
    }
    public static String getAdress(){
        return address;
    }
    // 4. Clear the memory card when logging out
    public static void clearSession() {
        userId = 0;
        username = null;
        email = null;
    }
}