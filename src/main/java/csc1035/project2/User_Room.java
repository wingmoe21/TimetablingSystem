package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.*;

/**
 * This object acts as a table of reservations. It is the join table between User and Rooms
 * to model the many to many connection with composite key containing additional attributes between them.
 *
 * It has method making reservations or canceling them and showing the available room in a
 * given datetime and duration.
 *
 * @author Harry Westmoreland
 * @author Muhannad Aldossary
 */
@Entity
public class User_Room implements IUser_Room{
    @EmbeddedId
    public User_RoomCompositeKey id = new User_RoomCompositeKey();
    @ManyToOne
    @MapsId("id")
    private User user;
    @ManyToOne
    @MapsId("roomNumber")
    private rooms room;
    @Column
    private Integer lengthOfBooking;

    public User_Room() {
    }

    public User_Room(User_RoomCompositeKey id, User user, rooms room, Integer length_of_booking) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.lengthOfBooking = length_of_booking;
    }

    public User_RoomCompositeKey getId() {
        return id;
    }

    public void setId(User_RoomCompositeKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public rooms getRoom() {
        return room;
    }

    public void setRoom(rooms room) {
        this.room = room;
    }

    public Integer getLengthOfBooking() {
        return lengthOfBooking;
    }

    public void setLengthOfBooking(Integer length_of_booking) {
        this.lengthOfBooking = length_of_booking;
    }


    /**
     * This method creates a String representation of a Booking Confirmation
     * @return a string which contains the room booking confirmation.
     */
    public String generateBookingConfirmation() {

        String roomReservationDateTime = this.id.getDateTimeOfBooking().toString();

        String roomReservationLength = this.lengthOfBooking.toString();
        return "--------------------------------- \n" +
                "You have successfully booked \n" +
                "Room: " + this.room.getRoomNumber() + "\n" +
                "On Date: " + roomReservationDateTime + "\n" +
                "For: " + roomReservationLength + " Hours\n" +
                "---------------------------------";
    }

    @Override
    public String toString() {
        return "User_Room{" +
                "id=" + id +
                ", length_of_booking=" + lengthOfBooking +
                '}';
    }

    /**
     * This method represent a list of all months with number associated with each one.
     * @return a string list of months.
     */
    public static String list_of_months() {
        return "--------------------------------- \n" +
                "Choose the number of the month: \n" +
                "0: January \n" +
                "1: February \n" +
                "2: March \n" +
                "3: April \n" +
                "4: May \n" +
                "5: June \n" +
                "6: July \n" +
                "7: August \n" +
                "8: September \n" +
                "9: October \n" +
                "10: November \n" +
                "11: December \n" +
                "---------------------------------";

    }

    /**
     * This method checks if two dates overlap or not.
     * @param date1Start the starting Date of a booking
     * @param date1Length the length of a booking
     * @param date2Start the starting Date of another booking
     * @param date2Length the length of another booking
     * @return returns true if the dates overlap
     */
    public static boolean overlapsBooking(Date date1Start, int date1Length, Date date2Start, int date2Length) {

        Date date1End = new Date(date1Start.getYear(), date1Start.getMonth(), date1Start.getDate(),
                date1Start.getHours() + date1Length, date1Start.getMinutes(),
                date1Start.getSeconds());

        Date date2End = new Date(date2Start.getYear(), date2Start.getMonth(), date2Start.getDate(),
                date2Start.getHours() + date2Length, date2Start.getMinutes(),
                date2Start.getSeconds());

        if (date1Start.before(date2End) && date1End.after(date2Start)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method create a reservation for a user and add it to the database.
     */
    public void reserveRoom() {
        Session Session = HibernateUtil.getSessionFactory().openSession();
        String hql1 = "SELECT roomNumber FROM rooms";
        String hql2 = "SELECT id FROM User";
        String hql3 = "FROM rooms";
        Session.beginTransaction();
        Query query1 = Session.createQuery(hql1);
        Query query2 = Session.createQuery(hql2);
        Query query3 = Session.createQuery(hql3);
        List<rooms> list_of_room_numbers = query1.list();
        List<rooms> list_of_ids = query2.list();
        List<rooms> list_of_rooms = query3.list();

        boolean i = true;
        while (i) {
            for (rooms room: list_of_rooms) {
                System.out.println("--------------------------------------------------");
                System.out.println(room);
                System.out.println("--------------------------------------------------");
            }
            System.out.println("Choose a room number:");
            Scanner sc = new Scanner(System.in);
            double roomNum1 = sc.nextDouble();

            if (list_of_room_numbers.contains(roomNum1)) {
                boolean ii = true;
                while (ii) {
                    System.out.println("Enter your ID number:");
                    String id = sc.next();
                    if (list_of_ids.contains(id)) {
                        System.out.println("Duration: (Hours)");
                        int hours = sc.nextInt();
                        User u = Session.get(User.class, id);
                        rooms r = Session.get(rooms.class, roomNum1);
                        //creates the relationship with the read user and room as well as a length of booking
                        User_Room bookedRoom = new User_Room();
                        bookedRoom.setRoom(r);
                        bookedRoom.setUser(u);
                        bookedRoom.setLengthOfBooking(hours);
                        //creates the composite key and embeds it into the relationship table
                        User_RoomCompositeKey compKey = new User_RoomCompositeKey();
                        System.out.println("Enter the date..");
                        System.out.println("--------------------------------------------------");
                        System.out.println("Year:");
                        int year = sc.nextInt();
                        System.out.println(list_of_months());
                        int month = sc.nextInt();
                        System.out.println("day: (1-31)");
                        int day = sc.nextInt();
                        System.out.println("hour: (0-23)");
                        int hour = sc.nextInt();
                        Date date = new Date(year - 1900, month, day, hour, 00, 00);
                        compKey.setDateTimeOfBooking(date);
                        bookedRoom.setId(compKey);
                        Session.getTransaction();
                        String hql = "FROM User_Room WHERE room_roomNumber = " + roomNum1;
                        Query query = Session.createQuery(hql);
                        List<User_Room> results = query.list();
                        List<Boolean> overlapsBookingList  = new ArrayList<>();
                        for (User_Room ur : results) {
                            overlapsBookingList.add(overlapsBooking(ur.id.getDateTimeOfBooking(), ur.getLengthOfBooking(),date, hours ));
                        }
                        //add the relation the the objects sand then persist the relation and close the connection
                        u.getBookedRooms().add(bookedRoom);
                        r.getBookedRooms().add(bookedRoom);
                        if(overlapsBookingList.contains(true)){
                            System.out.println("Your booking conflicts with a reservation");
                            System.out.println("Choose another room or date and time");
                            System.out.println("--------------------------------------------------");
                        }else{
                            Session.persist(bookedRoom);
                            Session.getTransaction().commit();
                            Session.close();
                            System.out.println(bookedRoom.generateBookingConfirmation());
                            i = false;
                            ii = false;
                        }
                    } else {
                        System.out.println("ERROR!!");
                        System.out.println("You entered a not valid ID number");
                        System.out.println("--------------------------------------------------");
                        System.out.println("Try Again...");

                    }
                }

            } else {
                System.out.println("ERROR!!");
                System.out.println("You entered a not valid room number");
                System.out.println("--------------------------------------------------");
                System.out.println("Try Again...");
            }
        }
    }

    /**
     * Ths method show all available rooms within a given datetime and duration.
     */
    public void availableRooms(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the date.");
        System.out.println("--------------------------------------------------");
        System.out.println("Duration: (Hours)");
        int hours = sc.nextInt();
        System.out.println("Year:");
        int year = sc.nextInt();
        System.out.println(list_of_months());
        int month = sc.nextInt();
        System.out.println("day: (1-31)");
        int day = sc.nextInt();
        System.out.println("hour: (0-23)");
        int hour = sc.nextInt();
        Date date = new Date(year - 1900, month, day, hour, 00, 00);
        Session Session = HibernateUtil.getSessionFactory().openSession();
        Session.beginTransaction();
        String hql = "FROM User_Room";
        String hql1 = "SELECT roomNumber FROM rooms";
        Query query = Session.createQuery(hql);
        Query query1 = Session.createQuery(hql1);
        List<User_Room> list_of_reservations= query.list();
        List<Double> list_of_roomNumbers = query1.list();
        List<Double> overlapsBookingList  = new ArrayList<>();
        for (User_Room ur : list_of_reservations) {
            boolean a = overlapsBooking(ur.id.getDateTimeOfBooking(), ur.getLengthOfBooking(),date, hours);
            if(a == true){
                overlapsBookingList.add(ur.id.getRoomNumber());
            }
        }
        list_of_roomNumbers.removeAll(overlapsBookingList);
        List rooms = new ArrayList<>();
        for (double room: list_of_roomNumbers) {
            String hql2 = "FROM rooms WHERE roomNumber = " + room;
            Query query2 = Session.createQuery(hql2);
             rooms.add(query2.list().get(0));

        }

        for (Object room:rooms) {
            System.out.println("--------------------------------------------------");
            System.out.println(room);
        }
    }

    /**
     * This method delete a reservation from the database.
     */
    public void cancelReservation() {
        Session Session = HibernateUtil.getSessionFactory().openSession();
        Session.beginTransaction();
        String hql = "SELECT room FROM User_Room";
        Query query = Session.createQuery(hql);
        List<rooms> list_of_reserved_rooms = query.list();
        List<Double> reservedRooms = new ArrayList<>();
        for (rooms room : list_of_reserved_rooms) {
            reservedRooms.add(room.getRoomNumber());
        }
        System.out.println("Choose the room number:");
        Scanner sc = new Scanner(System.in);
        double roomNum1 = sc.nextDouble();
        if (reservedRooms.contains(roomNum1)) {
            System.out.println("Enter your ID number:");
            String id = sc.next();
            System.out.println("year of the booking:");
            int year = sc.nextInt();
            System.out.println(list_of_months());
            int month = sc.nextInt();
            System.out.println("day of the booking: (1-31)");
            int day = sc.nextInt();
            System.out.println("hour of the booking: (0-23)");
            int hour = sc.nextInt();
            Date date = new Date(year - 1900, month, day, hour, 00, 00);
            User_RoomCompositeKey compKey = new User_RoomCompositeKey(roomNum1, id, date);
            User_Room recordToBeDeleted = (User_Room) Session.load(User_Room.class, compKey);
            Session.delete(recordToBeDeleted);
            Session.getTransaction().commit();
            Session.close();
        } else {
            System.out.println("This room has no booking record");
        }

    }

}

























