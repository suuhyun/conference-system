package event;
import java.util.List;


public class Event {
    private String eventID;
    private String title;
    private List<String> speakers;
    public String startDateTime; //  yyyy-mm-dd hh  (24 h)
    private String endDateTime; //  yyyy-mm-dd hh  (24 h)
    private List<String> attendees;
    private String roomID;
    private String conference;
    public int capacity;
    private boolean isVIP;


    /**
     * Instantiates a new attendee object
     * @param eventID the string unique id of this event
     * @param title the string name of this event
     * @param startDateTime the string of the date of this event which is yyyy-mm-dd hh an d is 24 hour
     * @param endDateTime the string of the date of this event which is yyyy-mm-dd hh an d is 24 hour
     * @param speakers the list of strings of the ids of each speaker of this event
     * @param attendees the list of people whom this attendee can event
     * @param roomID the string of the ID of the room of this event
     * @param conference the string of name pf the conference
     * */


    public Event(String eventID, String title, List<String> speakers, String startDateTime, String endDateTime,
                 List<String> attendees, String roomID, String conference, int capacity, boolean isVIP){
        this.eventID = eventID;
        this.title = title;
        this.speakers = speakers;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.attendees = attendees;
        this.roomID = roomID;
        this.conference = conference;
        this.capacity = capacity;
        this.isVIP = isVIP;

    }


    /**
     * @return isVip of Event entity
     */
    public boolean getIsVip() {
        return isVIP;
    }


    /**
     * Setter isVip of Event entity
     * @param newVip the new vip boolean
     */
    public void setVIP(boolean newVip){
        this.isVIP = newVip;
    }


    /**
     * @return String representation of Event entity
     */
    public String string(){
        // date: year month day hour
        String attendeesString = attendees.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        String speakersString = speakers.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        return eventID + "," + title + "," + speakersString + "," +
                this.startDateTime + ","+ this.endDateTime + "," + attendeesString + "," +
                roomID + "," + conference  + "," + capacity + "," + isVIP;
    }


    /**
     * return conference
     */
    public String getConference(){return this.conference;}


    /**
     * Return eventID
     * @return ID of event
     * */
    public String getId() {
        return this.eventID;
    }


    /**
     * Return title
     * @return title of event
     * */
    public String getTitle() {
        return this.title;
    }


    /**
     * Return speaker of event
     * @return ID of speaker of event
     * */
    public List<String> getSpeakers() {
        return this.speakers;
    }


    /**
     * set speaker for event
     * @param speakers new speakers' ids
     * */
    public void setSpeaker(List<String> speakers) {
        this.speakers = speakers;
    }


    /**
     * Return capacity of event
     * @return capacity of event
     * */
    public int getCapacity() {
        return this.capacity;
    }


    /**
     * set capacity for event
     * @param newCapacity new capacity of event
     * */
    public void setCapacity(int newCapacity) {
        this.capacity = newCapacity;
    }


    /**
     * Return dateTime of event
     * @return dateTime of event
     * */
    public String getDateTime() {
        return startDateTime + " to " + endDateTime;
    }


    /**
     * Return startHour of event
     * @return startHour of event
     * */
    public String getStartDateTime() {
        return this.startDateTime;
    }


    /**
     * set startHour for event
     * @param newDateTime date and hour event starts  yyyy-mm-dd hh  (24 h)
     * */
    public void setStartTime(String newDateTime) {
        this.startDateTime = newDateTime;
    }


    /**
     * Return endHour of event
     * @return startHour of event
     * */
    public String getEndDateTime() {
        return this.endDateTime;
    }


    /**
     * set endHour for event
     * @param newDateTime date and hour event starts  yyyy-mm-dd hh  (24 h)
     * */
    public void setEndDateTime(String newDateTime) {
        this.endDateTime = newDateTime;
    }


    /**
     * Return List of attendees of an event
     * @return date and hour event starts  yyyy-mm-dd hh  (24 h)
     * */
    public List<String> getAttendees() {
        return attendees;
    }


    /**
     * Set List of attendees of an event
     * @param newAttendees list of IDs of the new attendee
     * */
    public void setAttendees(List<String> newAttendees) {
        this.attendees =  newAttendees;
    }


    /**
     * Add attendeeID to list of attendees, return True if attendee is successfully added
     * (ie was not already in the list of attendees)
     * @param attendeeID ID of the attendee to be added
     * @return if attendee is successfully added to list of attendees
     * */
    public boolean addAttendee(String attendeeID){
        if (!this.attendees.contains(attendeeID)){
            this.attendees.add(attendeeID);
            return true;
        } else{
            return false;
        }
    }


    /**
     * Remove attendeeID to list of attendees, return True if attendee is successfully removed
     * (ie was already in the list of attendees, and is now no longer there)
     * @param attendeeID ID of the attendee to be removed
     * @return if attendee is successfully removed to list of attendees
     * */
    public boolean removeAttendee(String attendeeID){
        if (this.attendees.contains(attendeeID)){
            this.attendees.remove(attendeeID);
            return true;
        } else{
            return false;
        }
    }


    /**
     * Return location of event (room)
     * @return location of event
     * */
    public String getRoomID() {
        return this.roomID;
    }

}

