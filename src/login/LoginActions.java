package login;

import user.organizer.Organizer;
import user.speaker.Speaker;
import user.attendee.Attendee;

/**
 * A use case class which is responsible for validating of the user's username and password match
 * @author Mizna & Jiessie
 * @version 1
 * */

public class LoginActions {

    /**
     * Returns true when the username and password are correct otherwise returns false.
     * @param username the username representing the username
     * @param password the string representing the password
     * @return A boolean if the username and password given are correct
     * */
    public String isLogin(String username, String password, parameterObjects.AccountActions accountActions) {

        if (accountActions.getOrganizerActions().organizerExists(username)){
            Organizer user = accountActions.getOrganizerActions().returnUsernameHashMap().get(username);
            if(user.getPassword().equals(password)) {
                user.setLogin(true);
                return "O";
            }
        }
        else if (accountActions.getSpeakerActions().speakerExists(username)){
            Speaker user = accountActions.getSpeakerActions().returnUsernameHashMap().get(username);
            if(user.getPassword().equals(password)) {
                user.setLogin(true);
                return "S";
            }
        }
        else if (accountActions.getAttendeeActions().attendeeExists(username)){
            Attendee user = accountActions.getAttendeeActions().returnUsernameHashMap().get(username);
            if(user.getPassword().equals(password)) {
                user.setLogin(true);
                return "A";
            }
        }
        return "";

    }
}