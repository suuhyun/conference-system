package conference;

import loadUp.LoadUpIGateway;
import store.Storable;

import java.util.*;

public class ConferenceActions implements Storable {
    public HashMap<String, Conference> conferences = new HashMap<String, Conference>();
    private HashMap<String, Conference> conferenceTitlesHash = new HashMap<String, Conference>();
    private LoadUpIGateway loader;
    private List<String> conferencesList;

    /**
     * Instantiates ConferenceActions
     * @param loader LoadUpIGateway for loading up of conference objects
     * */
    public ConferenceActions(LoadUpIGateway loader) {
        loadAllConferences(loader);
        addLoadedToHashMap();
        this.loader = loader;
    }


    /***
     * return hashmap of all conferences in existence. key: conferenceID value: The conference object itself
     * @return hashmap of all conferences in existence with conferenceID as key
     */
    public HashMap<String, Conference> returnIDHashMap() {
        return conferences;
    }


    /***
     * return hashmap of all conferences in existence. key: the conference's title value: The conference object itself
     * @return hashmap of all conferences in existence with the conference's title as key
     */
    public HashMap<String, Conference> returnTitleHashMap() {
        return conferenceTitlesHash;
    }


    /***
     * Adds an event to the conference object
     * @param conferenceTitle The title of the conference to which the event will be aded to
     * @param eventId the id of the event to be added
     * @return True if the event could be added and false if it could not be
     */
    public boolean addEvent(String conferenceTitle, String eventId) {
        if (conferenceTitlesHash != null) {
            conferenceTitlesHash.get(conferenceTitle).addEvent(eventId);
            return true;
        }
        return false;
    }


    /**
     * add attendee to a conference
     * @param conferenceTitle the title of conference
     * @param attendee the username of attendee
     * @return true if and only if attendee was added to conference
     */
    public boolean addAttendee(String conferenceTitle, String attendee){
        if (conferenceTitlesHash != null || !conferenceTitlesHash.get(conferenceTitle).getAttendees().contains(attendee)){
            conferenceTitlesHash.get(conferenceTitle).addAttendee(attendee);
            return true;
        }
        return false;
    }


    /**
     * Returns whether the user is an attendee
     * @param conferenceTitle the title of conference
     * @param attendeeUsername the username of attendee
     * @return true if and only if attendee was added to conference
     */
    public boolean isAttendee(String conferenceTitle, String attendeeUsername){
        return conferenceTitlesHash.get(conferenceTitle).getAttendees().contains(attendeeUsername);
    }


    /***
     * set speaker of an event
     * @param conferenceID if of event
     * @param speakerID id of new speaker
     */
    public void setSpeaker(String conferenceID, List<String> speakerID) {
        this.conferences.get(conferenceID).setSpeaker(speakerID);
    }


    /**
     * returns the list of all conferences (title and events)
     * @return returns the list of all conferences (title and events)
     */
    public ArrayList<List<String>> returnConferences() {
        ArrayList<List<String>> stringRepConferences = new ArrayList<>();
        for (Map.Entry<String, Conference> entry : conferenceTitlesHash.entrySet()) {
            List<String> stringRepConference = new ArrayList<String>();
            Conference conference = entry.getValue();
            stringRepConference.add(conference.getTitle());
            String events = "";
            for (String eventID : conference.getEvents()) {
                events += eventID + ",";
            }
            stringRepConference.add(events);
            stringRepConferences.add(stringRepConference);
        }
        return stringRepConferences;
    }


    /**
     * returns the list of all conferences (title and events) that attendee is participating in
     * @param attendeeID the id of the attendee in question
     * @return returns the list of all conferences (title and events) that attendee is participating in
     */
    public ArrayList<List<String>> returnAttendedConferences(String attendeeID){
        ArrayList<List<String>> stringRepConferences = new ArrayList<>();
        for (Map.Entry<String, Conference> entry : conferenceTitlesHash.entrySet()) {
            List<String> stringRepConference = new ArrayList<String>();
            Conference conference = entry.getValue();
            if (conference.getAttendees().contains(attendeeID)){
                stringRepConference.add(conference.getTitle());
                //            stringRepConference.add(conference.getStartDateTime());
                //            stringRepConference.add(conference.getEndDateTime());
                String events = "";
                for (String eventID : conference.getEvents()) {
                    events += eventID + ",";
                }
                stringRepConference.add(events);
                stringRepConferences.add(stringRepConference);
            }

        }
        return stringRepConferences;
    }


    /**
     * returns the list of all conferences (title and events) that attendee is NOT participating in
     * @param attendee the attendee name in question
     * @return list of all conferences (title and events) that attendee is NOT participating in
     */
    public ArrayList<List<String>> returnAvailableConferences(String attendee){
        ArrayList<List<String>> stringRepConferences = new ArrayList<>();
        for (Map.Entry<String, Conference> entry : conferenceTitlesHash.entrySet()) {
            List<String> stringRepConference = new ArrayList<String>();
            Conference conference = entry.getValue();
            if (!conference.getAttendees().contains(attendee)){
                stringRepConference.add(conference.getTitle());
                String events = "";
                for (String eventID : conference.getEvents()) {
                    events += eventID + ",";
                }
                stringRepConference.add(events);
                stringRepConferences.add(stringRepConference);
            }

        }
        return stringRepConferences;
    }


    /***
     * Get list of all conferences (as string representations) from the loader
     */
    private void loadAllConferences(LoadUpIGateway loader) {
        conferencesList = loader.getConferencesList();
    }


    /***
     * Creates conference objects from their string representations and adds them to conferences hash map
     */
    private void addLoadedToHashMap() {
        if (conferencesList != null && !conferencesList.isEmpty()) {
            for (String conference : conferencesList) {
                String[] conferenceItems = conference.split(",");
                List<String> conferenceEvents = new ArrayList<>(Arrays.asList(conferenceItems[2].split("%%")));
                List<String> conferenceAttendees = new ArrayList<>(Arrays.asList(conferenceItems[3].split("%%")));
                ArrayList<String> eventList = new ArrayList<String>();
                ArrayList<String> attendeeList = new ArrayList<String>();

                for (String e : conferenceEvents) {
                    if (!e.equals("")) {
                        eventList.add(e);
                    }
                }
                for (String a : conferenceAttendees) {
                    if (!a.equals("")) {
                        attendeeList.add(a);
                    }
                }
                loadConference(conferenceItems[0], conferenceItems[1], eventList, attendeeList/*, conferenceSpeakers*/);
            }
        }
    }


    /***
     * @return True if conference exists, false if it doesn't.
     */
    public boolean conferenceExists(String conferenceTitle) {
        return conferenceTitlesHash.containsKey(conferenceTitle);
    }


    /**
     * determines whether a given attendee is not participating in a given conference, and whether the conference exists
     * @param conferenceTitle title of conference
     * @param attendee username of attendee
     * @return true if and only if given attendee is not participating in the given conference and the conference exists
     */
    public boolean conferenceAvailable(String conferenceTitle, String attendee){
        if (conferenceTitlesHash.containsKey(conferenceTitle) && !conferenceTitlesHash.get(conferenceTitle).getAttendees().contains(attendee)){
            return true;
        }
        return false;
    }


    /**
     * determines whether conference exists and a given attendee is participating in a given conference
     * @param conferenceTitle title of conference
     * @param attendee username of attendee
     * @return true if and only if conference exists and attendee is participating in the conference
     */
    public boolean conferenceAttended(String conferenceTitle, String attendee){
        return conferenceTitlesHash.containsKey(conferenceTitle) && conferenceTitlesHash.get(conferenceTitle).getAttendees().contains(attendee);
    }


    /***
     * Creates a conference object
     * @param title The title of the conference
     * @param events The list of event IDs
     */
    public void createConference(String title, List<String> events) {
        String conferenceId = "C" + String.valueOf(conferences.size());
        loadConference(conferenceId, title, events, new ArrayList<>());
    }


    /***
     * Puts conferences into conferences and conferenceTitlesHash
     * @param conferenceId Id of the conference
     * @param title Title of the conference
     * @param events List of event IDs of the events in this conference
     * @param attendees List of attendee IDs of the attendees for this conference
     * @return conference object that was just loaded into the HashMaps
     */
    private Conference loadConference(String conferenceId, String title, List<String> events, List<String> attendees) {
        Conference newConference = new Conference(conferenceId, title, events, attendees);
        conferences.put(conferenceId, newConference);
        conferenceTitlesHash.put(title, newConference);
        return newConference;
    }


    /***
     * Will be called to store conferences
     * @return List of string representations of all the conferences in existance
     */
    public ArrayList<String> store() {
        ArrayList<String> storedConferences = new ArrayList<String>();
        for (Map.Entry<String, Conference> conference : conferences.entrySet()) {
            storedConferences.add(conference.getValue().getStringRep() + "\n");
        }
        return storedConferences;
    }


    /***
     * remove event from a conference
     * @param conferenceTitle conference title
     * @param eventID ID of event
     * @return if this has been successful
     */
    public boolean removeEvent(String conferenceTitle, String eventID){
        Conference conference = conferenceTitlesHash.get(conferenceTitle);
        if (conference!= null) {
            return conference.removeEvent(eventID);
        }
        return false;
    }
}