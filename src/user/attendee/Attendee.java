package user.attendee;

import user.User;

import java.util.List;

public class Attendee extends User {

    private List<String> savedEventList;
    private boolean isVIP;

    /**
     * Instantiates a new attendee object
     * @param attendeeId the string unique id of this attendee
     * @param username the string username of this attendee
     * @param password the string password required for attendee to login
     * @param contactsList the list of people whom this attendee can message
     * @param eventList the list of events which this attendee is scheduled to talk
     * @param isLogin boolean whether this attendee has an account
     * @param isOrganizer boolean whether this attendee is an organizer
     * @param isVIP boolean indicating whether this attendee si a VIP or not
     * */


    public Attendee (String attendeeId, String username, String password, List<String> contactsList, List<String> eventList, List<String> savedEventList, boolean isLogin, boolean isOrganizer, boolean isVIP){
        super(attendeeId, username, password, contactsList, eventList, isLogin, isOrganizer/*, isVIP*/);
        this.savedEventList = savedEventList;
        this.isVIP = isVIP;
    }

    /**
     * Getter for the id unique to this attendee.
     * @return the id of this attendee
     * */
    public String getId() {
        return userId;
    }

    public void setSavedEventList(List<String> savedEventList) {
        this.savedEventList = savedEventList;
    }

    public List<String> getSavedEventList() {
        return savedEventList;
    }

    /**
     * Getter for the VIP status for this attendee.
     * @return boolean indicating VIP status of attendee
     * */
    public boolean getIsVIP() {return isVIP;}

    /**
     * Setter for the VIP status for this attendee.
     * @param newVIPStatus Updated VIP status for this attendee
     * */
    public void setIsVIP(boolean newVIPStatus) {this.isVIP = newVIPStatus;}
    // I'm not too sure if we're gonna need this if when the attendee signs/organizer signs them up, its final but made nonetheless

    /**
     * Returns the string representation of this attendee object
     * @return String representation of this attendee object in the way it should be stored in the csv. file
     * */
    public String stringRepresentation() {
        String contacts = contactsList.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        String events = eventList.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        String savedEventListString = savedEventList.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        return userId + "," + username + "," + password + "," + contacts + "," + events + "," + savedEventListString + "," + isLogin + "," +isOrganizer + "," +isVIP;
    }


}
