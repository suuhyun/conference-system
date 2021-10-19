package loadUp;


import java.util.*;

/**
 * Interface for loadUp to get all the entity lists
 *
 */
public interface LoadUpIGateway {


    /**
     * Gets list of organizers from the gateway
     **/
    public ArrayList<String> getMessagesList();

    /**
     * Gets list of attendees from the gateway
     **/
    public ArrayList<String> getAllAttendees();


    /**
     * Gets list of speakers from the gateway
     **/
    public ArrayList<String> getSpeakersList();


    /**
     * Gets list of organizers from the gateway
     **/
    public ArrayList<String> getAllOrganizers();


    /**
     * Gets list of rooms from the gateway
     **/
    public ArrayList<String> getRooms();


    /**
     * Gets list of events from the gateway
     **/
    public ArrayList<String> getEvents();


    /**
     * Gets list of conferences from the gateway
     **/
    public ArrayList<String> getConferencesList();

}
