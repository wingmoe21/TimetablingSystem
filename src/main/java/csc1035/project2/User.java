package csc1035.project2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all students and Staff members as they share fields. (all staff Id's start with NUC acting as a way
 * to differentiate)
 */
@Entity
@Table(name = "Users")
public class User implements Iuser{
    @Column(name = "id")
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(mappedBy="user")
    private List<User_Room> bookedRooms = new ArrayList<>();

    public User() {
    }

    public User(String id, String first_name, String last_name) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<User_Room> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(List<User_Room> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }
}
