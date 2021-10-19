package user.organizer;

import message.MessagePresenter;

import java.util.ArrayList;

/**
 * A presenter class. This class is responsible for anything related to displaying messages to the user.
 * Creates menu specific to organizer for the method of messaging.
 * @author multiple
 * @version 1
 * */
public class OrganizerMessagePresenter extends MessagePresenter {


    /**
     * Prompt to enter event to send message to
     * */
    public void promptEvent(){
        System.out.println("Please enter the name of an event you would like to send a message to."
                + "\nYou can see a list of events by selecting option 8 in the main menu");
    }


    /**
     * Print menu for messaging options specific to organizer
     * */
    public void printMessageMenu(){
        String display = ""
                + "\n[1] Send a message to all speakers"
                + "\n[2] Send a message to all attendees of an event."
                + "\n[3] Send a message to a single user"
                + "\nPlease select a menu item number.";
        System.out.println(display);
    }


    /**
     * Print message for error
     * */
    public void eventNotCreated(){
        String display = "That event does not exist. Try again or create a new event first.";
        System.out.println(display);
    }


    /**
     * Print message event has no attendees
     * */
    public void eventNoAttendees(){
        String display = "That event has no attendees. Try getting some friends first :/ ";
        System.out.println(display);
    }


    /**
     * Tell user they are creating a speaker
     */
    public void createSpeakerMessage(){
        System.out.println("You have chosen to create a speaker");
    }


    /**
     * Tell user they are creating a speaker
     */
    public void createOrganizerMessage(){
        System.out.println("You have chosen to create an organizer");
    }


    /**
     * Tell user they are creating a speaker
     */
    public void createAttendeeMessage(){
        System.out.println("You have chosen to create an attendee");
    }


    /**
     * Prompt to enter the username of the speaker to create
     */
    public void userUsernamePrompt(){
        String display = "What is the username of the user you would like to create? [enter x to go back]";
        System.out.println(display);
    }


    /**
     * Prompt to enter the password of the speaker to create
     */
    public void userPasswordPrompt(){
        String display = "What is the password of the user you would like to create?";
        System.out.println(display);
    }


    /**
     * Print menu for messaging options specific to organizer
     * */
    public void printUserMenu(){
        String display = ""
                + "\n[1] Create a speakers"
                + "\n[2] Create an attendee."
                + "\n[3] Create an organizer."
                + "\nPlease select a menu item number or enter x to go back";
        System.out.println(display);
    }


    /**
     * Print menu for messaging options specific to organizer
     * */
    public void printStatsMenu(){
        String display = ""
                + "\n[1] View number of offered events"
                + "\n[2] Most attended events"
                + "\n[3] Least attended event"
                + "\n[4] Top 5 events"
                + "\n[5] New session attendees"
                + "\n[6] Average number of attendees"
                + "\n[7] Median number of attendees"
                + "\n[8] Mode number of attendees"
                + "\n[9] Number of events at max capacity"
                + "\n[10] Events ordered by dates"
                + "\n[11] Total number of speakers"
                + "\n[12] Total number of attendees"
                + "\nPlease select a menu item number or enter x to go back";
        System.out.println(display);
    }


    /**
     * Message stating that the user does not exist, prompts user to try again or create a new speaker
     */
    public void speakerDoesNotExist(){
        String display = "This speaker does not exist. Try again or create a new speaker first.";
        System.out.println(display);
    }


    /**
     * Message stating that the speaker has been successfully created
     */
    public void userCreated(){
        String display = "You have successfully created the user.";
        System.out.println(display);
    }


    /**
     * Message stating that the user already exists
     */
    public void userExists(){System.out.println("This username already exists in our files. \nTry another one or enter x to go back");}


    /**
     * Message stating that the room does not exist and that the user can create a new room or rewrite the name of the room
     */
    public void badRoom(){
        System.out.println("That room does not exist. Add a new room by writing 'ADD' or rewrite the name of the room after pressing any key.");
    }


    /**
     * Message stating that the user successfully created a new room
     */
    public void addedRoom(){
        System.out.println("You have successfully created a new room.");
    }


    /**
     * Message stating that the room already exists
     */
    public void alreadyAddedRoom(){System.out.println("That room already exists.");}


    /**
     * Message prompting the user to create a new speaker or to press another key if speaker exists
     */
    public void newOrNoSpeaker(){System.out.println("Enter 'NEW' to create new speaker or another other key if speaker exists.");}


    /**
     * Message telling speaker has successfully been added
     */
    public void speakerAdded(){System.out.println("Speaker has successfully been added");}


    /**
     * Message stating that the room already exists
     */
    public void notValidChoice(){System.out.println("That choice is invalid please try again or press x to go back.");}


    /**
     * Message stating there are no speakers
     */
    public void zeroSpeakers(){System.out.println("Enter 'Y' to have 0 speakers");}


    /**
     * Prompt for number of speakers
     */
    public void numberOfSpeaker(){System.out.println("Enter an integer value for the number of speakers you would like to have");}


    /**
     * Message stating most attended events
     */
    public void mostAttendedEvents(ArrayList<String> i ){System.out.println(i + " is the most attended event.");}


    /**
     * Message stating least attended events
     */
    public void leastAttendedEvents(ArrayList<String> i ){System.out.println(i + " is the least attended event.");}


    /**
     * Message stating number of events available
     */
    public void numberEventsAvailable(Integer i){System.out.println("There are " + i + " events available.");}


    /**
     * States the number of events at max capacity
     */
    public void numAtMaxCapacity(Integer i){System.out.println(i + " is the number of events at max capacity.");}

    public void eventsOrderedByDate(ArrayList<String> i){System.out.println(i);}

    public void totalNumberSpeakers(Integer i){System.out.println(i + " is the total number of speakers.");}

    public void totalNumberAttendees(Integer i){System.out.println(i + " is the total number of attendees.");}

    public void newSessionAttendees(Integer i){System.out.println(i + " is the new session attendees.");}

    public void averageNumberAttendees(Integer i){System.out.println(i + " is the average number of attendees.");}

    public void medianNumberAttendees(Integer i){System.out.println(i + " is the median number of attendees.");}

    public void modeNumberAttendees(Integer i){System.out.println(i + " is the mode number of attendees.");}

    public void topFiveEvents(ArrayList<String> i){System.out.println(i + " are the top five events.");}
}
