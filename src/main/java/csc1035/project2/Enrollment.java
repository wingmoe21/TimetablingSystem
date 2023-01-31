package csc1035.project2;

import javax.persistence.*;

@Entity(name = "Enrollment")
/**
 * This is a special class for representing the Enrollment table.
 * This has methods relating to the attributes and some of its behaviour
 *
 * @Author Damon Brooker
 */
public class Enrollment implements IEnrollment{
    @Id
    @Column(name = "enrollmentID")
    private String enrollmentID;

    @Column(name = "id")
    private String userID;

    @Column(name = "moduleID")
    private String moduleID;

    public Enrollment(String enrollmentID, String userID, String moduleID) {
        this.enrollmentID = enrollmentID;
        this.userID = userID;
        this.moduleID = moduleID;
    }

    public Enrollment(){}

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }
}
