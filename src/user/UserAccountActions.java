package user;

import user.attendee.Attendee;
import user.organizer.Organizer;
import user.speaker.Speaker;

import java.util.HashMap;



/**
 * A use case class that stores a hashmap of users and can add and remove users from the hashmap.
 * Parent class to AttendeeActions, OrganizationActions and SpeakerActions. This class collaborates with user,
 * message, event and conference entity.
 * @author multiple
 * @version 1
 */

public class UserAccountActions {
    // ID HashMaps
    protected HashMap<String, Attendee> attendeeHashMapID = new HashMap<String, Attendee>();
    protected HashMap<String, Organizer> organizerHashMapID = new HashMap<String, Organizer>();
    protected HashMap<String, Speaker> speakerHashMapID = new HashMap<String, Speaker>();

    //Username HashMaps
    protected HashMap<String, Attendee> attendeeHashMapUsername = new HashMap<String, Attendee>();
    protected HashMap<String, Organizer> organizerHashMapUsername = new HashMap<String, Organizer>();
    protected HashMap<String, Speaker> speakerHashMapUsername = new HashMap<String, Speaker>();

    protected HashMap<String, User> master = new HashMap<String, User>();

    public HashMap<String, User> getMasterUsernameHashMap() {
        if (!attendeeHashMapUsername.isEmpty()) {
            master.putAll(attendeeHashMapUsername);
        }
        if (!organizerHashMapUsername.isEmpty()) {
            master.putAll(organizerHashMapUsername);
        }
        if (!speakerHashMapUsername.isEmpty()) {
            master.putAll(speakerHashMapUsername);
        }
        return master;
    }

    public HashMap<String, Attendee> getAttendeeHashmapID() {
        return attendeeHashMapID;
    }

    public HashMap<String, Organizer> getOrganizerHashMapID() {
        return organizerHashMapID;
    }

    public HashMap<String, Speaker> getSpeakerHashMapID() {
        return speakerHashMapID;
    }

    public HashMap<String, Organizer> getOrganizerHashMapUsername() {
        return organizerHashMapUsername;
    }

    public HashMap<String, Attendee> getAttendeeHashMapUsername() {
        return attendeeHashMapUsername;
    }

    public HashMap<String, Speaker> getSpeakerHashMapUsername() {
        return speakerHashMapUsername;
    }

    /**
     * Adds an user to existing list of contacts for an user.
     * @param addMe the user to be added
     * @param toMe the user who's contact list is updated
     * @return true if user is added successfully, false if not
     * */

    public boolean addUserContactList(String toMe, String addMe, HashMap<String, User> userHashMap) {
        return false;
    }

    /**
     * Allows them create to users
     *\
     * @param username  the username
     * @param password the password
     * @return User based on the username and password created
     */
    public User createUser(String username, String password){return null;}


    /**
     * Removes an user to existing list of contacts from an user.
     * @param removeMe the user to be removed
     * @param toMe the user who's contact list is updated
     * @return true if user is removed successfully, false if not
     * */

    public boolean removeUserContactList(String toMe, String removeMe) {
        return false;
    }


    /**
     * Adds an user to existing list of events for an user.
     * @param event the event to be added
     * @param user the user who's event list is updated
     * @return true if event is added successfully, false if not
     * */

    public boolean addEventToUser(String event, String user){
        return false;
    }


    /**
     * Removes an event from existing list of events from an user.
     * @param event the event to be removed
     * @param user the user who's event list is updated
     * @return true if event is removed successfully, false if not
     * */

    public boolean removeEventFromUser(String event, String user) {
        return false;
    }

    /**
     * Prints all the events in an user's eventList
     * @param user the user who's eventList is printed
     * @return string of all the events a user is attending
     * */

    public String returnAllEvents(String user) {
        return null;
    }

    /**
     * Finds an user from a given username
     * @param username the username given
     * @return user object from hashmap of user objects
     * */
    public User findUserFromUsername(String username){
        return null;
    }

    /**
     * Finds an user from a given userId
     * @param userId the userId given
     * @return user object from hashmap of user objects
     * */
    public User findUserFromId(String userId){
        return null;
    }

























//        /**
//     * Adds an userId to existing hashmap of userId's.
//     * The key is the userId, the value is an instance of the user object.
//     * @param addMe the user to be added
//     * */
//    public abstract void addUserIdToHashMap(User addMe);
////    {
////        if (usersHashMap.containsKey(addMe.getId())){
////            usersHashMap.put(addMe.getId(), addMe);
////        }
////    }
//
//    /**
//     * Adds an username to existing hashmap of usernames.
//     * The key is the username, the value is an instance of the user object.
//     * @param addMe the user to be added
//     * @return void
//     * */
//    protected abstract void addUsernameToHashMap(User addMe);
////    {
////
////        if (usersHashMap.containsKey(addMe.getUsername())){
////            usersHashMap.put(addMe.getUsername(), addMe);
////        }
////
////    }
//
//    /**
//     * Removes an userId from existing hashmap of userId's.
//     * The key is the userId, the value is an instance of the user object.
//     * @param removeMe the user to be removed
//     * @return true if user is removed successfully, false if it has not been removed
//     * */
//    public abstract boolean removeUserIdFromHashMap(User removeMe);
////    {
////        if (usersHashMap.containsKey(removeMe.getId())){
////            usersHashMap.remove(removeMe.getId(), removeMe);
////            return true;
////        }
////        return false;
////    }
//
//    /**
//     * Removes an username to existing hashmap of usernames.
//     * The key is the username, the value is an instance of the user object.
//     * @param removeMe the user to be removed
//     * @return true if user is removed successfully, false if it has not been removed
//     * */
//
//    public abstract boolean removeUsernameFromHashMap(User removeMe);
////    {
////        if (usersHashMap.containsKey(removeMe.getUsername())){
////            usersHashMap.remove(removeMe.getUsername(), removeMe);
////            return true;
////        }
////        return false;
////    }
//
//    /**
//     * Adds an user to existing list of contacts for an user.
//     * @param addMe the user to be added
//     * @param toMe the user who's contact list is updated
//     * @return true if user is added successfully, false if not
//     * */
//
//    public abstract boolean addUserContactList(String toMe, String addMe);
////    {
////        User user = usersHashMap.get(toMe);
////        User userOne = usersHashMap.get(addMe);
////        boolean isId = user.getContactsList().contains(userOne.getId());
////        if (isId) {
////            return false;
////        }
////        else {
////            List<String> toMeContacts = user.getContactsList();
////            toMeContacts.add(userOne.getId());
////            user.setContactsList(toMeContacts);
////            return true;
////        }}
//
//    /**
//     * Removes an user to existing list of contacts from an user.
//     * @param removeMe the user to be removed
//     * @param toMe the user who's contact list is updated
//     * @return true if user is removed successfully, false if not
//     * */
//
//    public abstract boolean removeUserContactList(String toMe, String removeMe);
////    {
////        User user = usersHashMap.get(toMe);
////        User userOne = usersHashMap.get(removeMe);
////        boolean isPresent = user.getContactsList().contains(userOne.getId());
////        if (!isPresent) {
////            return false;
////        }
////        else {
////            List<String> toMeContacts = user.getContactsList();
////            toMeContacts.remove(userOne.getId());
////            user.setContactsList(toMeContacts);
////            return true;
////        }}
//
//    /**
//     * Adds an user to existing list of events for an user.
//     * @param event the event to be added
//     * @param user the user who's event list is updated
//     * @return true if event is added successfully, false if not
//     * */
//
//    public abstract boolean addEventToUser(String event, String user);
////    {
////        User userOne = usersHashMap.get(user);
////        boolean isPresent = userOne.getEventList().contains(event);
////        if (isPresent) {
////            return false;
////        }
////        else{
////            List<String> userEvents = userOne.getEventList();
////            userEvents.add(event);
////            userOne.setEventList(userEvents);
////            return true;
////        }}
//
//    /**
//     * Removes an event from existing list of events from an user.
//     * @param event the event to be removed
//     * @param user the user who's event list is updated
//     * @return true if event is removed successfully, false if not
//     * */
//
//    public abstract boolean removeEventFromUser(String event, String user);
////    {
////        User userOne = usersHashMap.get(user);
////            boolean isPresent = userOne.getEventList().contains(event);
////            if (isPresent) {
////                List<String> userEvents = userOne.getEventList();
////                userEvents.remove(event);
////                userOne.setEventList(userEvents);
////                return true;
////            }
////            return false;
////    }
//
//    /**
//     * Prints all the events in an user's eventList
//     * @param user the user who's eventList is printed
//     * @return string of all the events a user is attending
//     * */
//
//    public abstract String returnAllEvents(String user);
////    {
////        User userOne = usersHashMap.get(user);
////        StringBuilder result = new StringBuilder();
////        for (int i = 0; i < userOne.getEventList().size(); i++){
////           // System.out.println(user.getEventList().get(i));
////            String a = userOne.getEventList().get(i);
////            result.append(a).append(' ');
////    }
////        return result.toString();
////}
//    /**
//     * Finds an user from a given username
//     * @param username the username given
//     * @return user object from hashmap of user objects
//     * */
//    public abstract User findUserFromUsername(String username);
////    {
////        return usersHashMap.get(username);
////    }
//
//    /**
//     * Finds an user from a given userId
//     * @param userId the userId given
//     * @return user object from hashmap of user objects
//     * */
//    public abstract User findUserFromId(String userId);
////    {
////        return usersHashMap.get(userId);
////        }






































}
