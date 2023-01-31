package csc1035.project2;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.persistence.*;

/**
 *This is a special class for representing the Timetabling object.
 *This has methods relating to the attributes and some of its behaviour
 *
 * @author Damon Brooker, Harry, Xan
 */
public class Timetabling implements ITimetabling{

    /**
     * This method is responsible for outputting all of the students taking an inputted module id
     * by querying the database
     *
     * @param inputModuleID The inputted module id from the user
     */
    public void listStudentsModule(String inputModuleID){

        Session Session = HibernateUtil.getSessionFactory().openSession();
        Object[] items;
        String hql = "SELECT firstName, lastName, id FROM User WHERE id IN " +
                "(SELECT userID FROM Enrollment WHERE (moduleID = '" + inputModuleID + "'))";
        Session.beginTransaction();
        Query query = Session.createQuery(hql);
        List<User> results = query.getResultList();
        Session.getTransaction().commit();
        items = results.toArray();


        for (int i = 0; i < items.length; i++){
            Object[] tmp = (Object[]) items[i];
            if(!tmp[2].toString().contains("NUC")){
                System.out.print(tmp[0] + " " + tmp[1]);
            }
            System.out.println();
        }

    }

    /**
     * This method is responsible for outputting all of the staff teaching an inputted module id
     * by querying the database
     * @param inputModuleID The inputted module id from the user
     */
    public void listStaffModule(String inputModuleID){
        Session Session = HibernateUtil.getSessionFactory().openSession();
        Object[] items;
        String hql = "SELECT firstName, lastName, id FROM User WHERE id IN " +
                "(SELECT userID FROM Enrollment WHERE (moduleID = '" + inputModuleID + "'))";
        Session.beginTransaction();
        Query query = Session.createQuery(hql);
        List<User> results = query.getResultList();
        Session.getTransaction().commit();
        items = results.toArray();


        for (int i = 0; i < items.length; i++){
            Object[] tmp = (Object[]) items[i];
            if(tmp[2].toString().contains("NUC")){
                System.out.print(tmp[0] + " " + tmp[1]);
            }
            System.out.println();
        }
    }

    /**
     * This method is responsible for outputting all of the module requirements for an
     * inputted module id by querying the database
     * @param inputModuleID The inputted module id from the user
     */
    public void listModuleRequirements(String inputModuleID){
        Session Session = HibernateUtil.getSessionFactory().openSession();
        Object[] items;
        String hql = "SELECT weekCommencing, noOfLecturesPerWeek, lectureLength, noOfPracticalsPerWeek, " +
                "practicalLength FROM ModuleRequirements WHERE moduleID = '" + inputModuleID + "'";
        Session.beginTransaction();
        Query query = Session.createQuery(hql);
        List<ModuleRequirements> results = query.getResultList();
        Session.getTransaction().commit();
        items = results.toArray();
        for (int i = 0; i < items.length; i++){
            Object[] tmp = (Object[]) items[i];
            System.out.println("Starting date: " + tmp[0] + " " + "Number of lectures per week: " + tmp[1] + " "
            + "Lecture length: " + tmp[2] + " " + "number of practicals per week: " + tmp[3] +
                    " " + "Practical length: " + tmp[4]);
            System.out.println();
        }
    }

    /**
     * This method is responsible for outputting all of the module requirements for an
     * inputted module id by querying the database
     * @param inputModuleID The inputted module id from the user
     */
    public void listModuleRequirementsV2(String inputModuleID){
        Session Session = HibernateUtil.getSessionFactory().openSession();
        Session.beginTransaction();

        String hql="from ModuleRequirements where moduleID=:moduleID";
        Query query = Session.createQuery(hql);
        ((org.hibernate.query.Query<?>) query).setString("moduleID",inputModuleID);

        List<ModuleRequirements> list= (List<ModuleRequirements>) ((org.hibernate.query.Query<?>) query).list();
        System.out.println("********************************************");
        for(ModuleRequirements moduleRequirements:list){
            System.out.println("Module ID:"+moduleRequirements.getModuleID());
            System.out.println("Starting Date:"+moduleRequirements.getWeekCommencing());
            System.out.println("Lectures per week:"+moduleRequirements.getNoOfLecturesPerWeek());
            System.out.println("Lecture length:"+moduleRequirements.getLectureLength());
            System.out.println("Number of practicals per week:"+moduleRequirements.getNoOfPracticalsPerWeek());
            System.out.println("Practical length:"+moduleRequirements.getPracticalLength());
            System.out.println("********************************************");
        }
        System.out.println("Query Finish");
        System.out.println("Do you want to get detail of Module?(Y/N)");
        Scanner sc=new Scanner(System.in);
        String choose=sc.nextLine();
        if(choose.equals("y")||choose.equals("Y")){
            System.out.println("Querying.......Please Wait");
            ShowModulesDetail(inputModuleID);
        }
    }

    /**
     * This method is responsible for creating a timetable for the whole school and booking relevant rooms
     * @param SocialDistant Boolean input to decipher if the user is attempting to create a timetable
     *                      under socially distant conditions or not
     */
    public void createTimetable(boolean SocialDistant) {
        ArrayList<String> RoomWithCapacity = new ArrayList<>();
        Date weekCommencing;
        int noOfLecturesPerWeek;
        int lectureLength;
        //For each module, look up module requirements book out computer suite
        Session Session = HibernateUtil.getSessionFactory().openSession();
        Object[] items;
        Object[] items2; // :-)
        String hql = "SELECT id from Modules";
        Session.beginTransaction();
        Query query = Session.createQuery(hql);
        List<String> results = query.getResultList();
        Session.getTransaction().commit();
        for (String s : results) {
            Session Session2 = HibernateUtil.getSessionFactory().openSession();
            hql = "SELECT weekCommencing, noOfLecturesPerWeek, lectureLength " +
                    " FROM ModuleRequirements WHERE moduleID = '" + s + "'";
            Session.beginTransaction();
            Query query2 = Session.createQuery(hql);
            List<String> results2 = query2.getResultList();
            Session.getTransaction().commit();
            items2 = results2.toArray();
            weekCommencing = (Date) items2[0];
            noOfLecturesPerWeek = (int) items2[1];
            lectureLength = (int) items2[2];

            Session SumPeople = HibernateUtil.getSessionFactory().openSession();
            SumPeople.beginTransaction();
            String hqlString = "SELECT COUNT(userID) FROM Enrollment WHERE moduleID = '" + s + "'";
            Query querySumPeople = SumPeople.createQuery(hqlString);
            int SumOfPeople = querySumPeople.getMaxResults();
            if (!SocialDistant) {
                for (int i = 0; i < noOfLecturesPerWeek; i++) {
                    //Select room with size less than sum of people once room selected book up slots
                    Session MaxCapacity = HibernateUtil.getSessionFactory().openSession();
                    MaxCapacity.beginTransaction();
                    String hqlRoomCapacity = "SELECT maxCapacity, roomNumber FROM rooms";
                    Query queryMaxCapacity = Session.createQuery(hqlRoomCapacity);
                    List<Object> MaxCapacities = queryMaxCapacity.getResultList();
                    Object[] MaxCapacitiesObject = MaxCapacities.toArray();
                    for (int counter = 0; counter < MaxCapacitiesObject.length; counter++) {
                        Object[] tmp = (Object[]) MaxCapacitiesObject[counter];
                        if (Integer.valueOf((Integer) tmp[0]) <= SumOfPeople) {
                            RoomWithCapacity.add(tmp[1].toString());
                        }
                    }
                    for(int counter2 = 0; counter2 < RoomWithCapacity.size(); counter2++) {
                        User u = Session.get(User.class, "000000001");
                        rooms r = Session.get(rooms.class, RoomWithCapacity.get(counter2));
                        int hours = lectureLength;
                        User_Room bookedRoom = new User_Room();
                        bookedRoom.setRoom(r);
                        bookedRoom.setUser(u);
                        bookedRoom.setLengthOfBooking(hours);
                        User_RoomCompositeKey compKey = new User_RoomCompositeKey();
                        Date date = weekCommencing;
                        compKey.setDateTimeOfBooking(date);
                        bookedRoom.setId(compKey);
                        Session.getTransaction();
                        u.getBookedRooms().add(bookedRoom);
                        r.getBookedRooms().add(bookedRoom);
                        String hql3 = "FROM User_Room WHERE room_roomNumber = " + RoomWithCapacity.get(counter2);
                        org.hibernate.query.Query query3 = Session.createQuery(hql);
                        List<User_Room> results3 = query3.getResultList();
                        List<Boolean> overlapsBookingList = new ArrayList<>();
                        for (User_Room ur : results3) {
                            overlapsBookingList.add(ur.overlapsBooking(ur.id.getDateTimeOfBooking(), ur.getLengthOfBooking(), date, hours));
                        }
                        if(overlapsBookingList.contains(false)) {
                            Session.persist(bookedRoom);
                            Session.getTransaction().commit();
                            Session.close();
                            break;
                        }
                    }
                }

            } else if (SocialDistant) {
                for (int i = 0; i < noOfLecturesPerWeek; i++) {
                    Session MaxCapacity = HibernateUtil.getSessionFactory().openSession();
                    MaxCapacity.beginTransaction();
                    String hqlRoomCapacity = "SELECT socialDistancingCapacity, roomNumber FROM rooms";
                    Query queryMaxCapacity = Session.createQuery(hqlRoomCapacity);
                    List<Object> MaxCapacities = queryMaxCapacity.getResultList();
                    Object[] MaxCapacitiesObject = MaxCapacities.toArray();
                    for (int counter = 0; counter < MaxCapacitiesObject.length; counter++) {
                        Object[] tmp = (Object[]) MaxCapacitiesObject[counter];
                        if (Integer.valueOf((Integer) tmp[0]) <= SumOfPeople) {
                            RoomWithCapacity.add(tmp[1].toString());
                        }
                    }
                    for (int counter2 = 0; counter2 < RoomWithCapacity.size(); counter2++) {
                        User u = Session.get(User.class, "000000001");
                        rooms r = Session.get(rooms.class, RoomWithCapacity.get(counter2));
                        int hours = lectureLength;
                        User_Room bookedRoom = new User_Room();
                        bookedRoom.setRoom(r);
                        bookedRoom.setUser(u);
                        bookedRoom.setLengthOfBooking(hours);
                        User_RoomCompositeKey compKey = new User_RoomCompositeKey();
                        Date date = weekCommencing;
                        compKey.setDateTimeOfBooking(date);
                        bookedRoom.setId(compKey);
                        Session.getTransaction();
                        u.getBookedRooms().add(bookedRoom);
                        r.getBookedRooms().add(bookedRoom);
                        String hql3 = "FROM User_Room WHERE room_roomNumber = " + RoomWithCapacity.get(counter2);
                        org.hibernate.query.Query query3 = Session.createQuery(hql);
                        List<User_Room> results3 = query3.getResultList();
                        List<Boolean> overlapsBookingList = new ArrayList<>();
                        for (User_Room ur : results3) {
                            overlapsBookingList.add(ur.overlapsBooking(ur.id.getDateTimeOfBooking(), ur.getLengthOfBooking(), date, hours));
                        }
                        if (overlapsBookingList.contains(false)) {
                            Session.persist(bookedRoom);
                            Session.getTransaction().commit();
                            Session.close();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is responsible for creating a timetable for a single user
     *
     * @param userID The target user to create the timetable for
     */
    public void createTimetableForPerson(String userID){
        Session Session = HibernateUtil.getSessionFactory().openSession();
        Session.beginTransaction();
        String hql = "FROM User_Room WHERE user = " + userID;
        Query query = Session.createQuery(hql);
        List<User_Room> results = query.getResultList();
        Object[] items;
        items = results.toArray();

        for (User_Room ur : results){
            System.out.println("---------------------" + "\n" +
                    "Booked By: " + ur.getUser().getId() + "\n" +
                    "Date and time of booking: " + ur.getId().getDateTimeOfBooking() + "\n" +
                    "Length: " + ur.getLengthOfBooking() + " Hours" + "\n");
        }
    }

    /**
     * This method is responsible for creating a schedule for a single user,
     * with some additional information added
     *
     * @param userID The target user to create the timetable for
     */
    public void createTimetableForPerson_V2(String userID){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql1="from User where id=:id";
        Query query1=session.createQuery(hql1);
        ((org.hibernate.query.Query<?>) query1).setString("id",userID);
        User user=(User) ((org.hibernate.query.Query<?>) query1).uniqueResult();

        String hql2="from User_Room where user =:user";
        Query query2=session.createQuery(hql2);
        ((org.hibernate.query.Query<?>) query2).setEntity("user",user);
        List<User_Room> list= (List<User_Room>) ((org.hibernate.query.Query<?>) query2).list();

        for (User_Room user_room:list){
            System.out.print("User ID:"+user_room.getId().getId()+"\n");
            System.out.print("Applicant First Name:"+user_room.getUser().getFirstName()+"\n");
            System.out.print("Applicant Last Name:"+user_room.getUser().getLastName()+"\n");
            System.out.print("Room Number:"+user_room.getRoom().getRoomNumber()+"\n");
            System.out.print("Date and time of booking: " + user_room.getId().getDateTimeOfBooking() + "\n");
            System.out.print("Length: " + user_room.getLengthOfBooking() + " Hours" + "\n");
            System.out.print("********************************************"+"\n");
        }

        System.out.println("Query Finish");
        System.out.println("Do you want to get detail of Room?(Y/N)");
        Scanner sc=new Scanner(System.in);
        String choose=sc.nextLine();
        if (choose.equals("y")){
            System.out.println("Please input room number:");
            double room_number=sc.nextDouble();
            System.out.println("Querying.......Please Wait");
            ShowBookedRoomDetail(room_number);
        }
        else {

        }

    }

    /**This method is after calling the method createtimetable forperson V2 (),
     * an optional method. If the user chooses to view the room details,
     * this method will be triggered, otherwise it will not be called
     *
     * @param RoomNumber
    */
    public void ShowBookedRoomDetail(Double RoomNumber){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql1="From rooms where roomNumber="+RoomNumber;
        Query query1=session.createQuery(hql1);
        List<rooms> list = (List<rooms>) ((org.hibernate.query.Query<?>) query1).list();
        for(rooms room:list){
            System.out.println(room);
        }

    }

    /**This is a humanized function, which is used to display the detailed information of the module.
     * After calling method requirement, you will choose whether to call it or not according to the user's input.
     *
     * @param inputModuleID
     */
    public void ShowModulesDetail(String inputModuleID){
        System.out.println("inputModuleID: "+inputModuleID);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql1="From Modules where id=:id";
        Query query1=session.createQuery(hql1);
        ((org.hibernate.query.Query<?>) query1).setString("id",inputModuleID);
        List<Modules> list = (List<Modules>) ((org.hibernate.query.Query<?>) query1).list();
        System.out.println("********************************************");
        for(Modules modules:list){
            System.out.println("Modules ID:"+modules.getId());
            System.out.println("Modules Name:"+modules.getName());
            System.out.println("Credits:"+modules.getCredits());
            System.out.println("Modules Weeks:"+modules.getWeeks());
            System.out.println("********************************************");
        }
    }
}

