package model;
public class User {
    private int userId;

    private static User instance;
    private String username;
    private String password;
    private String email;

    public User() {

    }

    public User(int i, String s, String s1) {

    }


    public static User getInstance() {
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User();
                }
            }
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }

    public String getUserName() {
        return username;
    }
}