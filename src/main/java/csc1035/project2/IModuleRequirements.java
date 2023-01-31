package csc1035.project2;

/**
 * This is the interface for the ModuleRequirements.java Class
 *
 * @author Harry Westmoreland
 */
public interface IModuleRequirements {

    public String getModuleID();

    public void setModuleID(String moduleID);

    public String getWeekCommencing();

    public void setWeekCommencing(String weekCommencing);

    public int getNoOfLecturesPerWeek();

    public void setNoOfLecturesPerWeek(int noOfLecturesPerWeek);

    public int getLectureLength();

    public void setLectureLength(int lectureLength);

    public int getNoOfPracticalsPerWeek();

    public void setNoOfPracticalsPerWeek(int noOfPracticalsPerWeek);

    public int getPracticalLength();

    public void setPracticalLength(int practicalLength);
}
