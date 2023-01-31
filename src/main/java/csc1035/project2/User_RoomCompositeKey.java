package csc1035.project2;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * This Class acts as the composite key for the User_Room Connection
 */
@Embeddable
public class User_RoomCompositeKey implements Serializable, IUser_RoomCompositeKey {
    private double roomNumber;
    private String id;
    private Date dateTimeOfBooking;

    public User_RoomCompositeKey() {
    }

    public User_RoomCompositeKey(double roomNumber, String id, Date dateTimeOfBooking) {
        this.roomNumber = roomNumber;
        this.id = id;
        this.dateTimeOfBooking = dateTimeOfBooking;
    }

    public double getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(double roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTimeOfBooking() {
        return dateTimeOfBooking;
    }

    public void setDateTimeOfBooking(Date dateTimeOfBooking) {
        this.dateTimeOfBooking = dateTimeOfBooking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User_RoomCompositeKey that = (User_RoomCompositeKey) o;
        return Double.compare(that.roomNumber, roomNumber) == 0 && Objects.equals(id, that.id) && Objects.equals(dateTimeOfBooking, that.dateTimeOfBooking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, id, dateTimeOfBooking);
    }

    @Override
    public String toString() {
        return "user_RoomCompositeKey{" +
                "roomNumber=" + roomNumber +
                ", id='" + id + '\'' +
                ", dateTime_of_booking=" + dateTimeOfBooking +
                '}';
    }

}
