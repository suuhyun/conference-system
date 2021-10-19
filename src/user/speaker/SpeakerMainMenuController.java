package user.speaker;
import conference.ConferenceActions;
import user.MainMenuController;
import room.RoomActions;
import user.attendee.AttendeeActions;
import event.Event;
import user.User;
import event.EventActions;
import event.EventPresenter;

import java.util.*;

/**
 * A controller class for speaker that decides what to do based on user input when choosing from the main menu.
 * @author multiple
 * @version 1
 * */
public class SpeakerMainMenuController extends MainMenuController {
    private SpeakerController controller;
    private String userID;
    private SpeakerMessagePresenter displayMessage;
    private EventPresenter displayEvent;
    private EventActions eventActions;
    private AttendeeActions attendeeActions; //TODO : Should we get rid of attendee actions and bring in useractionations instead?
    private RoomActions roomActions;
    // private SpeakerActions speakerActions;
    private ConferenceActions conferenceActions;
    private Scanner scan = new Scanner(System.in);

    /**
     * Instantiates the main menu responder object
     *
     * @param userID              the user ID
     * @param speakerController the controller responsible for speaker
     */
    public SpeakerMainMenuController(String userID, SpeakerController speakerController, EventActions eventActions, AttendeeActions attendeeActions, RoomActions roomActions, SpeakerActions speakerActions, ConferenceActions conferenceActions) {
        super(userID, speakerController, roomActions, speakerActions, conferenceActions);
        this.userID = userID;
        this.controller = speakerController;
        this.displayMessage = new SpeakerMessagePresenter();
        this.displayEvent = new EventPresenter();
        this.controller = speakerController;
        this.eventActions = eventActions;
        this.attendeeActions = attendeeActions;
        this.conferenceActions = conferenceActions;
        this.roomActions = roomActions;
    }

    /**
     * Responds to menu option 2, send message
     */
    public void option2SendMessage(){
        displayMessage.printMenu();
        String option = scan.nextLine();
        SpeakerMessageMenuController menuController = new SpeakerMessageMenuController(this.controller);
        if (option.equals("1")){
            menuController.sendMessage();
        }
        if (option.equals("2")){
            super.option2SendMessage();
        }
    }
    /**
     * Responds to menu option 3
     * View all messages
     */
    public void option3ViewAllMessages() {
        List<String> contactIds = controller.returnUserIDHashMap().get(userID).getContactsList();
        if(contactIds.isEmpty()){
            displayMessage.zeroContacts();
        } else {
            HashMap<String, User> userIdHash = controller.returnUserIDHashMap();
            ArrayList<String> contactUsernames = new ArrayList<String>();
            for(Map.Entry<String, User> user : userIdHash.entrySet()) {
                if(contactIds.contains(user.getKey())){
                    contactUsernames.add(user.getValue().getUsername());
                }
            }
            displayMessage.promptSelectReceiver(); // please select the receiver whose conversation you would like to view
            for(String username : contactUsernames){
                displayMessage.printString(username); // receiver username
            }
            String receiverUsername = scan.nextLine();
            HashMap<String, User> usernameHash = controller.returnUserUsernameHashMap();
            if(usernameHash.get(receiverUsername) != null){
                displayMessage.displayMessages(controller, userID, usernameHash.get(receiverUsername).getId()); // will pass in id instead of username
            } else {
                displayMessage.failedContact();
            }
        }
    }

    /**
     * Responds to menu option 4
     * Add a contact
     */

    public void option4AddContact(){
        String username = controller.returnUserIDHashMap().get(userID).getUsername();
        displayMessage.promptContact();
        String add = scan.nextLine();
        //HashMap<String, User> userUsernameHashMap = controller.returnUserUsernameHashMap();
        if (controller.returnUserUsernameHashMap().containsKey(add)){
            if (controller.addContact(add, username)){
                displayMessage.successContact();
            }
        }
        else{
            displayMessage.failedContact();
        }
    }

    /**
     * Responds to menu option 5
     * View all contacts
     */
    public void option5ViewAllContacts() { //view all contacts
        displayMessage.displayContacts(controller, userID);
}

    /**
     * Responds to menu option 6
     * View schedule of talks
     */
    public void option6EventSignupCreateView() {
        List<String> e = controller.returnUserIDHashMap().get(userID).getEventList();
        List<List<String>> stringE = new ArrayList<>();

        for (String event : e) {
            List<String> miniList = new ArrayList<>();
            Event e1 = eventActions.getEvent(event);

            List<String> newList = new ArrayList<>();
            for (String attendeeID : e1.getAttendees()) {
                newList.add(attendeeActions.findUserFromId(attendeeID).getUsername());
            }


            miniList.add((e1.getTitle()));
            miniList.add(e1.getDateTime());
            miniList.add(roomActions.findRoomFromId(e1.getRoomID()).getRoomName());
            miniList.add(newList.toString());

            stringE.add(miniList);
        }

        displayEvent.viewEvents(stringE);
    }
}