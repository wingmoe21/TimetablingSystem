package csc1035.project2;

import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;

/**
 * This class tests the relationship  and methods of User_Room.java
 *
 * @author Muhannad Aldossary
 * @author Harry Westmoreland
 */
public class User_RoomTest {

    public static void main(String[] args) {
        testCancelReservation();
    }

    private static void testRelationship(){
        //begins a transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        //reads a user and room from the db
        User u = session.get(User.class, "NUC5633757");
        rooms r = session.get(rooms.class, 4.55);
        //creates the relationship with the read user and room as well as a length of booking
        User_Room sr = new User_Room();
        sr.setRoom(r);
        sr.setUser(u);
        sr.setLengthOfBooking(100);

        //creates the composite key and embeds it into the relationship table
        User_RoomCompositeKey id = new User_RoomCompositeKey();
        Date testDate = new Date(30, Calendar.APRIL, 11, 11, 11, 11);
        id.setDateTimeOfBooking(testDate);
        sr.setId(id);

        //add the relation the the objects sand then persist the relation and close the connection
        u.getBookedRooms().add(sr);
        r.getBookedRooms().add(sr);
        session.persist(sr);
        session.getTransaction().commit();
        session.close();
    }

    private static void testBookingConfirmation(){
        //begins a hibernate session's transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //reads a User_Room relationship from the table
        Date testDate = new Date(30, Calendar.APRIL, 11, 11, 11, 11);
        User_RoomCompositeKey urck = new User_RoomCompositeKey(4.55,"NUC5633757",testDate);
        User_Room ur = session.get(User_Room.class,urck);

        System.out.println(ur.generateBookingConfirmation());
    }

    private static void testReserveRoom(){
        //create an instance of User_Room to activate the method
        User_Room ur = new User_Room();
        ur.reserveRoom();
    }

    private static void testAvailableRooms(){
        //create an instance of User_Room to activate the method
        User_Room ur = new User_Room();
        ur.availableRooms();
    }
    private static void testCancelReservation(){
        //create an instance of User_Room to activate the method
        User_Room ur = new User_Room();
        ur.availableRooms();
    }
}