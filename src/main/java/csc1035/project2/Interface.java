package csc1035.project2;

import java.util.Scanner;
/**
 *This is a special class for representing the input and output of the system.
 *This has methods relating to the attributes and some of its behaviour
 *
 * @author Damon Brooker
 */

public class Interface {

    public static void main(String[] args) {new Interface().run();}

    /**
     * This method is responsible for the interactions between the user and the system
     */
    public void run(){
        Timetabling t = new Timetabling();
        rooms r = new rooms();
        User_Room ur = new User_Room();
        Scanner sc = new Scanner(System.in);
        String inputModuleID;
        boolean sociallyDistant = false;
        System.out.println("Welcome to csc1035 Project 2, Electric Boogaloo");
        boolean quit = false;
        while(!quit){
            menu();
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    r.roomsList();  // Calls roomsList function which outputs all rooms stored in the rooms table
                    break;
                case 2:
                    ur.availableRooms();    // Calls available rooms function which outputs all available rooms
                    break;
                case 3:
                    ur.reserveRoom();   // Calls reserve room function which will take inputs from the user
                    break;              // to reserve a room
                case 4:
                    ur.cancelReservation(); // Calls cancel reservation function which will take in the information
                    break;                  // for the a previous booking and remove it
                case 5:
                    System.out.println("Please enter room number");
                    double roomNumber = sc.nextDouble();
                    r.generateTimetable(roomNumber);    // Calls the generate timetable function that will generate a
                    break;                              // timetable for the user inputted room number
                case 6:
                    System.out.println("Please enter room number");
                    double inputRoomNumber = sc.nextDouble();
                    System.out.println("Please enter the room type");
                    String inputRoomType = sc.next();
                    System.out.println("Please enter the maximum room capacity");
                    int inputMaxCapacity = sc.nextInt();
                    System.out.println("Please enter the maximum socially distant capacity");
                    int inputSocialDistancingCapacity = sc.nextInt();
                    r.updateDetails(inputRoomNumber, inputRoomType, inputMaxCapacity, inputSocialDistancingCapacity);
                    break;          // Calls update details function that takes inputs from the user and changes the
                case 7:             // reservation information
                    System.out.println("Please enter the module ID");
                    inputModuleID = sc.next();
                    t.listStudentsModule(inputModuleID);    // Calls list student module which takes a module ID from
                    break;                                  // the user and outputs all students that take that module
                case 8:
                    System.out.println("Please enter the module ID");
                    inputModuleID = sc.next();
                    t.listStaffModule(inputModuleID);       // Calls list staff module which takes a module ID from the
                    break;                                  // user and outputs all of the staff teaching that module
                case 9:
                    System.out.println("Please enter the module ID");
                    inputModuleID = sc.next();
                    t.listModuleRequirementsV2(inputModuleID);// Calls list module requirements which takes a module ID
                    break;                                  // from the user and outputs all of the module requirements
                case 10:
                    System.out.println("Creating timetable now");
                    t.createTimetable(sociallyDistant);     // Calls create timetable function that will generate a
                    break;                                  // timetable for the school and book relevant rooms
                case 11:
                    System.out.println("Creating socially distant timetable now");
                    sociallyDistant = true;
                    t.createTimetable(sociallyDistant);     // Calls create timetable function that will generate a
                    break;                                  // timetable under socially distant conditions for the school
                case 12:
                    System.out.println("Please enter the user ID for the create timetable");
                    String inputUserID = sc.next();
                    t.createTimetableForPerson_V2(inputUserID);
                    break;
                case 13:
                    System.out.println("Quitting program"); // Quits program
                    quit = true;
                    break;
                default:
                    System.out.println("Please enter a suitable option");
                    break;

            }
        }
    }

    /**
     * This method is responsible for the layout of the menu the user will be presented with
     */
    public void menu(){
        System.out.println(     "1: List of all rooms \n" +
                                "2: List available rooms \n" +
                                "3: Reservation of room \n" +
                                "4: Cancellation of reservation \n" +
                                "5: Timetable for a room \n" +
                                "6: Update room details \n" +
                                "7: List students taking a module \n" +
                                "8: List staff that teach a module \n" +
                                "9: List of module requirements \n" +
                                "10: Create timetable for school \n" +
                                "11: Create socially distant timetable for school \n" +
                                "12: Producing timetable for a user \n" +
                                "13: Quit program \n" +
                                "Enter your choice:");
    }

}
