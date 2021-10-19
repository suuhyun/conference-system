package logout;

import user.attendee.AttendeeActions;
import user.organizer.OrganizerActions;
import user.speaker.SpeakerActions;
import user.User;


/**
 * A use case class which is responsible for validating of the user's username and password match
 * @author Mizna & Jiessie
 * @version 1
 * */

public class LogoutActions{


    /**
     * Passes true to the setter for Log in when the username and password are correct otherwise passes false.
     * @param username A string representing the username
     * @param type A string that represents the type of user
     * @param attendees the use case responsible for attendees
     * @param org the use case responsible for organizers
     * @param speakers the use case responsible for speakers
     */
    public void logout(String username, String type, AttendeeActions attendees, OrganizerActions org, SpeakerActions speakers) {
        if (type.equals("O")){
            User user = org.returnUsernameHashMap().get(username);
            user.setLogin(false);
        }
        else if (type.equals("S")){
            User user = speakers.returnUsernameHashMap().get(username);
            user.setLogin(false);
        }
        else{
            User user = attendees.returnUsernameHashMap().get(username);
            user.setLogin(false);
        }
    }
}
