package models;

import java.util.Objects;

public class User {
    private  int id;
    private  String name;
    private String position;
    private String staff_role;


    public User(String name, String position, String staff_role) {
        this.name = name;
        this.position = position;
        this.staff_role = staff_role;

    }

    public String getName() {
        return  name;
    }

    public String getPosition() {
        return position;
    }

    public String getStaff_role() {
        return staff_role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(position, user.position) && Objects.equals(staff_role, user.staff_role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, staff_role);
    }
}
