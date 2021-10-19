package user;

import java.util.List;
import java.util.Date;

/**
 * An entity class. The User object represents a user with attributes for its userId, username, login status represented
 * by isLogin, password, contactList, eventList, and organizer status represented by isOrganizer. This is the abstract
 * parent class of the entities Attendee, Organizer and Speaker.
 * @author Mizna & Jiessie
 * @version 1
 * */

public abstract class User{
    protected String username;
    protected boolean isLogin;
    protected String password;
    protected List<String> contactsList;
    protected List<String> eventList;
    protected boolean isOrganizer;
    protected String userId;
    protected Date logOutTime;
//    protected boolean isVIP;

    /**
     * instantiates a new User object with an id attribute
     * @param userId the unique id of this user
     * @param username the string representation of this user
     * @param isLogin the boolean which represents the login status of this user
     * @param password the string that is the password for this user
     * @param contactsList the list of contacts this user can message
     * @param eventList the list of events this user is attending
     * @param isOrganizer a boolean which represents whether this user is an organizer or not
     */

    public User (String userId, String username, String password, List<String> contactsList, List<String> eventList,
                 boolean isLogin, boolean isOrganizer/*, boolean isVIP*/){

        this.username = username;
        this.isLogin = isLogin;
        this.password = password;
        this.contactsList = contactsList;
        this.eventList = eventList;
        this.userId = userId;
        this.isOrganizer = isOrganizer;
//        this.isVIP = isVIP;
    }
//    /**
//     * returns isVIP
//     */
//    public boolean getIsVIP(){
//        return isVIP;
//    }

    /**
     * Getter for the login status of this user. (May be needed in Phase 2)
     * @return boolean of the user's login status
     * */
    public boolean getIsLogin(){
        return isLogin;
    }

    /**
     * Getter for the organizer status of this user
     * @return boolean of the user's organizer status
     * */
    public boolean getIsOrganizer() { return isOrganizer; }

    /**
     * Getter for the list of contacts of this user
     * @return list of the user's contacts
     * */
    public List<String> getContactsList(){
        return contactsList;
    }

    /**
     * Setter for the list of contacts of this user
     * @param contactsList The updated list of what should be set for this user
     * */
    public void setContactsList(List<String> contactsList){
        this.contactsList = contactsList;
    }

    /**
     * Getter for this user's username
     * @return the username of this user
     * */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for this user's password
     * @return the password of this user
     * */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the list of events this user is attending
     * @return the list of event's this user is attending
     * */
    public List<String> getEventList(){
        return eventList;
    }

    /**
     * Setter for the list of events this user is attending
     * @param eventList The updated list of events that should be set for this user
     * */
    public void setEventList(List<String> eventList){
        this.eventList = eventList;
    }

    /**
     * Setter for the Login status of this user
     * @param bool A boolean to what the status should be set to
     * */
    public void setLogin(boolean bool) {this.isLogin = bool;}

    /**
     * Abstract getter for the user's id
     * */
    public abstract String getId();

    /**
     * Returns the string representation of this user object
     * @return String representation of this user object int he way it should be stored in the csv. file
     * */
    public String stringRepresentation() {
        String contacts = contactsList.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        String events = eventList.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        return userId + "," + username + "," + password + "," + contacts + "," + events + "," + isLogin + "," +isOrganizer;
    }
}