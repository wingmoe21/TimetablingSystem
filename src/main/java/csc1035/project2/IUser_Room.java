package csc1035.project2;

/**
 * This is the Interface foe the User.java class.
 *
 * @author Harry Westmoreland
 */
public interface IUser_Room {

    public User_RoomCompositeKey getId();

    public void setId(User_RoomCompositeKey id);

    public User getUser();

    public void setUser(User user);

    public rooms getRoom();

    public void setRoom(rooms room);

    public Integer getLengthOfBooking();

    public void setLengthOfBooking(Integer length_of_booking);

    /**
     * This method creates a String representation of a Booking Confirmation
     * @return a string which contains the room booking confirmation.
     */
    public String generateBookingConfirmation();

    /**
     * This method create a reservation for a user and add it to the database.
     */
    public void reserveRoom();

    /**
     * Ths method show all available rooms within a given datetime and duration.
     */
    public void availableRooms();

    /**
     * This method delete a reservation from the database.
     */
    public void cancelReservation();
}
