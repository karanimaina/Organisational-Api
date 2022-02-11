package models;

public class User {
    private  int id;
    private String name;
    private String position;
    private String role;

    public User(String name, String position, String role) {
        this.name = name;
        this.position = position;
        this.role = role;
    }

    public String getName() {
        return  name;
    }

    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }
}
