package csc1035.project2;
import javax.persistence.*;


@Entity
@Table(name = "Module_Requirements")

/**
 *This is a special class for representing the module requirements table.
 *This has methods relating to the attributes and some of its behaviour
 *
 * @author Damon Brooker
 */
public class ModuleRequirements implements IModuleRequirements{
    @Id
    @Column(name = "Id")
    private String moduleID;

    @Column(name = "week_commencing")
    private String weekCommencing;

    @Column(name = "no_of_lectures_per_week")
    private int noOfLecturesPerWeek;

    @Column(name = "lecture_length")
    private int lectureLength;

    @Column(name = "no_of_practicals_per_week")
    private int noOfPracticalsPerWeek;

    @Column(name = "practical_length")
    private int practicalLength;

    public ModuleRequirements(){}

    public ModuleRequirements(String moduleID, String weekCommencing, int noOfLecturesPerWeek, int lectureLength, int noOfPracticalsPerWeek, int practicalLength) {
        this.moduleID = moduleID;
        this.weekCommencing = weekCommencing;
        this.noOfLecturesPerWeek = noOfLecturesPerWeek;
        this.lectureLength = lectureLength;
        this.noOfPracticalsPerWeek = noOfPracticalsPerWeek;
        this.practicalLength = practicalLength;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getWeekCommencing() {
        return weekCommencing;
    }

    public void setWeekCommencing(String weekCommencing) {
        this.weekCommencing = weekCommencing;
    }

    public int getNoOfLecturesPerWeek() {
        return noOfLecturesPerWeek;
    }

    public void setNoOfLecturesPerWeek(int noOfLecturesPerWeek) {
        this.noOfLecturesPerWeek = noOfLecturesPerWeek;
    }

    public int getLectureLength() {
        return lectureLength;
    }

    public void setLectureLength(int lectureLength) {
        this.lectureLength = lectureLength;
    }

    public int getNoOfPracticalsPerWeek() {
        return noOfPracticalsPerWeek;
    }

    public void setNoOfPracticalsPerWeek(int noOfPracticalsPerWeek) {
        this.noOfPracticalsPerWeek = noOfPracticalsPerWeek;
    }

    public int getPracticalLength() {
        return practicalLength;
    }

    public void setPracticalLength(int practicalLength) {
        this.practicalLength = practicalLength;
    }
}
