package conference;

import java.util.List;

public class Conference {
    private String conferenceID;
    private String title;
    private List<String> events;
    private List<String> attendees;
    private List<String> speakers;


    /**
     * Instantiates a new conference object
     * @param conferenceID the string unique id of this conference
     * @param title the string name of this conference
     * @param events the list of strings of the ids of each event of this conference
     * */
    public Conference(String conferenceID, String title, List<String> events, List<String> attendees){
        this.conferenceID = conferenceID;
        this.title = title;
        this.events = events;
        this.attendees = attendees;
    }


    /** Returns information representing the Conference object as a string, separated by commas
     * @return string representation of this Conference entity
     */
    public String getStringRep(){
        String eventsString = events.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        String attendeesString = attendees.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        // String speakersString = speakers.toString().replaceAll("[\\[\\]]", "").replaceAll(", ", "%%");
        return conferenceID + "," + title + "," + eventsString + "," + attendeesString+ ",0";
    }


    /**
     * Return conferenceID
     * @return ID of conference
     * */
    public String getId(){
        return this.conferenceID;
    }


    /**
     * Return title
     * @return title of this conference
     * */
    public String getTitle(){
        return this.title;
    }


    /**
     * Return list of IDs of the events in this conference
     * @return list IDs of event
     * */
    public List<String> getEvents(){
        return this.events;
    }


    /**
     * return list of attendees participating in this conference
     * @return list of attendees participating in this conference
     */
    public List<String> getAttendees(){
        return this.attendees;
    }


    /**
     * return list of speakers participating in this conference
     * @return list of speakers participating in this conference
     */
    public List<String> getSpeakers(){
        return this.speakers;
    }


    /**
     * Adds the new event ID to the list of event IDs
     * @return true if the event was successfully added, and false if it couldn't be
     * */
    public boolean addEvent(String eventID){
        return this.events.add(eventID);
    }


    /**
     * Adds the new attendee ID to the list of attendee IDs
     * @return true if the attendee was successfully added, and false if it couldn't be
     * */
    public boolean addAttendee(String attendeeID){
        return this.attendees.add(attendeeID);
    }


    /**
     * Adds the new speaker ID to the list of speaker IDs
     * @return true if the speaker was successfully added, and false if it couldn't be
     * */
    public boolean addSpeakers(String speakerID){
        return this.speakers.add(speakerID);
    }


    /**
     * set speaker for event
     * @param speakers new speakers' ids
     * */
    public void setSpeaker(List<String> speakers) {
        this.speakers = speakers;
    }


    /**
     * Removes the event ID from the list of event IDs
     * @return true if the event could be removes, false if it couldn't be
     * */
    public boolean removeEvent(String eventID){
        if (this.events.contains(eventID)){
            this.events.remove(eventID);
            return true;
        } else{
            return false;
        }
    }
}
