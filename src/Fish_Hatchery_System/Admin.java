package Fish_Hatchery_System;

public class Admin {
    private String name;                    
    private String username;                
    private String password;              

    public Admin(String name, String username, String password) {        
        this.name = name;                                                
        this.username = username;
        this.password = password;
    }

    public static Admin authenticate(String username, String password) {
        if ("aman".equals(username) && "a232".equals(password)) {
            return new Admin("Monowar Hossain Aman", "aman", "a232");
        }
        if ("shobuz".equals(username) && "s231".equals(password)) {
            return new Admin("Mahedi Hassan Shobuz", "shobuz", "s231");
        }
        return null;
    }
    
    public static void logout() {
        System.out.println("Successfully logged out!");
    }

    public String getName() {          
        return name;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {      
        return "Admin{" +
               "name='" + name + '\'' +
               ", username='" + username + '\'' +
               '}';
    }
}
