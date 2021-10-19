package user.speaker;

import message.MessagePresenter;

/**
 * A presenter class. This class is responsible for anything related to displaying messages to the user.
 * Creates menu specific to speaker for the method of messaging.
 * @author multiple
 * @version 1
 * */
public class SpeakerMessagePresenter extends MessagePresenter {
    /**
     * Prompt to enter a list of events
     * */
    public void promptListEvents(){
        System.out.println("Enter an event name or multiple event names (separated by commas without spaces) to send a message. "
                + "\nEnter the 'return' key to separate each event. Enter 'X' to finish.");
    }

    /**
     * Message that the user did not input valid event
     */
    public void failedEvent(){
        System.out.println("Message failed. The event you provided was not valid. Enter 'X' to finish or re-enter the event");
    }

    /**
     * Print menu for messaging options specific to speaker
     * */
    public void printMenu(){
        String display = ""
                + "\n[1] Send a message to all attendees of an event or multiple events"
                + "\n[2] Send a message to a single user"
                + "\nPlease select a menu item number.";
        System.out.println(display);
    }

    /**
     * Display a message stating that there are no attendees in the event
     */
    public void noAttendees(){
        String display = "There are no attendees in the event.";
        System.out.println(display);
    }

}
