package user;

import conference.ConferenceActions;
import user.attendee.AttendeeActions;
import user.organizer.OrganizerActions;
import user.speaker.SpeakerActions;
import event.Event;
import message.Message;
import message.MessageActions;
import room.Room;
import room.RoomActions;
import event.EventActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A controller class for users. UserController is a parent class to OrganizerController, AccountController and
 * SpeakerController.
 * @author Jiessie and Mizna
 * @version 1
 * */

public class UserController {
    private UserAccountActions user; // = getUserAccountActions();
    private MessageActions messageActions; // = super.getMessages();
    private EventActions eventActions;  //= super.getEvents();
    private RoomActions roomActions; // = super.getRooms();

    private AttendeeActions attendeeActions; // = super.getAttendees();
    private OrganizerActions organizerActions; // = super.getOrganizers();
    private SpeakerActions speakerActions; // = super.getSpeakers();

    private ConferenceActions conferenceActions; // = super.getConferences()
    private HashMap<String, User> usernameHashmap = new HashMap<String, User>();
    private HashMap<String, User> userIdHashmap = new HashMap<String, User>();


    /**
     * Instantiates a new UserController object. Creates an instance of UserAccountActions, MessageActions, EventActions
     * AttendeeActions, RoomActions.
     */
    public UserController(parameterObjects.EventSystemActions eventSystemActions, parameterObjects.AccountActions accountActions, char userType) {
        this.messageActions = eventSystemActions.getMessageActions();
        this.eventActions = eventSystemActions.getEventActions();
        this.roomActions = eventSystemActions.getRoomActions();
        this.conferenceActions = eventSystemActions.getConferenceActions();
        if (userType == 'o') {
            this.user = accountActions.getOrganizerActions();
        } else if (userType == 's') {
            this.user = accountActions.getSpeakerActions();
        } else if (userType == 'a') {
            this.user = accountActions.getAttendeeActions();
        }

        this.attendeeActions = accountActions.getAttendeeActions();
        this.organizerActions = accountActions.getOrganizerActions();
        this.speakerActions = accountActions.getSpeakerActions();
        //this.conferenceActions = conference;
    }


    public HashMap<String, User> returnUserUsernameHashMap() {
        if (!(attendeeActions == null) &&!attendeeActions.getAttendeeHashMapUsername().isEmpty()) {
            usernameHashmap.putAll(attendeeActions.getAttendeeHashMapUsername());
        }
        if (!(organizerActions == null)&&!organizerActions.getOrganizerHashMapUsername().isEmpty()) {
            usernameHashmap.putAll(organizerActions.getOrganizerHashMapUsername());
        }
        if (!(speakerActions == null)&&!speakerActions.getSpeakerHashMapUsername().isEmpty()) {
            usernameHashmap.putAll(speakerActions.getSpeakerHashMapUsername());
        }
        return usernameHashmap;

    }
//    public HashMap<String, User> returnUserUsernameHashMap() {
//        if (!(attendeeActions == null) && !attendeeActions.returnUsernameHashMap().isEmpty()) {
//            usernameHashmap.putAll(attendeeActions.returnUsernameHashMap());
//        }
//        if (!(organizerActions == null) && !organizerActions.returnUsernameHashMap().isEmpty()) {
//            usernameHashmap.putAll(organizerActions.returnUsernameHashMap());
//        }
//        if (!(speakerActions == null) && !speakerActions.returnUsernameHashMap().isEmpty()) {
//            usernameHashmap.putAll(speakerActions.returnUsernameHashMap());
//        }
//        return usernameHashmap;
//
//    }

    public HashMap<String, User> returnUserIDHashMap() {
        if (!user.getAttendeeHashmapID().isEmpty()) {
            userIdHashmap.putAll(user.getAttendeeHashmapID());
        }
        if (!user.getOrganizerHashMapID().isEmpty()) {
            userIdHashmap.putAll(user.getOrganizerHashMapID());
        }
        if (!user.getSpeakerHashMapID().isEmpty()) {
            userIdHashmap.putAll(user.getSpeakerHashMapID());
        }
        return userIdHashmap;
    }

//    public HashMap<String, User> returnUserIDHashMap() {
//        if (!attendeeActions.returnIDHashMap().isEmpty()) {
//            userIdHashmap.putAll(attendeeActions.returnIDHashMap());
//        }
//        if (!organizerActions.returnIDHashMap().isEmpty()) {
//            userIdHashmap.putAll(organizerActions.returnIDHashMap());
//        }
//        if (!speakerActions.returnIDHashMap().isEmpty()) {
//            userIdHashmap.putAll(speakerActions.returnIDHashMap());
//        }
//        return userIdHashmap;
//    }



    /***
     * return if the username already belongs to a user
     * @param username potential username 
     * @return true if the username already exists
     */
    public boolean usernameExists(String username) {
        return returnUserUsernameHashMap().containsKey(username);
    }


    /**
     * Sends a message to a user
     *
     * @param content  the message to be sent
     * @param receiver the user who will be getting the message
     * @param sender   the user who is sending the message
     * @return boolean true if message was successfully sent, false if it was not
     */
    public boolean sendMessage(String sender, String receiver, String content) {
        if (user != null) {
            HashMap<String, User> usernameHash = returnUserUsernameHashMap();
            // add receiver to contact list of sender
            user.addUserContactList(sender, receiver, usernameHash);
            user.addUserContactList(receiver, sender, usernameHash);
            if (usernameHash.get(receiver) == null) {
                return false;
            }
            String receiverId = usernameHash.get(receiver).getId();
            String senderId = usernameHash.get(sender).getId();
            if (user.findUserFromUsername(sender).getContactsList().contains(receiverId)) {
                messageActions.createMessage(senderId, receiverId, content);
                return true;
            }
            return false;
        }
        return false;
    }


    /**
     * Adds a user to the contact list of another user
     *\
     * @param toMe  the username who's contact list that is updated
     * @param addMe the username who will be added
     * @return boolean true if contact was successfully added, false if it was not
     */
    public boolean addContact(String addMe, String toMe) { // toMe should be a username
        if (user != null) {
            HashMap<String, User> userUsernameHashMap = returnUserUsernameHashMap();
            User me = user.findUserFromUsername(toMe);
            return user.addUserContactList(toMe, addMe, userUsernameHashMap);
        }
        return false;
    }

    public boolean deleteContact(String removeMe, String toMe) {
        return user.removeUserContactList(toMe, removeMe);
    }


    /**
     * Shows the messages from one user to another
     *
     * @param toMe   the user receiving the messages
     * @param fromMe the user who is sending the messages
     * @return array of a list of messages
     */
    public ArrayList<ArrayList<String>> viewMessages(String fromMe, String toMe) {
        ArrayList<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();
        HashMap<String, User> usernameHash = returnUserUsernameHashMap();
        List<Message> messageList = messageActions.getMessages(fromMe, toMe);
        for (Message message : messageList) {
            ArrayList<String> info = new ArrayList<String>();
            HashMap<String, User> userIdHash = returnUserIDHashMap();
            String receiverUsername = userIdHash.get(message.getReceiverId()).getUsername();
            String senderUsername = userIdHash.get(message.getSenderId()).getUsername();
            if (senderUsername != null) {
                info.add(senderUsername);
            }
            if (receiverUsername != null) {
                info.add(receiverUsername);
            }
            if (message.getTimeSent() != null) {
                info.add(message.getTimeSent());
            }
            if (message.getMessage() != null) {
                info.add(message.getMessage());
            }
            if (info.get(0) != null && info.get(1) != null && info.get(2) != null && info.get(3) != null) {
                messages.add(info);
            }
        }
        return messages;
    }


    /**
     * Shows the contacts of a user
     *
     * @param userid the user who wants to see their contacts
     * @return array of other usernames who are in their contacts
     */

    public ArrayList<String> viewContacts(String userid) {
        ArrayList<String> contacts = new ArrayList<String>();
        if (user != null) {
            List<String> usersList = user.findUserFromId(userid).getContactsList();
            for (String id : usersList) {
                contacts.add(returnUserIDHashMap().get(id).getUsername());
            }
            // TODO i think theres something wrong with the for loop here
        }
        return contacts;
    }


    /**
     * Adds an event to a user and an attendee to an event
     *
     * @param eventName the event the user wants to attend
     * @param userName  the attendee who wants to attend an event
     * @return list of booleans if users can attend event or not
     */

    public List<Boolean> signupEvent(String eventName, String userName) {
        if (eventActions.getEventNames().containsKey(eventName)) {
            String eventId = eventActions.getEventFromName(eventName).getId();
            List<Boolean> checks = new ArrayList<Boolean>();
            if (!eventActions.eventExists(eventId)) {
                checks.add(false);
                return checks;
            }
            Event e1 = eventActions.getEvent(eventId);
            User a1 = returnUserUsernameHashMap().get(userName);

            if (!checkConflictSpots(eventId) && !checkConflictTime(userName, eventId)) {
                eventActions.addAttendee(e1.getId(), a1.getId());
                a1.getEventList().add(eventId);
                checks.add(true);
                return checks;
            }
            checks.add(false);
            if (!checkConflictSpots(eventId)) {
                checks.add(true);
            } else {
                checks.add(false);
            }
            if (!checkConflictTime(userName, eventId)) {
                checks.add(true);
            } else {
                checks.add(false);
            }
            return checks;
        }
        List<Boolean> checks = new ArrayList<>();
        checks.add(false);
        return checks;
    }


    /**
     * Shows the events a given user is attending
     *
     * @param user the user who wants to see their events
     * @return list of the events that a user is attending in the form of a list
     * with the string representation of each aspect (title, dateTime, etc)
     */

    public List<List<String>> viewOwnSchedule(String user) {
        User a1 = returnUserUsernameHashMap().get(user);
        List<String> eventList = a1.getEventList();
        List<List<String>> scheduleList = new ArrayList<List<String>>();
        if (eventActions != null) {
            for (String event : eventList) {
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
        }
        return scheduleList;
    }


    /**
     * Shows the events a given user could be attending
     *
     * @param user the user who wants to see possible events to attend
     * @return string of events that an user could attend
     */

    public List<List<String>> viewAvailableSchedule(String user, String conferenceTitle) {
        List<String> conferenceEvents = conferenceActions.returnTitleHashMap().get(conferenceTitle).getEvents();
        // Set<String> allEvents = eventActions.getEvents().keySet();
        List<String> availableS = new ArrayList<>();
        // List<String> targetList = new ArrayList<>(allEvents);

        for (String s : conferenceEvents) {
            if (!checkConflictTime(user, s) && !checkConflictSpots(s)) {
                if (!eventActions.getEvent(s).getIsVip()){
                    availableS.add(s);
                }
            }
        }
        List<List<String>> scheduleList = new ArrayList<List<String>>();
        for (String event : availableS) {
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

    /**
     * Allows them to view VIP Events
     *\
     * @param user  the username who's contact list that is updated
     * @param conferenceTitle the username who will be added
     * @return List<List<String>> true if contact was successfully added, false if it was not
     */
    public List<List<String>> viewVIPEvents(String user, String conferenceTitle){
        List<String> conferenceEvents = conferenceActions.returnTitleHashMap().get(conferenceTitle).getEvents();
        // Set<String> allEvents = eventActions.getEvents().keySet();
        List<String> availableS = new ArrayList<>();
        // List<String> targetList = new ArrayList<>(allEvents);

        for (String s : conferenceEvents) {
            if (!checkConflictTime(user, s) && !checkConflictSpots(s)) {
                if (eventActions.getEvent(s).getIsVip()){
                    availableS.add(s);
                }
            }
        }
        List<List<String>> scheduleList = new ArrayList<List<String>>();
        for (String event : availableS) {
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

    /**
     * Shows the spots available in an event
     *
     * @param eventID the event that is given
     * @return int of the number of spots available
     */

    public int spotsAvailable(String eventID) {
        String rooms = eventActions.getEvent(eventID).getRoomID();

        Room r1 = roomActions.returnHashMap().get(rooms);

        return r1.getCapacity() - eventActions.getEvent(eventID).getAttendees().size();

    }


    /**
     * Checks if users can attend an event or not
     *
     * @param username the user who wants to see if they can attend an event
     * @param eventID  the event the user wants to attend
     * @return boolean if user can attend event
     */

    private boolean checkConflictTime(String username, String eventID) {
        //return true if there is a conflict
        String timeEvent = eventActions.getEvent(eventID).getDateTime();
        if (user != null) {

            User u = user.findUserFromUsername(username);

            for (int i = 0; i < u.getEventList().size(); i++) {

                String eventId = u.getEventList().get(i);

                String time = eventActions.getEvent(eventId).getDateTime();

                if (time.equals(timeEvent)) {
                    return true;
                }
            }

            return false;
        }
        return false;
    }


    /**
     * Checks if event is full or not
     *
     * @param eventID the event that is being checked
     * @return boolean if event is full
     */
    private boolean checkConflictSpots(String eventID) {
        return spotsAvailable(eventID) == 0;

    }


    /**
     * To be overridden by OrganizerController
     *
     * @param eventID the event to be cancelled
     * @return boolean if event was cancelled or not
     */
    public boolean cancelEvent(String eventID) {
        return false;
    }


    /**
     * To be overridden by AttendeeController
     *
     * @param eventID  the event to be removed
     * @param username the user who wants to leave the event
     * @return boolean if event was removed or not
     */
    public boolean leaveEvent(String eventID, String username) {
        return false;
    }

    /***
     * check if a user has any other events booked between the startDateTime and endDateTime
     * @param username of user to check if they are available
     * @param startDateTime start time of event
     * @param endDateTime end time of event
     * @return true if there is a conflict
     */
    public boolean checkTimeConflict(String username, String startDateTime, String endDateTime) {
        //return true if there is a conflict
        //String timeEvent = e.getEvent(event).getDateTime();
        if (user != null) {
            User u = user.findUserFromUsername(username);

            if ((u != null) && u.getEventList() != null) {
                for (int i = 0; i < u.getEventList().size(); i++) {
                    String eventID = u.getEventList().get(i);
                    if(eventActions.timeConflict(eventID, startDateTime, endDateTime)){
                        return true;
                    }

                }
                // return false;
            }
            // return false;
        }
        return false;
    }


    /***
     * Check if event exists
     * @param eventName
     *
     * @return true if an event with that event name exists
     */
    public boolean checkEvent(String eventName) {
        return (eventActions.getEventNames().containsKey(eventName));
    }


    /***
     * Check if event has attendees
     * @param eventName
     *
     * @return true is the event has more than 0 attendees
     */
    public boolean eventHasAttendees(String eventName) {
        return (eventActions.getEventFromName(eventName).getAttendees().size() > 0);
    }

    /***
     * Check if any speakers exists in the system
     *
     * @return true if speaker exists in the hashmap
     */
    public boolean speakersExist() {
        return (user.getSpeakerHashMapID().size() > 0);
    }
//    /***
//     * Check if any speakers exists in the system
//     */
//    public boolean speakersExist() {
//        return (speakerActions.returnIDHashMap().size() > 0);
//    }

//    public Date dateLoggedOut(String userName) {
//        User u = user.findUserFromUsername(userName);
//        return u.getLogOutTime();
//    }

}






