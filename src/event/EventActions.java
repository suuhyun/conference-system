package event;
import loadUp.LoadUpIGateway;
import store.Storable;

import java.util.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A use case class that stores a hashmap of Events.
 * This class collaborates with UserAccountActions, OrganizationActions and SpeakerActions.
 * @author multiple
 * @version 1
 */

public class EventActions implements Storable {
    public HashMap<String, Event> events = new HashMap<String, Event>(); // public private
    public HashMap<String, Integer> eventCapacity = new HashMap<String, Integer>(); //eventID: capacity
    private HashMap<String, Event> eventNames = new HashMap<String, Event>();
    private HashMap<String, List<String>> roomSchedule = new HashMap<String, List<String>>(); // roomID: date
    private HashMap<String, List<String>> speakerSchedule = new HashMap<String, List<String>>(); // SpeakerID: date
    private HashMap<String, List<String>> attendees = new HashMap<String, List<String>>(); // EventID: attendees
    private LoadUpIGateway loader;
    private List<String> eventList;

    /**
     * @param loader
     * This will load up the data in the hashmap to the CSV files.
     * */
    public EventActions(LoadUpIGateway loader) {
        loadAllEvents(loader);
        addLoadedToHashMap();
        this.loader = loader;
    }


    /***
     * return event of given ID
     * @param eventID the unique id of event
     * @return events
     */
    public Event getEvent(String eventID){
        return events.get(eventID);
    }


    /***
     * return event of given name
     * @param eventName the title of the event
     * @return events
     */
    public Event getEventFromName(String eventName){
        return eventNames.get(eventName);
    }


    /***
     * return if the event exists in events
     * @param eventID the unique id of the event
     * @return true if the event exists in events
     */
    public boolean eventExists(String eventID){
        return events.containsKey(eventID);
    }


    /***
     * return if the eventName exists in eventNames
     * @param eventName the title of the event
     * @return true if the eventName exists in eventNames
     */
    public boolean eventNameExists(String eventName){
        return eventNames.containsKey(eventName);
    }


    /***
     * return hashmap of all eventIDs and the corresponding event object. key: speakerID value: List of date and time
     * @return hashmap of all eventIDs and the corresponding event object
     */
    public HashMap<String, Event> getEvents(){
        return events;
    }


    /***
     * return hashmap of all eventNames and the corresponding event object. key: speakerID value: List of date and time
     * @return hashmap of all eventNames and the corresponding event object
     */
    public HashMap<String, Event> getEventNames(){
        return eventNames;
    }

    public List<List<String>> getEventsList() {
        List<List<String>> eventsList = new ArrayList<List<String>>();
        if(events != null){
            List<String> eventStringsList;
            for(Map.Entry<String, Event> entry : events.entrySet()){
               eventStringsList = Arrays.asList(entry.getValue().string().split(","));
               eventsList.add(eventStringsList);
            }
        }
        return eventsList;
    }


    /***
     * return hashmap of all speakers and the times they are busy. key: speakerID value: List of date and time
     * @return hashmap of all speakers and the times they are busy
     */
    public HashMap<String, List<String>> getSpeakerSchedule(){
        return speakerSchedule;
    }


    /***
     * Add a new speaker to the speakerSchedule
     * @param speakerID ID of speaker
     */
    public void addSpeakerToSchedule(String speakerID){
        speakerSchedule.put(speakerID, new ArrayList<>());
    }


    /***
     * return hashmap of all rooms and the times they are in use. key: roomID value: List of date and time
     * @return hashmap of all rooms and the times they are in use
     */
    public HashMap<String, List<String>> getRoomSchedule(){
        return roomSchedule;
    }


    /***
     * Add a new room to the roomSchedule
     * @param roomID ID of room
     */
    public boolean addRoomToSchedule(String roomID){
        roomSchedule.put(roomID, new ArrayList<>());
        return roomSchedule.containsKey(roomID);
    }


    /** gets list of event from the IGateway **/
    private void loadAllEvents(LoadUpIGateway loader) {
        eventList = loader.getEvents();
    }


    /** Adds events loaded from the csv to <events> **/
    private void addLoadedToHashMap() {
        if (eventList != null && !eventList.isEmpty()) {
            for (String event: eventList){
                String[] eventAttributes = event.split(",");
                List<String> eventAttendees = new ArrayList<>(Arrays.asList(eventAttributes[5].split("%%")));
                List<String> eventSpeaker = new ArrayList<>(Arrays.asList(eventAttributes[2].split("%%")));
                loadEvent(eventAttributes[0], eventAttributes[1], eventSpeaker, eventAttributes[3], eventAttributes[4],
                        eventAttendees, eventAttributes[6], eventAttributes[7], Integer.parseInt(eventAttributes[8]), Boolean.parseBoolean(eventAttributes[9]));
            }
        }
    }


    /**
     * Create a new event based on the parameters provided
     * @param title title of event
     * @param speakerId id of speaker
     * @param startDateTime the start date and time for the event
     * @param endDateTime the end date and time for the event
     * @param attendees list of attendees of event
     * @param roomID id of room
     * @param isVip if event is a vip event
     * @return true if the event was created
     * */
    public Event createEvent(String title, List<String> speakerId, String startDateTime, String endDateTime,
                             List<String> attendees, String roomID, String conference, int capacity, boolean isVip){

        if (isRoomFree(roomID, startDateTime, endDateTime) && isSpeakerFree(speakerId, startDateTime, endDateTime)){

            int IDNumber = events.size();
            String newID = "E" + IDNumber;
            while(events.containsKey(newID)){
                IDNumber += 1;
                newID = "E" + IDNumber;
            }

            return loadEvent(newID, title, speakerId, startDateTime, endDateTime, attendees, roomID, conference,  capacity, isVip);
        }
        return null;
    }


    /***
     * set speaker of an event
     * @param eventID if of event
     * @param speakerID id of new speaker
     */
    public void setSpeaker(String eventID, List<String> speakerID){
        this.events.get(eventID).setSpeaker(speakerID);
    }


    /***
     *  Load a new event to CSV file so we are able to access it after the system has been closed as well
     *
     * @param eventID id of event
     * @param title title of event
     * @param speakerId id of speaker
     * @param startDateTime the start date and time for the event
     * @param endDateTime the end date and time for the event
     * @param attendees list of attendees of event
     * @param roomID id of room
     * @param isVip if event is a vip event
     */
    public Event loadEvent(String eventID, String title, List<String> speakerId, String startDateTime, String endDateTime,
                           List<String> attendees, String roomID, String conference, int capacity, boolean isVip) {

        if (attendees.size() == 1 && attendees.get(0).equals("")) { // not certain second one is necessary
            attendees = new ArrayList<>();
        }
        Event newEvent = new Event(eventID, title, speakerId, startDateTime, endDateTime, attendees, roomID, conference, capacity, isVip);
        events.put(eventID, newEvent);
        eventNames.put(title, newEvent);
        this.attendees.put(eventID, attendees);
        List<String> dateTimes = timeInBetween(startDateTime, endDateTime);
        for (String dateTime: dateTimes) {
            if (speakerSchedule.containsKey(speakerId)) {
                speakerSchedule.get(speakerId).add(dateTime);

            } else {
                for (String elem : speakerId) {
                    List<String> speakerTimes = new ArrayList<>();
                    speakerTimes.add(dateTime);
                    speakerSchedule.put(elem, speakerTimes);
                }
            }
            if (roomSchedule.containsKey(roomID)) {
                roomSchedule.get(roomID).add(dateTime);

            } else {
                List<String> roomTimes = new ArrayList<>();
                roomTimes.add(dateTime);
                roomSchedule.put(roomID, roomTimes);
            }
        }
        return newEvent;
    }


    /***
     * return list of dates in string format of the time beginning with and including startDateTime,
     *      and ending with and excluding  endDateTime
     * @param startDateTime the new start date and time for the event to be changed to
     * @param endDateTime the new end date and time for the event to be changed to
     * @return list of dates in string format of the time beginning with and including startDateTime,
     *      and ending with and excluding  endDateTime
     */
    private List<String> timeInBetween(String startDateTime, String endDateTime){
        int strLength = startDateTime.length();
        int startTime = Integer.parseInt(startDateTime.substring(strLength - 2, strLength));
        int endTime = Integer.parseInt(endDateTime.substring(strLength - 2, strLength));
        String date = startDateTime.substring(0, 10);
        List<String> times = new ArrayList<>();
        times.add(startDateTime);
        while (startTime < endTime){
            times.add(date + startTime);
            startTime += 1;
        }
        return times;
    }


    /***
     * Add an attendee who has requested to be added to a specific event
     * @param eventID the unique id of the event
     * @param attendeeID the unique id of the attendee to be added
     * @return true if an attendee has been added to their desired event
     */
    public boolean addAttendee(String eventID, String attendeeID){
        List<String> eventAttendees = events.get(eventID).getAttendees();
        if (eventAttendees.contains(attendeeID)) {
            return false;
        } else{
            events.get(eventID).getAttendees().add(attendeeID);
            return true;
        }
    }


    /***
     * Remove an attendee who has requested to be removed to a specific event
     * @param eventID the unique id of the event
     * @param attendeeID the unique id of attendee to be removed
     * @return true if an attendee has been removed to their desired event
     */
    public boolean removeAttendee(String eventID, String attendeeID){
        List<String> eventAttendees = attendees.get(eventID);
        if (!eventAttendees.contains(attendeeID)) {
            return false;
        } else{
            eventAttendees.remove(attendeeID);
            attendees.replace(eventID, eventAttendees);
            return true;
        }
    }


    /***
     * Remove an attendee who has requested to be removed from a specific event
     * @param eventName the name of the event to be cancelled
     * @return true if an attendee has been removed from an event that they used to be a part of
     */
    public List<String> cancelEvent(String eventName){
        Event event = eventNames.get(eventName);
        String eventID = event.getId();
        if (event != null) {
            this.events.remove(eventID);
            List<String> eventAttendees = this.attendees.get(eventID);
            this.attendees.remove(eventID);
            List<String> dateTimes = timeInBetween(event.getStartDateTime(), event.getEndDateTime());
            List<String> speakers = event.getSpeakers();
            if (!(speakers.size()== 1 && speakers.get(0).equalsIgnoreCase(""))) {
                for (String speaker : speakers) {
                    this.speakerSchedule.get(speaker).removeAll(dateTimes);
                }
            }
            // speakerSchedule.get(event.getSpeakers()).removeAll(dateTimes);
            roomSchedule.get(event.getRoomID()).removeAll(dateTimes);
            return eventAttendees;
        }
        return null;
    }


    /***
     * Cancel an event
     * @param eventID the unique id of the event to be changed
     * @param newStartDateTime the new start date and time for the event to be changed to
     * @param newEndDateTime the new end date and time for the event to be changed to
     * @return List<String> which is the list of attendees who had signed up for that event
     */
    public boolean changeEventTime(String eventID, String newStartDateTime, String newEndDateTime){
        Event event = this.events.get(eventID);
        if(event != null) {
            if (isRoomFree(event.getRoomID(), newStartDateTime, newEndDateTime) &&
                    isSpeakerFree(event.getSpeakers(), newStartDateTime, newEndDateTime)) {

                List<String> dateTimes = timeInBetween(event.getStartDateTime(), event.getEndDateTime());
                this.roomSchedule.get(event.getRoomID()).removeAll(dateTimes);

                event.setStartTime(newStartDateTime);
                event.setEndDateTime(newEndDateTime);
                List<String> newDateTimes = timeInBetween(newStartDateTime, newEndDateTime);
                List<String> speakers = event.getSpeakers();
                if (!(speakers.size()== 1 && speakers.get(0).equalsIgnoreCase(""))) {
                    for (String speaker : speakers) {
                        this.speakerSchedule.get(speaker).removeAll(dateTimes);
                        this.speakerSchedule.get(speaker).addAll(dateTimes);
                    }
                }
                // this.speakerSchedule.get(event.getSpeakers()).addAll(newDateTimes);
                this.roomSchedule.get(event.getRoomID()).addAll(newDateTimes);
                return true;
            }
        }
        return false;

    }


    /***
     * return if this dateTime is in conflict with a given event
     * @param eventID ID of event
     * @param startDateTime startDateTime to check if it is in conflict with the event
     * @param endDateTime endDateTime to check if it is in conflict with the event
     * @return true if this dateTime is in conflict with a given event
     */
    public boolean timeConflict(String eventID, String startDateTime, String endDateTime){
        Event event = getEvent(eventID);
        List<String> eventDateTimes = timeInBetween(event.getStartDateTime(), event.getEndDateTime());
        List<String> dateTimes = timeInBetween(startDateTime, endDateTime);
        for(String dateTime: dateTimes){
            if (eventDateTimes.contains(dateTime)){
                return true;
            }
        }
        return false;
    }


    /**
     * Change the time of an event which has been created
     * @param roomID the unique id for the room to be checked
     * @param startDateTime the date and time to be checked
     * @param endDateTime the date and time to be checked
     * @return true if the event has been successfully updated
     * */

    public boolean isRoomFree(String roomID, String startDateTime, String endDateTime){
        List<String> roomTime = this.roomSchedule.get(roomID);
        List<String> dateTimes = timeInBetween(startDateTime, endDateTime);
        for (String dateTime: dateTimes) {
            if (roomTime != null && roomTime.contains(dateTime)) {
                return false;
            }
        }
        return true;

    }


    /**
     * Check if a room is available as a certain time
     * @param speakerID the unique id of the speaker to be checked
     * @param startDateTime the date and time to be checked
     * @param endDateTime the date and time to be checked
     * @return true if the room is in fact available
     * */
    public boolean isSpeakerFree(List<String> speakerID, String startDateTime, String endDateTime){
        for(String object : speakerID){
            List<String> SpeakerTime = speakerSchedule.get(object);
            List<String> dateTimes = timeInBetween(startDateTime, endDateTime);
            for (String dateTime: dateTimes) {
                if (SpeakerTime != null && SpeakerTime.contains(dateTime)) {
                    return false;
                }
            }

        } return true;
    }



    /***
     * return all events in hashmap as a List of strings
     * @return all events in hashmap as a List of strings
     */
    public ArrayList<String> store(){
        ArrayList<String> storedEvents = new ArrayList<String>();
        for(Map.Entry<String, Event> event : events.entrySet()) {
            storedEvents.add(event.getValue().string()+"\n");
        }
        return storedEvents;

    }


    /***
     * @return the event ID
     */
    public ArrayList<String> getEventIds() {
        ArrayList<String> storedE = new ArrayList<String>();
        if (events != null && !events.isEmpty()) {
            for (Map.Entry<String, Event> o : events.entrySet()) {
                storedE.add(o.getValue().getId() + "\n");
            }
        }
        return storedE;
    }


    /***
     * return list of all attendee IDs of an event
     * @param eventID the unique id of the event
     * @return List of all attendee IDs of an event
     */
    public List<String> getEventAttendees(String eventID){
        return attendees.get(eventID);
    }

    /**
     * Return the number of events that are available.
     * @return the number of events that are available.
     */
    public Integer numberEventsAvailable(){
        Integer total = 0;
        for (Map.Entry<String, Event> entry : events.entrySet()) {
            total++;
        }
        return total;
    }

    /**
     * Events are considered to be one of the most attended events with an attendance rate of at least 75%
     * @return a list of the most attended events
     */
    public ArrayList<String> mostAttendedEvents(){
        ArrayList<String> mostAttended = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry1: eventCapacity.entrySet()) {
            for (Map.Entry<String, List<String>> entry2 : attendees.entrySet()) {
                double eventRoomCapacity = eventCapacity.get(entry1.getValue());
                double numAttendees = attendees.get(entry2.getValue()).size();
                if (numAttendees / eventRoomCapacity >= 0.75) {
                    String username = events.get(entry1.getKey()).getTitle();
                    mostAttended.add(username);
                    return mostAttended;
                }
            }
        }
        return mostAttended;
    }


    /**
     * Events are considered to be one of the least attended events with an attendance rate of less than 50%
     * @return a list of the most attended events
     */
    public ArrayList<String> leastAttendedEvents(){
        ArrayList<String> leastAttended = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry1: eventCapacity.entrySet()) {
            for (Map.Entry<String, List<String>> entry2 : attendees.entrySet()) {
                double eventRoomCapacity = eventCapacity.get(entry1.getValue());
                double numAttendees = attendees.get(entry2.getValue()).size();
                if (numAttendees / eventRoomCapacity <= 0.50) {
                    String username = events.get(entry1.getKey()).getTitle();
                    leastAttended.add(username);
                    return leastAttended;
                }
            }
        }
        return leastAttended;
    }


    /**
     * Reveals the number of events that are at full capacity
     * @return the number of events at full capacity
     */
    public int numAtMaxCapacity() {
        int count = 0;
        for (Map.Entry<String, Integer> entry1 : eventCapacity.entrySet()) {
            for (Map.Entry<String, List<String>> entry2 : attendees.entrySet()) {
                if (eventCapacity.get(entry1.getValue()) == attendees.get(entry2.getValue()).size()) {
                    count += 1;
                }
            }
        }
        return count;
    }


    /**
     * Provides a list of events in ascending order of event date
     * @return a list of events with their date times in ascending order
     */

    public ArrayList<String> eventsOrderedByDate(){
        ArrayList<String> eventsByDate = new ArrayList<String>();
        for (Map.Entry<String, Event> entry : eventNames.entrySet()) {
            eventsByDate.add(entry.getValue().getStartDateTime() + " , " + entry.getKey());
            eventsByDate.sort(Comparator.comparing(String::toString));
            return eventsByDate;
        }
        return eventsByDate;
    }


    /**
     * Provide the average number of attendees in each event
     * @return the mean number of attendees in each event
     */

    public int averageNumberAttendees(){
        int numberEvents = numberEventsAvailable();
        int numberAttendees = attendees.keySet().size();
        return numberAttendees % numberEvents;
    }


    /**
     * Provide the median for the number of attendees attending events
     * @return the median number of attendees attending events or 0
     */
    public int medianNumberAttendees() {
        ArrayList<Integer> numberAttendees = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : attendees.entrySet()) {
            numberAttendees.add(entry.getValue().size());
            numberAttendees.sort(Integer::compareTo);
            if (numberAttendees.size() % 2 == 0) {
                return (numberAttendees.get(numberAttendees.size() / 2) + numberAttendees.get(numberAttendees.size() / 2 - 1) / 2);
            } else {
                return (numberAttendees.get(numberAttendees.size() / 2));
            }
        }
        return 0;
    }


    /**
     * Provide the mode for the number of attendees attending events
     * @return the mode of attendees attending events or 0
     */
    public int modeNumberAttendees() {
        ArrayList<Integer> numberAttendees = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : attendees.entrySet()) {
            numberAttendees.add(entry.getValue().size());
            return numberAttendees.stream()
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getKey();
        }
        return 0;
    }


    /**
     * Show a list of top 5 events with most attendees. The list could be less than 5 events, or more than 5 events if
     * more than one event has the same number of attendees. It is top 5 based on number of attendees; in other words,
     * the top attendee rate.
     * @return a list of the top 5 events
     */
    public ArrayList<String> topFiveEvents() {
        ArrayList<Integer> numberAttendees = new ArrayList<>();
        ArrayList<String> topFive = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : attendees.entrySet()) {
            numberAttendees.add(entry.getValue().size());
            Set<Integer> set = new HashSet<>(numberAttendees);
            numberAttendees.clear();
            numberAttendees.addAll(set);
            numberAttendees.sort(Integer::compareTo);
            numberAttendees.sort(Comparator.reverseOrder()); //descending order
            if (numberAttendees.size() > 5) {
                ArrayList<Integer> bottomFiveNumbers = new ArrayList<>(numberAttendees.subList(0,5));
                if (bottomFiveNumbers.contains(entry.getValue().size())) {
                    String username = events.get(entry.getKey()).getTitle();
                    topFive.add(username);
                }
            }
            else{
                String username = events.get(entry.getKey()).getTitle();
                topFive.add(username);
            }
        }
        return topFive;
    }


    /**
     * Show a list of bottom 5 events with most attendees. The list could be less than 5 events, or more than 5 events if
     * more than one event has the same number of attendees. It is bottom 5 based on number of attendees; in other words,
     * the bottom attendee rate.
     * @return a list of the bottom 5 events
     */
    public ArrayList<String> bottomFiveEvents() {
        ArrayList<Integer> numberAttendees = new ArrayList<>();
        ArrayList<String> bottomFive = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : attendees.entrySet()) {
            numberAttendees.add(entry.getValue().size());
            Set<Integer> set = new HashSet<>(numberAttendees);
            numberAttendees.clear();
            numberAttendees.addAll(set);
            numberAttendees.sort(Integer::compareTo);
            numberAttendees.sort(Comparator.naturalOrder()); //ascending order
            if (numberAttendees.size() > 5) {
                ArrayList<Integer> bottomFiveNumbers = new ArrayList<>(numberAttendees.subList(0,5));
                if (bottomFiveNumbers.contains(entry.getValue().size())) {
                    String username = events.get(entry.getKey()).getTitle();
                    bottomFive.add(username);
                }
            }
            else{
                String username = events.get(entry.getKey()).getTitle();
                bottomFive.add(username);
            }
        }
        return bottomFive;
    }
}

