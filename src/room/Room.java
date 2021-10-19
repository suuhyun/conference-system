package room;

/**
 * An entity class. The Room object represents a room with a room ID attribute.
 * @author Cynthia
 * @version 1
 * */
public class Room {
    private String roomId;
    private String roomName;
    private int capacity;
    private boolean hasProjector = false;
    private boolean hasMicrophone = false;
    private boolean hasTables = false;
    private boolean hasWhiteboard = false;


    /**
     * instantiates a new Room object with an id attribute
     * @param roomId the unique id of this room
     */
    public Room(String roomId, String roomName){
        this.capacity = 8;
        this.roomId = roomId;
        this.roomName = roomName;
    }


    /**
     * Getter for the capacity of this room.
     * @return capacity of room
     * */
    public int getCapacity(){ return capacity; }


    /**
     * Setter for the capacity of this room. (Will be needed in Phase 2)
     * @param capacity list of events
     * */
    public void setCapacity(int capacity){ this.capacity = capacity; }


    /**
     * Getter for the id unique to this room.
     * @return the id of this room
     * */
    public String getRoomId(){ return roomId; }


    /**
     * Returns the unique name of this room object
     * @return the name of this room
     * */
    public String getRoomName() {return roomName;}


    /**
     * Returns the string representation of this room object
     * @return String representation of this room object int he way it should be stored in the csv. file
     * */
    public String stringRepresentation() {
        return roomId + "," + roomName + "," + hasProjector + "," + hasMicrophone
                + "," + hasTables + "," + hasWhiteboard;
    }


    /**
     * sets a projector in room
     */
    public void setProjector(){
        this.hasProjector = true;
    }


    /**
     * sets a microphone in room
     */
    public void setMicrophone(){
        this.hasMicrophone = true;
    }


    /**
     * sets tables in room
     */
    public void setTables(){
        this.hasTables = true;
    }


    /**
     * sets whiteboard in room
     */
    public void setWhiteboard(){
        this.hasWhiteboard = true;
    }


    /**
     * returns whether the room has a projector
     */
    public boolean getHasProjector(){
        return this.hasProjector;
    }


    /**
     * returns whether the room has a microphone
     */
    public boolean getHasMicrophone(){
        return this.hasMicrophone;
    }


    /**
     * returns whether the room has tables
     */
    public boolean getHasTables(){
        return this.hasTables;
    }


    /**
     * returns whether the room has a whiteboard
     */
    public boolean getHasWhiteboard(){
        return this.hasWhiteboard;
    }
}