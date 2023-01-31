package csc1035.project2;

import javax.persistence.*;


@Entity(name = "Modules")
/**
 *This is a special class for representing the modules table.
 *This has methods relating to the attributes and some of its behaviour
 *
 * @author Damon Brooker
 */
public class Modules implements IModules{
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private int credits;

    @Column(name = "weeks")
    private int weeks;

    public Modules(){}

    public Modules(String id, String name, int credits, int weeks) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.weeks = weeks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }
}
