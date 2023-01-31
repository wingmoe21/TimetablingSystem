package csc1035.project2;

import java.util.Date;
/**
 * This is the Interface for the User_RoomCompositeKey.java class.
 */
public interface IUser_RoomCompositeKey {
    public double getRoomNumber();

    public void setRoomNumber(double roomNumber);

    public String getId();

    public void setId(String id);

    public Date getDateTimeOfBooking();

    public void setDateTimeOfBooking(Date dateTimeOfBooking);
}
