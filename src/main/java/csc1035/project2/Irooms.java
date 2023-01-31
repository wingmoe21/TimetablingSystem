package csc1035.project2;

import java.util.List;

/**
 * This is the Interface for the rooms.java Class
 *
 * @author Harry Westmoreland
 */
public interface Irooms {

    public double getRoomNumber();

    public void setRoomNumber(double roomNumber);

    public String getType();

    public void setType(String type);

    public int getMaxCapacity();

    public void setMaxCapacity(int maxCapacity);

    public int getSocialDistancingCapacity();

    public void setSocialDistancingCapacity(int socialDistancingCapacity);

    /**
     * This method updates the room object and saves it to the database
     * @param roomNumber the roomNumber of the object you want to update
     * @param type the type you want to change the objects type to
     * @param maxCapacity the max_capacity you want to change the objects max_capacity to
     * @param socialDistancingCapacity the social_distancing_capacity you want to change the
     *                                   objects social_distancing_capacity to
     */
    public void updateDetails(double roomNumber, String type, int maxCapacity,int socialDistancingCapacity);

    /**
     * This method produces a timetable for the room instance
     * @return a string representation of the timetable for the room
     */
    public String generateTimetable(double roomNumber);

    /**
     *This method adds the room the the database
     */
    public void addRoomToDB();

    /**
     * This method deletes a room object from the database
     */
    public void deleteRoom();

    /**
     * This method returns a list of all rooms in the database
     * @return all rooms in the database
     */
    public void roomsList();
}
