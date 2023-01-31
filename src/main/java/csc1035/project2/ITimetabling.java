package csc1035.project2;

/**
 * This is the interface for the Timetabling.java class
 *
 * @author Harry Westmoreland, Damon Brooker
 */
public interface ITimetabling {

    /**
     * This method is responsible for outputting all of the students taking an inputted module id
     * by querying the database
     *
     * @param inputModuleID The inputted module id from the user
     */
    void listStudentsModule(String inputModuleID);

    /**
     * This method is responsible for outputting all of the staff teaching an inputted module id
     * by querying the database
     * @param inputModuleID The inputted module id from the user
     */
    void listStaffModule(String inputModuleID);

    /**
     * This method is responsible for outputting all of the module requirements for an
     * inputted module id by querying the database
     * @param inputModuleID The inputted module id from the user
     */
    void listModuleRequirements(String inputModuleID);

    /**
     * This method is responsible for creating a timetable for the whole school and booking relevant rooms
     * @param SocialDistant Boolean input to decipher if the user is attempting to create a timetable
     *                      under socially distant conditions or not
     */
    void createTimetable(boolean SocialDistant);

    /**
     * This method is responsible for outputting all of the module requirements for an
     * inputted module id by querying the database
     * @param inputModuleID The inputted module id from the user
     */
    void listModuleRequirementsV2(String inputModuleID);

    /**
     * This method is responsible for creating a schedule for a single user,
     * with some additional information added
     *
     * @param userID The target user to create the timetable for
     */
    void createTimetableForPerson_V2(String userID);

    /**This method is after calling the method createtimetable forperson V2 (),
     * an optional method. If the user chooses to view the room details,
     * this method will be triggered, otherwise it will not be called
     *
     * @param RoomNumber
     */
    void ShowBookedRoomDetail(Double RoomNumber);

    /**This is a humanized function, which is used to display the detailed information of the module.
     * After calling method requirement, you will choose whether to call it or not according to the user's input.
     *
     * @param inputModuleID
     */
    void ShowModulesDetail(String inputModuleID);


}
