package user.organizer;

import user.User;
import loadUp.LoadUpIGateway;
import store.Storable;
import user.UserAccountActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A use case class that stores a hashmap of organizers
 */
public class OrganizerActions extends UserAccountActions implements Storable {

    private HashMap<String, Organizer> organizerHashMap = super.organizerHashMapID;
    private HashMap<String, Organizer> organizerUsernameHashMap = super.organizerHashMapUsername;
    private ArrayList<String> organizers = new ArrayList<String>();
    private LoadUpIGateway loader;
    private int counter;


    /**
     * @return ID of the organizer from the hashmap
     */
    public HashMap<String, Organizer> returnIDHashMap(){
        return organizerHashMap;
    }


    /**
     * @return ID of the organizer username from the hashmap
     */
    public HashMap<String, Organizer> returnUsernameHashMap(){
        return organizerUsernameHashMap;
    }


    /**
     * @param loader the IGateway
     * This will load up the data in the hashmap to the CSV files.
     * */
    public OrganizerActions(LoadUpIGateway loader) {
        this.loader = loader;
        getAllOrganizer(loader); // gets all messages from message.csv
        addOrganizerToHashMap(); // adds those messages to a hashmap of all messages from the csv
        this.loader = loader;
        // with message ID as key and message object as value
    }

    /**
     * @param userId the id of the attendee
     * @param username the username of the attendee
     * @param password the password of the attendee
     * @param contactsList the contact list of the attendee
     * @param eventList the list of events the attending is attending
     * @param isLogin the login status of the attendee
     * This will create a new Attendee (Will need the overloaded function for phase 2)
     * */
    public Organizer loadOrganizer(String userId, String username, String password, List<String> contactsList,
                                 List<String> eventList, boolean isLogin) {
        Organizer userOrganizer = new Organizer(userId, username, password, contactsList, eventList, isLogin, true);
        addUserIdToHashMap(userOrganizer);
        addUsernameToHashMap(userOrganizer);
        organizerHashMap.put(userId, userOrganizer);
        organizerUsernameHashMap.put(username, userOrganizer);
        return userOrganizer;
    }

    /**
     * @param username the username of the attendee
     * @param password the password of the attendee
     * This will create a new Attendee
     * */
    public Organizer createUser(String username, String password) {
        String userId = "O" + String.valueOf(organizerHashMap.size());
        Organizer userOrganizer = loadOrganizer(userId, username, password, new ArrayList<>(), new ArrayList<>(), false);
        organizerHashMap.put(userId, userOrganizer);
        organizerUsernameHashMap.put(username, userOrganizer);
        counter += 1;
        return userOrganizer;
    }

    /**
     * @param username the username of the organizer to check
     * @return true if the user with the following username exists.
     * */
    public boolean organizerExists(String username){
        return organizerUsernameHashMap.containsKey(username);
    }


    /**
     * Adds an userId to existing hashmap of userId's.
     * The key is the userId, the value is an instance of the user object.
     * @param userId the user to be added
     * */
    public void addUserIdToHashMap(Organizer userId){
        if (organizerUsernameHashMap.containsKey(userId.getId())){
            organizerUsernameHashMap.put(userId.getId(), userId);
        }
    }


    /**
     * Adds an username to existing hashmap of usernames.
     * The key is the username, the value is an instance of the user object.
     * @param user the user to be added
     * */
    protected void addUsernameToHashMap(Organizer user){

        if (organizerUsernameHashMap.containsKey(user.getUsername())){
            organizerUsernameHashMap.put(user.getUsername(), user);
        }
    }


    /**
     * Removes an userId from existing hashmap of userId's.
     * The key is the userId, the value is an instance of the user object.
     * @param removedUser the user to be removed
     * @return true if user is removed successfully, false if it has not been removed
     * */
    public boolean removeUserIdFromHashMap(Organizer removedUser){
        if (organizerUsernameHashMap.containsKey(removedUser.getId())){
            organizerUsernameHashMap.remove(removedUser.getId(), removedUser);
            return true;
        }
        return false;
    }


    /**
     * Removes an username to existing hashmap of usernames.
     * The key is the username, the value is an instance of the user object.
     * @param removedUser the user to be removed
     * @return true if user is removed successfully, false if it has not been removed
     * */
    public boolean removeUsernameFromHashMap(Organizer removedUser){
        if (organizerUsernameHashMap.containsKey(removedUser.getUsername())){
            organizerUsernameHashMap.remove(removedUser.getUsername(), removedUser);
            return true;
        }
        return false;
    }


    /**
     * Adds an user to existing list of contacts for an user.
     * @param sender the username of the user to be added
     * @param receiver the username of the user who's contact list is updated
     * @return true if user is added successfully, false if not
     * */
    public boolean addUserContactList(String receiver, String sender, HashMap<String, User> userUsernameHashMap) {
        User user = userUsernameHashMap.get(receiver);
        User userOne = userUsernameHashMap.get(sender);
        if(userOne != null && user != null) {
            if(userOne.getId() == null) {
                return false;
            }
            boolean isId;
            if(!user.getContactsList().contains(userOne.getId())){
                isId = user.getContactsList().contains(userOne.getId());
                if (userOne.getIsOrganizer() || user.getId().equals(userOne.getId()) || isId){ // organizers can now message other organizers
                    return false;
                } else {
                    List<String> toMeContacts = user.getContactsList();
                    toMeContacts.add(userOne.getId());
                    user.setContactsList(toMeContacts);
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    /**
     * Removes an user to existing list of contacts from an user.
     * @param removeUser the user to be removed
     * @param receiver the user who's contact list is updated
     * @return true if user is removed successfully, false if not
     * */
    public boolean removeUserContactList(String receiver, String removeUser) {
        User user = organizerUsernameHashMap.get(receiver);
        User userOne = organizerUsernameHashMap.get(removeUser);
        boolean isPresent = user.getContactsList().contains(userOne.getId());
        if (!isPresent) {
            return false;
        }
        else {
            List<String> toMeContacts = user.getContactsList();
            toMeContacts.remove(userOne.getId());
            user.setContactsList(toMeContacts);
            return true;
        }}


    /**
     * Adds an user to existing list of events for an user.
     * @param event the event to be added
     * @param user the user who's event list is updated
     * @return true if event is added successfully, false if not
     * */
    public boolean addEventToUser(String event, String user) {
        Organizer userOne = organizerUsernameHashMap.get(user);
        boolean isPresent = userOne.getEventList().contains(event);
        if (isPresent) {
            return false;
        }
        else{
            List<String> userEvents = userOne.getEventList();
            userEvents.add(event);
            userOne.setEventList(userEvents);
            return true;
        }
    }


    /**
     * Removes an event from existing list of events from an user.
     * @param event the event to be removed
     * @param user the username of who's event list is updated
     * @return true if event is removed successfully, false if not
     * */
    public boolean removeEventFromUser(String event, String user) {
        if (organizerUsernameHashMap != null) {
                Organizer userOne = organizerUsernameHashMap.get(user);
            if (userOne != null) {
                if (userOne.getEventList() != null) {
                    boolean isPresent = userOne.getEventList().contains(event);
                    if (isPresent) {
                        List<String> userEvents = userOne.getEventList();
                        userEvents.remove(event);
                        userOne.setEventList(userEvents);
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }


    /**
     * Finds an user from a given username
     * @param username the username given
     * @return user object from hashmap of user objects
     * */
    public User findUserFromUsername(String username){
        return organizerUsernameHashMap.get(username);
    }


    /**
     * Finds an user from a given userId
     * @param userId the userId given
     * @return user object from hashmap of user objects
     * */
    public User findUserFromId(String userId){
        return organizerHashMap.get(userId);
    }


    /**
     * Returns all the events in an user's eventList
     *
     * @param user the username of who's eventList is printed
     * @return string of all the events a user is attending
     */
    public String returnAllEvents(String user) {
        {
            User userOne = organizerUsernameHashMap.get(user);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < userOne.getEventList().size(); i++) {
                String a = userOne.getEventList().get(i);
                result.append(a).append(' ');
            }
            return result.toString();
        }
    }


    /**
     * It will get all organizers from the CSV file.
     * @param loader the userId given
     */
    private void getAllOrganizer(LoadUpIGateway loader) {
        organizers = loader.getAllOrganizers();
    }


    /**
     * This method will add the organizer to the hashmap.
     */
    private void addOrganizerToHashMap() {
        if (organizers != null) {

            for (String organizersString : organizers) {
                String[] organizerInfo = organizersString.split(",");
                ArrayList<String> eventList = new ArrayList<String>();
                ArrayList<String> contactList = new ArrayList<String>();
                String[] events = organizerInfo[4].split("%%");
                String[] contacts = organizerInfo[3].split("%%");
                List<String> emptySavedEvents = new ArrayList<>();

                for (String e : events) {
                    if (!e.equals("")) {
                        eventList.add(e);
                    }
                }

                for (String c : contacts) {
                    if (!c.equals("")) {
                        contactList.add(c);
                    }
                }
                loadOrganizer(organizerInfo[0], organizerInfo[1], organizerInfo[2], contactList,
                        eventList, Boolean.parseBoolean(organizerInfo[5]));
            }
        }
    }


    /**
     * It will be storing organizers
     * @return ArrayList<String>
     */
    public ArrayList<String> store(){
        ArrayList<String> storedOrganizer = new ArrayList<String>();
        if(organizerHashMap != null && !organizerHashMap.isEmpty()) {
            for(Map.Entry<String, Organizer> o : organizerHashMap.entrySet()) {
                storedOrganizer.add(o.getValue().stringRepresentation() + "\n");
            }
        }
        return storedOrganizer;

    }


    /**
     * It will be get the organizer ID
     * @return ArrayList<String>
     */
    public ArrayList<String> getOrganizerIds() {
        ArrayList<String> storedOrganizer = new ArrayList<String>();
        if (organizerHashMap != null && !organizerHashMap.isEmpty()) {
            for (Map.Entry<String, Organizer> o : organizerHashMap.entrySet()) {
                storedOrganizer.add(o.getValue().getId() + "\n");
            }
        }
        return storedOrganizer;
    }


    /**
     * It will be get the organizers' events
     * @param username the username of organizer
     * @return the list of event of this organizer
     */
    public List<String> getOrganizersEvents(String username){
        return returnUsernameHashMap().get(username).getEventList();
    }


}
