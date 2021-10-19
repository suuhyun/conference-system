package user.speaker;


import user.User;

import java.util.List;

/**
 * A use case class that stores a list of rooms and can add/remove rooms.
 * @author multiple
 * @version 1
 * */
public class Speaker extends User {

    /**
     * Instantiates a new speaker object
     * @param speakerId the string unique id of this speaker
     * @param username the string username of this speaker
     * @param password the string password required for speaker to login
     * @param contactsList the list of people whom this speaker can message
     * @param eventList the list of events which this speaker is scheduled to talk
     * @param isLogin boolean whether this speaker has an account
     * @param isOrganizer boolean whether this speaker is an organizer
     * */
    public Speaker(String speakerId,String username, String password, List<String> contactsList, List<String>
            eventList, boolean isLogin, boolean isOrganizer/*, boolean isVIP*/){
        super(speakerId, username, password, contactsList, eventList, isLogin, isOrganizer/*, isVIP*/);
    }


    /**
     * Getter for the id unique to this speaker.
     * @return the id of this speaker
     * */
    public String getId() {
        return userId;
    }
}
