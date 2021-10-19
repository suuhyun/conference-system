package loadUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Loads up information for user including messages, events, rooms, attendees and organizers
 */
public class LoadUp implements LoadUpIGateway {

    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<String> events = new ArrayList<>();
    private ArrayList<String> rooms = new ArrayList<>();
    private ArrayList<String> attendees = new ArrayList<>();
    private ArrayList<String> organizers = new ArrayList<>();
    private ArrayList<String> speakers = new ArrayList<>();
    private ArrayList<String> conferences = new ArrayList<>();



    /**
     * Loads up information of messages, events, rooms, attendees and organizers
     */
    public LoadUp() {
        getMessages();
        getEvents();
        getRooms();
        getSpeakers();
        getAllAttendees();
        getAllOrganizers();
        getConferences();
        getAllAttendees();
        getAllOrganizers();
    }


    /**
     * This method returns messages in a list from messages.csv
     * @return messages in a list
     */
    public ArrayList<String> getMessages() {
        // gets list of messages from messages.csv and sets it to <messages>
        try (BufferedReader br = new BufferedReader(new FileReader("./phase2/src/assets/dataFiles/messages.csv"))) {
            String line = null;
            while((line = br.readLine()) != null) {
                messages.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }


    /**
     * This method returns rooms in a list from rooms.csv
     * @return rooms in a list
     */
    public ArrayList<String> getRooms(){
        try (BufferedReader br = new BufferedReader(new FileReader("./phase2/src/assets/dataFiles/rooms.csv"))) {
            String line = null;
            while((line = br.readLine()) != null) {
                rooms.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }


    /**
     * This method returns rooms in a list from events.csv
     * @return rooms
     */
    public ArrayList<String> getEvents() {
        // gets list of events from events.csv and sets it to <events>
        try (BufferedReader br = new BufferedReader(new FileReader("./phase2/src/assets/dataFiles/events.csv"))) {
            String line = null;
            while((line = br.readLine()) != null) {
                events.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }


    /**
     * This method returns conferences in a list from conferences.csv
     * @return conferences
     */
    public ArrayList<String> getConferences() {
        try (BufferedReader br = new BufferedReader(new FileReader("./phase2/src/assets/dataFiles/conferences.csv"))) {
            String line = null;
            while((line = br.readLine()) != null) {
                conferences.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conferences;
    }


    /**
     * Getter for messages
     * @param filePath the text file path of messages to be found
     * @return messages
     */
    // Overloaded with custom file path
    public List<String> getMessages(String filePath) {
        // gets list of messages from messages.csv and sets it to <messages>
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            while((line = br.readLine()) != null) {
                messages.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }


    /**
     * This method returns attendees in a list from attendees.csv
     * @return attendees in a list
     */
    public ArrayList<String> getAllAttendees() {
        // gets list of attendees from attendees.csv and sets it to <attendeesHM>
        try (BufferedReader br = new BufferedReader(new FileReader("./phase2/src/assets/dataFiles/attendees.csv"))) {
            String line = null;
            while((line = br.readLine()) != null) {
                attendees.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attendees;
    }


    /**
     * This method returns organizers in a list from organizers.csv
     * @return organizers in a list
     */
    public ArrayList<String> getAllOrganizers() {
        try (BufferedReader br = new BufferedReader(new FileReader("./phase2/src/assets/dataFiles/organizers.csv"))) {
            String line = null;
            while((line = br.readLine()) != null) {
                organizers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return organizers;
    }


    /**
     * This method returns messages in a list from messages.csv
     * @return messages in a list
     */
    public ArrayList<String> getSpeakers() {
        // gets list of messages from messages.csv and sets it to <messages>
        try (BufferedReader br = new BufferedReader(new FileReader("./phase2/src/assets/dataFiles/speakers.csv"))) {
            String line = null;
            while((line = br.readLine()) != null) {
                speakers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return speakers;
    }


    // Methods from IGateway
    // To be called inside Use Case Classes

    /**
     * Getter for the list of messages from last use of program using IGateway
     * */
    @Override
    public ArrayList<String> getMessagesList() {
        return messages;
    }


    /**
     * Getter for the list of speakers from last use of program using IGateway
     * */
    @Override
    public ArrayList<String> getSpeakersList() { return speakers; }


    /**
     * Getter for the list of conferences from last use of program using IGateway
     * */
    @Override
    public ArrayList<String> getConferencesList() {
        return conferences;
    }

}
