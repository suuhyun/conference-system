package user.organizer;

import event.EventPresenter;

import java.util.List;
import java.util.ArrayList;

/**
 * A presenter class. This class is responsible for anything related to displaying events to an organizer.
 * @author multiple
 * @version 1
 * */

public class OrganizerEventPresenter extends EventPresenter {
    private String eventSuccess = "This event has been successfully ";
    private String roomSuccess = "This room has been successfully ";

    /**
     * View all the rooms that meet hte requirements
     */
    public void viewRooms(ArrayList<String> rooms){
        System.out.println("The following is a list of all rooms that meet your requirements:");
        for (String room : rooms){
            System.out.println(room);
        }

    }
    /**
     * Message informing that user cancelled event
     * */
    public void successCancelEvent(){
        System.out.println(eventSuccess+ "cancelled.");
    }

    /**
     * Message informing that user succeeded in rescheduling event
     * */
    public void successRescheduleEvent(){
        System.out.println(eventSuccess+ "rescheduled.");
    }

    /**
     * Message informing that user failed to rescheduled event
     * */
    public void failedRescheduleEvent(){
        System.out.println("Failed to reschedule event. Check for conflicts with either the speaker or room");
    }

    /**
     * Message informing that user added room
     * */
    public void successAddRoom(){
        System.out.println(roomSuccess+ "added.");
    }

    /**
     * Message informing that organizer double-booked speaker
     * */
    public void failedDoubleBookSpeaker(){
        System.out.println("This speaker is booked at this time.");
    }

    /**
     * Message informing that organizer double-booked room
     * */
    public void failedDoubleBookRoom(){
        System.out.println("This room is booked at this time.");
    }

    /***
     * Message informing organizer room capacity is too low
     * @param roomCap capacity of room
     */
    public void roomCapacityLow(int roomCap){
        System.out.println("This room has the capacity of " + roomCap + " and so the event cannot be held there.");
    }

    /**
     * Two options: reschedule or cancel event?
     */
    public void promptEditConference(){
        System.out.println("Enter conference you want to remove or edit the event from");
    }

    /**
     * Two options: reschedule or cancel event?
     */
    public void promptCancelMethod(){
        System.out.println("If you are cancelling an event, enter 'x'. Otherwise enter any key.");
    }

    /**
     * Asks for title of event to add
     */
    public void promptRescheduleMethod(){
        System.out.println("Enter the name of the event to be rescheduled.");
    }

    /**
     * Asks for the title of the event to be added
     */
    public void promptTitle(){
        System.out.println("Enter the title of the event you want to add.");
    }

    /**
     * Asks for the title of the conference this event is to be added to
     */
    public void promptConference(){
        System.out.println("Enter the title of the conference you want to add this event to. \nIf the conference does not exist, please create a new conference from the main menu.");
    }

    /**
     * Shows that conference title added was invalid
     */
    public void invalidConference(){
        System.out.println("This conference does not exist, please enter the title of an existing conference or create a new conference from the main menu. " +
                "\nEnter 'x' to go back or anything else to try again");
    }

    /**
     * Shows that comething went wrong when creating the event
     */
    public void failedAddEventToConference(){
        System.out.println("This event was successfully created but could not be added to the conference. You can create a conference and add this event to the conference in the main menu.");
    }

    /**
     * Asks for date of event to add
     */
    public void promptDate(){
        System.out.println("Enter the date in DD/MM/YY format followed");
    }

    /**
     * Asks for start time of event to add
     */
    public void promptStartTime(){
        System.out.println("Enter the start hour of day as a single integer using the 24-hour clock.");
    }

    /**
     * Asks for end time of event to add
     */
    public void promptEndTime(){
        System.out.println("Enter the end hour of day as a single integer using the 24-hour clock.");
    }

    /**
     * Asks for speaker
     */
    public void promptSpeaker(){
        System.out.println("Enter speaker username.");
    }

    /**
     * Asks for room
     */
    public void promptRoom(){
        System.out.println("Enter room name");
    }

    /**
     * Shows that the time is not valid, prompts the user to try again
     */
    public void badTime(){System.out.println("Time is not valid. Try again.");}

    /**
     * Prompts user to view contacts
     */
    public void promptViewContacts(){
        System.out.println("Press 'x' if you want view your contacts according to event or any key to view all contacts together");
    }

    /**
     * Prompts user to view events
     */
    public void promptEvent(){
        System.out.println("Please enter the name of the event you would like view your contacts from of X to go back");
    }

    /**
     * Messages user that they are not in charge of the event
     */
    public void notYourEvent(){
        System.out.println("You are not charge of this event, please try again");
    }

    /**
     * Tells user that the event name is unavailable because it has already been taken
     */
    public void eventExists(){System.out.println("This event name is already taken.");}

    /**
     * Messages user that the event does not exist
     */
    public void noEvent(){System.out.println("This event does not exist, please try again.");}

    /**
     * Contacts of the user
     */
    public void allYourContacts(List<String> contacts){
        System.out.println(contacts.toString());
    }

    /**
     * Events of the contacts of the user
     */
    public void allYourContactsEvent(List<String> eventContacts){
        System.out.println(eventContacts.toString());
    }

    /**
     * Prompts user exit the selection
     */
    public void breakPlease(){System.out.println("* Write '!' if you would like exit the selection at any time. Doing so will not save your current information. *");}

    /**
     * Warns the user that they are existing without saving their information
     */
    public void warning(){System.out.println("WARNING: You are about to exit without saving your information. Enter 'x' to leave or any key to go back.");}

    /**
     * Asks user if event is a vip event
     */
    public void promptVIP(){System.out.println("Is this a VIP event? Enter 'Y' for Yes and 'N' for No.");}

    /**
     * Asks user if room has projector
     */
    public void promptHasProjector(){System.out.println("Does this room have a projector? Enter 'y' for yes and any other key for no");}

    /**
     * Asks user if room has microphone
     */
    public void promptHasMicrophone(){System.out.println("Does this room have a microphone? Enter 'y' for yes and any other key for no");}

    /**
     * Asks user if room has tables
     */
    public void promptHasTables(){System.out.println("Does this room have tables? Enter 'y' for yes and any other key for no");}
    /**
     * Asks user if room has whiteboard
     */
    public void promptHasWhiteboard(){System.out.println("Does this room have a white board? Enter 'y' for yes and any other key for no");}

    /**
     * Asks user if the room to be added needs a projector
     */
    public void promptNeedProjector(){
        System.out.println("Do you need a room with a projector? Enter 'y' for yes and any other key for no.");
    }
    /**
     * Asks user if the room to be added needs a microphone
     */
    public void promptNeedMicrophone(){
        System.out.println("Do you need a room with a microphone? Enter 'y' for yes and any other key for no.");
    }
    /**
     * Asks user if the room to be added needs tables
     */
    public void promptNeedTables(){
        System.out.println("Do you need a room with tables? Enter 'y' for yes and any other key for no.");
    }
    /**
     * Asks user if the room to be added needs a whiteboard
     */
    public void promptNeedWhiteboard(){
        System.out.println("Do you need a room with a whiteboard? Enter 'y' for yes and any other key for no.");
    }
    /**
     * Tells user that their input is not valid
     */
    public void bad(){System.out.println("That is not a valid input. Try again.");}

}
