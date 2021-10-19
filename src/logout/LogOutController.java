package logout;

import conference.ConferenceActions;
import message.MessageActions;
import room.RoomActions;
import user.attendee.AttendeeActions;
import user.organizer.OrganizerActions;
import user.speaker.SpeakerActions;
import event.EventActions;
import store.Store;


/**
 * Allows user to exit the system with their information stored
 */
public class LogOutController {
    Store store;
    MessageActions messageActions;
    OrganizerActions organizerActions;
    AttendeeActions attendeeActions;
    RoomActions roomActions;
    SpeakerActions speakerActions;
    EventActions eventActions;
    LogoutActions logoutActions;
    ConferenceActions conferenceActions;


    /**
     * Instantiates a new logout object
     * @param store the gateway responsible for storing information regarding rooms, events, messages, and
     *              organizers of the conference
     * @param eventSystemActions the use cases for handling the system of events
     * @param accountActions the use cases for handling the accounts of users
     * @param logoutActions the use case handling logout
     */
    public LogOutController(Store store, parameterObjects.EventSystemActions eventSystemActions,
                            parameterObjects.AccountActions accountActions, LogoutActions logoutActions){
        this.store = store;
        this.messageActions = eventSystemActions.getMessageActions();
        this.organizerActions = accountActions.getOrganizerActions();
        this.attendeeActions = accountActions.getAttendeeActions();
        this.roomActions = eventSystemActions.getRoomActions();
        this.speakerActions = accountActions.getSpeakerActions();
        this.eventActions = eventSystemActions.getEventActions();
        this.logoutActions = logoutActions;
        this.conferenceActions = eventSystemActions.getConferenceActions();

    }


    /**
     * Stores information for logging out
     * @param username A string the user inputs as their username
     * @param type A string that represents the type of user
     */
    public void loggingOut(String username, String type) {

        store.storeMessages(messageActions);
        store.storeRooms(roomActions);
        store.storeEvents(eventActions);
        store.storeOrganizers(organizerActions);
        store.storeAttendees(attendeeActions);
        store.storeSpeakers(speakerActions);
        store.storeConferences(conferenceActions);


        logoutActions.logout(username, type, attendeeActions, organizerActions, speakerActions);

    }


    /**
     * Exits the application
     */
    public void exit(){
        System.exit(1);
    }
}
