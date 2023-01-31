package csc1035.project2;

import java.util.*;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * This is a class for representing a rooms table with its methods.
 *
 * This has constructors, getter, setter, and toString methods for generating the table.
 *
 * This has six methods (roomList(), populateDBwCSC(), addRoomToDB(), deleteRoom(),
 *  updateDetails(), generateTimetable) for storing, retrieving, and manipulate information about
 *  rooms.
 *
 * @author Muhannad Aldossary
 * @author Harry Westmoreland
 */
@Entity
@Table(name = "Rooms")
public class rooms implements Irooms {
    @Id
    @Column
    private double roomNumber;
    @Column
    private String type;
    @Column
    private int maxCapacity;

    @Column
    private int socialDistancingCapacity;
    @OneToMany(mappedBy = "room")
    private List<User_Room> bookedRooms = new ArrayList();

    public rooms() {
    }

    public rooms(double room_number, String type, int max_capacity, int socialDistancingCapacity) {
        this.roomNumber = room_number;
        this.type = type;
        this.maxCapacity = max_capacity;
        this.socialDistancingCapacity = socialDistancingCapacity;
    }

    public double getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(double roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setMaxCapacity(int max_capacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSocialDistancingCapacity() {
        return this.socialDistancingCapacity;
    }

    public void setSocialDistancingCapacity(int socialDistancingCapacity) {
        this.socialDistancingCapacity = socialDistancingCapacity;
    }

    public List<User_Room> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(List<User_Room> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    @Override
    public String toString() {
        return "rooms{" +
                "roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", socialDistancingCapacity=" + socialDistancingCapacity +
                '}';
    }

    /**
     * This method returns a list of all rooms in the database
     * @return all rooms in the database
     */
    public void roomsList() {
        Session Session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM rooms";
        Session.beginTransaction();
        Query query = Session.createQuery(hql);
        List<rooms> results = query.list();
        Session.getTransaction().commit();
        for (rooms room: results) {
            System.out.println("-----------------------------------------");
            System.out.println(room);
        }
    }

    /**
     * This method populates the Database with the test data within the rooms.csv file
     */
    public static void populateDBwCSV() {
        Session session = null;
        List<rooms> roomCSV = new ArrayList();
        Scanner scanner = new Scanner(rooms.class.getClassLoader().getResourceAsStream("rooms.csv"));
        scanner.nextLine();

        while(scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",");
            roomCSV.add(new rooms(Double.parseDouble(line[0]), line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3])));
        }

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Iterator var8 = roomCSV.iterator();

            while(var8.hasNext()) {
                rooms room = (rooms)var8.next();
                session.save(room);
            }

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }


    /**
     *This method adds a room the the database
     */
    public void addRoomToDB() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method deletes a room object from the database
     */
    public void deleteRoom() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(session.get(rooms.class, this.roomNumber));
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method updates the room object and saves it to the database
     * @param roomNumber the roomNumber of the object you want to update
     * @param type the type you want to change the objects type to
     * @param maxCapacity the max_capacity you want to change the objects max_capacity to
     * @param socialDistancingCapacity the social_distancing_capacity you want to change the
     *                                   objects social_distancing_capacity to
     */
    public void updateDetails(double roomNumber, String type, int maxCapacity,int socialDistancingCapacity){
        Session session = HibernateUtil.getSessionFactory().openSession();

        //reads a room from the database with the same roomNumber
        rooms roomUpdate = session.get(rooms.class,roomNumber);

        //changes its properties
        roomUpdate.setType(type);
        roomUpdate.setMaxCapacity(maxCapacity);
        roomUpdate.setSocialDistancingCapacity(socialDistancingCapacity);

        //saves it back into the database
        session.beginTransaction();
        session.saveOrUpdate(roomUpdate);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method produces a timetable for the room instance
     * @return a string representation of the timetable for the room
     */
    public String generateTimetable(double roomNumber){
        //begins a session transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //get all from User_Room where the room number matches this room object's
        String hql = "FROM User_Room WHERE room_roomNumber = " + roomNumber;

        //performs the query
        Query query = session.createQuery(hql);
        List<User_Room> results = query.list();

        //Creates a timetable String for the room
        String timetable = "";
        for (User_Room ur : results){
            timetable += "---------------------" + "\n" +
                         "Booked By: " + ur.getUser().getId() + "\n" +
                         "On: " + ur.getId().getDateTimeOfBooking() + "\n" +
                         "For: " + ur.getLengthOfBooking() + " Hours" + "\n";
        }
        timetable += "---------------------";

        return timetable;
    }
}
