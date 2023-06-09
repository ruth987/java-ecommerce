package model;

public class Admin {
    private static Admin instance;

    private int id;
    private String username;
    private String password;
    private String email;
    private String phone_number;

    Admin() {
    }

    public static Admin getInstance() {
        if (instance == null) {
            synchronized (Admin.class) {
                if (instance == null) {
                    instance = new Admin();
                }
            }
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
