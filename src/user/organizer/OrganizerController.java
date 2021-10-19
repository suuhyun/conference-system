package user.organizer;

import conference.ConferenceActions;
import user.UserController;
import message.MessageActions;
import room.RoomActions;
import user.attendee.AttendeeActions;
import user.speaker.SpeakerActions;
import event.Event;
import user.speaker.Speaker;
import user.User;
import user.attendee.Attendee;
import event.EventActions;
import user.UserAccountActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * An organizer controller. Includes all the abilities only organizers can complete.
 * @author Eryka Shi-Shun
 *
 */

public class OrganizerController extends UserController {
    private MessageActions messageActions;
    private EventActions eventActions;
    private RoomActions roomActions;
    private SpeakerActions speakerActions;
    private OrganizerActions organizerActions;
    private AttendeeActions attendeeActions;
    private ConferenceActions conferenceActions;
    private UserAccountActions userAccountActions;
    private String organizerID;

    /**
     * Instantiates a new SpeakerController object. Creates an instance of SpeakerID, MessageActions, EventActions
     * AttendeeActions, RoomActions, OrganizerActions and SpeakerActions.
     * @param organizerID
     * @param eventSystemActions the use cases related to the system of events
     * @param accountActions the use cases related to handling the account
     */
    public OrganizerController(String organizerID, parameterObjects.EventSystemActions eventSystemActions, parameterObjects.AccountActions accountActions){

        super(eventSystemActions, accountActions, 'o');
        this.organizerID = organizerID;
        this.speakerActions = accountActions.getSpeakerActions();
        this.eventActions = eventSystemActions.getEventActions();
        this.roomActions = eventSystemActions.getRoomActions();
        this.messageActions = eventSystemActions.getMessageActions();
        this.attendeeActions = accountActions.getAttendeeActions();
        this.organizerActions = accountActions.getOrganizerActions();
        this.userAccountActions = accountActions.getUserAccountActions();
        this.conferenceActions = eventSystemActions.getConferenceActions();
    }
    /***
     * create a new event
     * @param title of event
     * @param speakerIds of event
     * @param startDateTime the start date and time for the event
     * @param endDateTime the end date and time for the event
     * @param roomID of event
     * @param conferenceTitle
     * @param isVip if event is a vip event
     * @return if the event was created- this will return false if the event already exists
     */
    public List<Boolean> createEvent(String title, List<String> speakerIds, String startDateTime, String endDateTime,
                                     String roomID, String conferenceTitle, int capacity, boolean isVip){
        List<String> attendees = new ArrayList<String>();
        List<Boolean> checks = new ArrayList<Boolean>();
        int roomCap = roomActions.findRoomFromId(roomID).getCapacity();
        Event event;
        if (roomCap >= capacity) {
            event = this.eventActions.createEvent(title, speakerIds, startDateTime, endDateTime, attendees, roomID, conferenceTitle, capacity, isVip);
            checks.add(true);
            if(event != null){
                if (speakerIds.size() != 0) {
                    scheduleSpeaker(event.getId(), speakerIds, true);
                }

                return checks;
            }
        } else{
            checks.add(false);
        }

        if (!eventActions.isRoomFree(roomID, startDateTime, endDateTime)){
            checks.add(false);
        } else {
            checks.add(true);
        }
        if(!eventActions.isSpeakerFree(speakerIds, startDateTime, endDateTime)){
            checks.add(false);
        } else {
            checks.add(true);
        }
        return checks;
    }

    public List<List<String>> viewAvailableSchedule(String user, String conferenceTitle) {
        List<String> conferenceEvents = conferenceActions.returnTitleHashMap().get(conferenceTitle).getEvents();
        List<List<String>> scheduleList = new ArrayList<List<String>>();
        for (String event : conferenceEvents) {
            String title = eventActions.getEvent(event).getTitle();
            String dateTime = eventActions.getEvent(event).getDateTime();
            String roomId = eventActions.getEvent(event).getRoomID();
            List<String> speakers = eventActions.getEvent(event).getSpeakers();
            List<String> info = new ArrayList<String>();
            info.add(title);
            info.add(dateTime);
            info.add(roomId);
            info.addAll(speakers);
            scheduleList.add(info);
        }
        return scheduleList;
    }

    /***
     * cancel an event
     * @param eventName
     * @return true if the event was successfully canceled (ie if it exists, then it will be cancelled)
     */

    //@Override
    public boolean cancelEvent(String eventName){
        if (this.eventActions.getEventNames().containsKey(eventName)) {
            List<String> eventAttendees = this.eventActions.cancelEvent(eventName);
            List<String> s = eventActions.getEventNames().get(eventName).getSpeakers();
            //if (speakerActions.isEventRemovedFromSpeaker(this.eventActions.getEventFromName(eventName).getId(), s)) {
            if (eventAttendees != null) {
                for (String attendeeID : eventAttendees) {
                    this.organizerActions.removeEventFromUser(eventName, attendeeID);
                }

                return true;
            }

        }
        return false;
    }

    /***
     * create a speaker and add them to the speaker schedule
     * @param username username of speaker
     * @param password password of speaker
     */
    public boolean createSpeaker(String username, String password){
        // what if speaker is already created?
        boolean speaker = true;
        if(speakerActions != null) {
            if (speakerActions.findUserFromUsername(username) != null) {
                return false;
            }
            String speakerID = this.speakerActions.createUser(username, password).getId();

            this.eventActions.addSpeakerToSchedule(speakerID);
        }
        return speaker;
    }

    /***
     * create an organizer and add them to the speaker schedule
     * @param username username of organizer
     * @param password password of organizer
     */
    public boolean createOrganizer(String username, String password){
        if(organizerActions != null) {
            if (organizerActions.findUserFromUsername(username) != null) {
                return false;
            }
            this.organizerActions.createUser(username, password);
            return true;
        }
        return false;
    }

    /***
     * create an attendee
     * @param username username of attendee
     * @param password password of attendee
     */
    public boolean createAttendee(String username, String password, boolean isVIP){
        if(attendeeActions != null) {
            if (attendeeActions.findUserFromUsername(username) != null) { // if attendee username exists
                return false;
            }

            this.attendeeActions.createUser(username, password);

            return true;
        }
        return false;

    }


    /***
     * Create a room and add it to the room schedule
     */
    public boolean createRoom(String roomName){
        if(roomActions != null && eventActions != null){
            String roomID = this.roomActions.createRoom(roomName).getRoomId();
            return this.eventActions.addRoomToSchedule(roomID);
        }
        return false;
    }

    /**
     * Schedule speaker to speak at an event. under the assumption that speaker can be added/ is free
     * @param eventID
     * @param speakerID
     *
     */
    public boolean scheduleSpeaker(String eventID, List<String> speakerID, boolean canAdd){
        if (canAdd) {
            eventActions.setSpeaker(eventID, speakerID);
            String speakerUsername = speakerActions.returnIDHashMap().get(speakerID) != null ? speakerActions.returnIDHashMap().get(speakerID).getUsername() : null;
            speakerActions.addEventToUser(eventID, speakerUsername);
            return true;
        }
        return false;
    }

    // TODO edit event details... is this necessary?

    /***
     * reschedule an event with a new date and time
     * @param eventID
     * @param newStartDateTime the new start date and time for the event to be changed to
     * @param newEndDateTime the new end date and time for the event to be changed to
     * @return if the event was successfully rescheduled
     */
    public boolean rescheduleEvent(String eventID, String newStartDateTime, String newEndDateTime){
        return this.eventActions.changeEventTime(eventID, newStartDateTime, newEndDateTime);
    }

    /***
     * Send all the attendees of an event a message
     * @param event
     * @param message
     */
    public boolean sendAttendeesMessage(String event, String message){
        HashMap<String, User> userHash = returnUserUsernameHashMap();
        HashMap<String, Attendee> attendeesHash = attendeeActions.returnIDHashMap();
        HashMap<String, Organizer> idHash = organizerActions.returnIDHashMap();
        String userUsername = idHash.get(organizerID).getUsername();
        HashMap<String, Event> eventsHash = eventActions.getEventNames();

        if(eventsHash.get(event) == null) {
            return false;
        }

        String eventID = eventsHash.get(event).getId();
        List<String> attendees = eventActions.getEventAttendees(eventID);
        for (String attendeeID: attendees){
            organizerActions.addUserContactList(userUsername, attendeesHash.get(attendeeID).getUsername(), userHash);
            attendeeActions.addUserContactList(attendeesHash.get(attendeeID).getUsername(), userUsername, userHash);
            if(userHash.get(attendeesHash.get(attendeeID).getUsername()) == null) {
                return false;
            }
            messageActions.createMessage(organizerID, attendeeID, message);
        }
        return true;
    }

    /***
     * Send the speaker a message
     * @param messageContent
     */
    public boolean sendSpeakersMessage(String messageContent) {
        HashMap<String, User> userHash = returnUserUsernameHashMap();
        HashMap<String, Speaker> speakersHash = speakerActions.returnUsernameHashMap();
        HashMap<String, Organizer> idHash = organizerActions.returnIDHashMap();
        String userUsername = idHash.get(organizerID).getUsername();

        for(Map.Entry<String, Speaker> entry : speakersHash.entrySet()) {
            organizerActions.addUserContactList(userUsername, entry.getValue().getUsername(), userHash);
            speakerActions.addUserContactList(entry.getValue().getUsername(), userUsername, userHash);

            if(userHash.get(entry.getValue().getUsername()) == null) {
                return false;
            }
            if (organizerActions.findUserFromUsername(userUsername).getContactsList().contains(entry.getValue().getId())) {
                messageActions.createMessage(organizerID, entry.getValue().getId(), messageContent);
            } else {
                return false;
            }
        }
        return true;
    }


    /***
     * Send all the attendees of conference a message
     * @param message
     */
    public void sendAllAttendeesMessage( String message){
        List<String> eventIDs = new ArrayList();
        for(Map.Entry<String, Event> event : eventActions.getEvents().entrySet()) {
            eventIDs.add(event.getValue().getId());
        }

        List<String> attendees = new ArrayList();
        for(String eventID: eventIDs){
            attendees.addAll(this.eventActions.getEventAttendees(eventID));
        }

        for (String attendeeID: attendees){
            this.messageActions.createMessage(this.organizerID, attendeeID, message);
        }

    }


}


