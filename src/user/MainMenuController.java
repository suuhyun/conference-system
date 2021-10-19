package user;
import user.AccountController;
import user.User;
import conference.ConferencePresenter;
import event.EventPresenter;
import message.MessagePresenter;
import conference.ConferenceActions;
import room.RoomActions;
import user.UserController;
import user.speaker.SpeakerActions;

import java.util.*;

/**
 * A controller class that decides what to do based on user input when choosing from the main menu.
 * @author multiple
 * @version 1
 * */
public abstract class MainMenuController extends AccountController {
    private UserController controller;
    private String userID;
    private RoomActions room;
    private MessagePresenter displayMessage;
    private EventPresenter displayEvent;
    private SpeakerActions speakerActions;
    private ConferenceActions conferenceActions;
    protected ConferencePresenter displayConference;
    private Scanner scan = new Scanner(System.in);

    /**
     * Instantiates the main menu responder object
     *
     * @param userID     the user ID
     * @param controller the controller responsible for user
     */
    public MainMenuController(String userID, UserController controller, RoomActions room, SpeakerActions speakerActions, ConferenceActions conferenceActions) {
        this.controller = controller;
        this.userID = userID;
        this.displayMessage = new MessagePresenter();
        this.displayEvent = new EventPresenter();
        this.displayConference = new ConferencePresenter();
        this.room = room;
        this.speakerActions = speakerActions;
        this.conferenceActions = conferenceActions;
    }

    /**
     * @param str
     * @return exiting program
     */

    protected boolean validInput(String str) {
        return !str.equals("") && !str.equalsIgnoreCase("x");
    }


    /**
     * Responds to menu option 2, send message
     */
    public void option2SendMessage() {
        String username = controller.returnUserIDHashMap().get(userID).getUsername();
        displayMessage.promptRecipient(); // enter user you would like to send message to
        if (option2Helper()) {

            String receiver = scan.nextLine();
            // if receiver in contacts
            displayMessage.promptMessage(); // enter the message
            String content = scan.nextLine();

            if (controller.sendMessage(username, receiver, content)) {
                displayMessage.successMessage(); // message has been sent successfully
            }
        } else {
            displayMessage.failedMessage(); // message could not be sent
        }
    }

    /**
     * Responds to menu option 3, view all messages
     */
    public void option3ViewAllMessages() {
        List<String> contactIds = controller.returnUserIDHashMap().get(userID).getContactsList();
        if (contactIds.isEmpty()) {
            displayMessage.zeroContacts();
        } else {
            HashMap<String, User> userIdHash = controller.returnUserIDHashMap();
            ArrayList<String> contactUsernames = new ArrayList<String>();
            for (Map.Entry<String, User> user : userIdHash.entrySet()) {
                if (contactIds.contains(user.getKey())) {
                    contactUsernames.add(user.getValue().getUsername());
                }
            }
            displayMessage.promptSelectReceiver(); // please select the receiver whose conversation you would like to view
            for (String username : contactUsernames) {
                displayMessage.printString(username); // receiver username
            }
            String receiverUsername = scan.nextLine();
            HashMap<String, User> usernameHash = controller.returnUserUsernameHashMap();
            if (usernameHash.get(receiverUsername) != null) {
                displayMessage.displayMessages(controller, userID, usernameHash.get(receiverUsername).getId()); // will pass in id instead of username
            } else {
                displayMessage.failedContact();
            }
        }
    }

    /**
     * Responds to menu option 4, add a contact
     */
    public void option4AddContact() {
        String username = controller.returnUserIDHashMap().get(userID).getUsername();
        displayMessage.promptContact();
        String add = scan.nextLine();
        if (controller.returnUserUsernameHashMap().containsKey(add)) {
            if (controller.addContact(add, username)) {
                displayMessage.successContact();
            } else {
                displayMessage.sameUserContact();
            }
        } else {
            displayMessage.failedContact();
        }
    }

    /**
     * Responds to menu option 5- view all contacts
     */
    public void option5ViewAllContacts() { //view all contacts

        displayMessage.displayContacts(controller, userID);

    }

    /**
     * Responds to menu option 5- view all contacts
     * Returns boolean if contact list is empty
     */
    private boolean option2Helper() { //view all contacts

        return displayMessage.displayContacts(controller, userID);

    }

    /**
     * Responds to menu option 6- sign up or save an event (Attendee)
     * Create event or speaker (Organizer)
     * View schedule of talks (Speaker)
     */
    public abstract void option6EventSignupCreateView();

    /**
     * Responds to menu option 7- cancel attendance to an event (Attendee)
     * Remove event or reschedule (Organizer)
     */
    public void option7Cancel() {
        option9ScheduleOrRoom();
        displayEvent.promptCancelEvent();
        String eventName = scan.nextLine();
        // i think this is trying to cancel event for an attendee, so it's using leaveEvent in AttendeeActions
        if (controller.leaveEvent(eventName, userID)) {
            displayEvent.successCancelEnrol();
        } else {
            displayEvent.failedCancelEvent();
        }
    }

    /**
     * Responds to menu option 8- view all events
     */
    public void option8ViewAllEvents() {
        String conferenceTitle = "";
        String username = controller.returnUserIDHashMap().get(userID).getUsername();
        ArrayList<List<String>> conferences = conferenceActions.returnAttendedConferences(username);
        displayConference.displayConferences(conferences);
        displayConference.promptConference();
        boolean correctConference = false;
        while (!correctConference) {
            conferenceTitle = scan.nextLine();
            if (!conferenceActions.conferenceExists(conferenceTitle)) {
                displayConference.invalidTitle();
                if (scan.nextLine().equalsIgnoreCase("x")) {
                    return;
                }
            } else if (!conferenceActions.isAttendee(conferenceTitle, username)) {
                displayConference.notAttendingConfernce();
                if (scan.nextLine().equalsIgnoreCase("x")) {
                    return;
                }
            } else {
                correctConference = true;
            }
        }

        // String username = controller.returnUserIDHashMap().get(userID).getUsername();
        List<List<String>> eventsList = controller.viewAvailableSchedule(username, conferenceTitle);

        if (eventsList.size() == 0) {
            //displayMessage.noEvents();
            displayEvent.noEventsAvailable();
        } else {
            displayEvent.eventIntro();
            for (List<String> e : eventsList) {
                e.set(2, room.findRoomFromId(e.get(2)).getRoomName());
                List<String> speakerList = new ArrayList<String>();
                for (String speaker : e.get(3).split(",")) {
                    if (speaker.equals("")) {
                        speakerList.add(displayMessage.noSpeakers());
                    } else {
                        speakerList.add(speakerActions.findUserFromId(speaker).getUsername());
                    }
                }
                e.set(3, String.valueOf(speakerList));
                //e.set(3, speakerActions.findUserFromId(e.get(3)).getUsername());
            }
            displayEvent.displayEvents(eventsList);
        }
    }

    /**
     * Responds to menu option 9- view events user is signed up for (Attendee)
     * Add room (Organizer)
     * View schedule of talks (Speaker)
     */
    public void option9ScheduleOrRoom() {
        String username = controller.returnUserIDHashMap().get(userID).getUsername();
        List<List<String>> eventsList = controller.viewOwnSchedule(username);
        if (eventsList.size() == 0) {
            displayMessage.noEventsSignUp();
        } else {
            for (List<String> e : eventsList) {
                e.set(2, room.findRoomFromId(e.get(2)).getRoomName());
                if (e.get(3).equals("")) {
                    e.set(3, "There are no speakers at the moment for this event.");
                } else {
                    e.set(3, speakerActions.findUserFromId(e.get(3)).getUsername());
                }
            }
            displayEvent.displayEvents(eventsList);
        }

    }

    /**
     * Responds to menu option 10
     * Add user (Organizer)
     * View saved events (Attendee)
     */
    public void option10AddOrViewEvents() {
    }

    /**
     * Responds to menu option 11
     * View VIP events (Attendee)
     * View conferences (Organizers)
     */
    public void option11VIPOrConferences() {
    }

    /**
     * Response to menu option 12
     * Add a conference (Organizer)
     * Sign up for a conference (Attendee)
     */
    public void option12Conference() {
    }

    /**
     * Responds to menu option 13 - change event capacity (Organizer)
     */
    public void option13ChangeCapacity() {
    }


    /**
     * Responds to menu option 14 - View statistics (Organizer)
     */
    public void option14ViewStatistics() {
    }

}
