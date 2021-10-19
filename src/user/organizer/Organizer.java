package user.organizer;

import user.User;

import java.util.List;

public class Organizer extends User {

    /**
     * Instantiates a new attendee object
     * @param organizerId the string unique id of this organizer
     * @param username the string username of this organizer
     * @param password the string password required for organizer to login
     * @param contactsList the list of people whom this organizer can message
     * @param eventList the list of events which this organizer is scheduled to talk
     * @param isLogin boolean whether this organizer has an account
     * @param isOrganizer boolean whether this organizer is an organizer
     * */
    public Organizer (String organizerId, String username, String password, List<String> contactsList, List<String>
            eventList, boolean isLogin, boolean isOrganizer/*, boolean isVIP*/){
        super(organizerId, username, password, contactsList, eventList, isLogin, isOrganizer/*, isVIP*/); //added eventList to constructor (Jiessie) added userId to constructor
    }

    /**
     * Getter for the id unique to this organizer.
     * @return the id of this organizer
     * */
    public String getId() {
        return userId;
    }


}
