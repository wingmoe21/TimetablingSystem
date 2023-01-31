package csc1035.project2;


import org.hibernate.Session;

/**
 * This class is used to test the rooms class and its methods.
 *
 * @author Harry Westmoreland
 * @author Muhannad Aldossary
 */
public class roomsTest {

    private static void testUpdateDetails(){
        //opens a session and begins a transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //reads a room from the database
        rooms r = session.get(rooms.class,2.484);
        session.getTransaction().commit();
        session.close();
        //updates the roomDetails
        r.updateDetails(2.383,"Meeting Room",32,12);
    }

    private static void testTimeTable(){
        //opens a session and begins a transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //reads a room from the database
        rooms r = session.get(rooms.class,4.55);
        session.getTransaction().commit();
        session.close();

        System.out.println(r.generateTimetable(4.55));
    }
    private static void testListRoom(){
        //create an instance of a room to activate the method
        rooms r = new rooms();
        r.roomsList();
    }
    private static void testPopulateDBwCSV(){
        rooms.populateDBwCSV();
    }

    private static void testAddRoomToDB(){
        //create an instance of a room to add it to the Database
        rooms r = new rooms(1.1,"PC",10,5);
        r.addRoomToDB();
}

    private static void testDeleteRoom(){
        //create an instance of a room to activate the method
        rooms r = new rooms(1,"1",1,1);
        r.deleteRoom();
    }

}
