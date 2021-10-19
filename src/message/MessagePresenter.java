package message;
import user.UserController;

import java.util.ArrayList;
import java.util.List;

/**
 * A presenter class. This class is responsible for anything related to displaying messages to the user.
 * @author multiple
 * @version 1
 * */
public class MessagePresenter {


    /**
     * Displays the content of the message
     */
    public void printString(String theString) {
        System.out.println(theString);
    }


    /**
     * Display the prompt to select the receiver of the user's message
     */
    public void promptSelectReceiver() {
        System.out.println("Please select the conversation you would like to view by selecting the receiver of your message.");
    }


    /**
     * Display messages
     */
    public boolean displayMessages(UserController controller, String fromMe, String toMe) {
        ArrayList<ArrayList<String>> messagesList = controller.viewMessages(fromMe, toMe); // fromMe and toMe are are user IDs
        int count = 1;
        for (ArrayList<String> message : messagesList) {
            System.out.println(count);
            count++;
            System.out.println("Sender: " + message.get(0));
            System.out.println("Recipient: " + message.get(1));
            System.out.println("Time Sent: " + message.get(2));
            System.out.println("Message: " + message.get(3));
            System.out.println("\n");
        }
        return true;
    }


    /**
     * Prompt for recipient
     */
    public void promptRecipient() {
        System.out.println("To whom would you like to send this message? Enter a username.");
    }


    /**
     * Prompt for message content
     */
    public void promptMessage() {
        System.out.println("Enter your message.");
    }


    /**
     * Prompt for contact
     */
    public void promptContact() {
        System.out.println("Enter the username of the person you would like to add to your contacts list.");
    }


    /**
     * Message of successful contact
     */
    public void successContact() {
        System.out.println("You have successfully added this person to your contacts list!");
    }


    /**
     * Message of failed contact
     */
    public void failedContact() {
        System.out.println("This username is not valid.");
    }


    /**
     * User does not have any contacts
     */
    public void zeroContacts() {
        System.out.println("You have not received any messages or contacted anyone.");
    }


    /**
     * Message of failed contact
     */
    public void sameUserContact() {
        System.out.println("This username is your username");
    }


    /**
     * Message informing message sent
     */
    public void successMessage() {
        System.out.println("Your message has been sent successfully!");
    }


    /**
     * Message informing message sent
     */
    public void failedMessageNoSpeakers() {
        System.out.println("No speakers exist so there is no one to send your message to :(");
    }


    /**
     * Display all contacts
     */
    public boolean displayContacts(UserController controller, String userId) {
        List<String> usersList = controller.viewContacts(userId);
        if (usersList.size() == 0) {
            noContacts();
            return false;
        } else {
            int count = 1;
            if (usersList != null) {
                for (String name : usersList) {
                    System.out.println(count + ". " + name);
                    count += 1;
                }
            }
        }
        return true;
    }


    /**
     * Message informing user they have no contacts
     */
    public void noContacts() {
        System.out.println("You currently have no contacts :(");
        System.out.println("Add a friend through option 4!");
    }


    /**
     * Message informing failed message
     */
    public void failedMessage() {
        System.out.println("Your message could not be sent. Please check if the recipient is valid.");
    }


    /**
     * Message informing user is signed up for no events
     */
    public void noEventsSignUp() {
        System.out.println("You are not signed up for any events.");
    }


    /**
     * Message informing user has no saved events
     */
    public void noSavedEvents() {
        System.out.println("You didn't save any events");
    }


    /**
     * Message informing user there are no events
     */
    public void noEvents() {
        System.out.println("There are no events available.");
    }


    /**
     * Message informing user there are no events
     */
    public void noVIPEvents() {
        System.out.println("There are no VIP events available.");
    }


    /**
     * Message informing user there are no events
     */
    public String noSpeakers() {
        System.out.println("There are no speakers for this event.");
        return "There are no speakers for this event.";
    }


    /**
     * A message asking for the capacity of an event
     */
    public void eventCapacity() {
        System.out.println("Please enter the event capacity");
    }


    /**
     * A message informing user that their input is invalid
     */
    public void invalidCapaity() {
        System.out.println("Seems like that wasn't a number, please try again");
    }


    /**
     * A message informing user that their input is invalid ie empty or a variation of 'x'
     */
    public void invalidInput() {
        System.out.println("Your input is invalid and either empty or 'x'. Please try again");
    }


    /**
     * A message informing user that they are exiting their current task
     */
    public void exit() {
        System.out.println("Exiting...");
    }

}