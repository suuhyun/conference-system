package room;
import loadUp.LoadUpIGateway;
import store.Storable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A use case class that stores a list of rooms and can add/remove rooms.
 * @author multiple
 * @version 1
 * */

public class RoomActions implements Storable {
    private HashMap<String, Room> roomsID = new HashMap<String, Room>();
    private HashMap<String, Room> roomUsername = new HashMap<String, Room>();
    private LoadUpIGateway loader;
    private ArrayList<String> loadUpRooms = new ArrayList<String>();


    /**
     * @param loader the IGateway to use when loading
     * This will load up the data in the hashmap to the CSV files.
     * */
    public RoomActions(LoadUpIGateway loader) {
        this.loader = loader;
        getAllRooms(loader);
        addRoomToHashMap();
    }


    /**
     * @return ID of the room from the hashmap
     */
    public HashMap<String, Room> returnHashMap() {
        return roomsID;
    }


    /**
     * @return ID of the room username from the hashmap
     */
    public HashMap<String, Room> returnRoomUsernameHashMap() {
        return roomUsername;
    }


    /**
     * @param roomName the name of the room to check
     * @return true if the room with the following username exists.
     */
    public boolean roomExists(String roomName){
        return roomUsername.containsKey(roomName);
    }


    /**
     * This will create a new room
     * @param username the name of the room to be created
     * @return a new room
     */
    public Room createRoom(String username) {
        String userId = "R" + String.valueOf(roomsID.size());
        Room room = new Room(userId, username);
        addRoomIdToHashMap(room);
        addRoomUsernameToHashMap(room);
        addRoom(room);
        return room;
    }


    /**
     * Adds an roomId to existing hashmap of roomId's.
     * The key is the roomId, the value is an instance of the room object.
     * @param roomId the room to be added
     * */
    private void addRoomIdToHashMap(Room roomId) {
        if (!roomsID.containsKey(roomId.getRoomId())) {
            roomsID.put(roomId.getRoomId(), roomId);
        }
    }


    /**
     * Adds an room username to existing hashmap of usernames.
     * The key is the room username, the value is an instance of the room object.
     * @param roomName the room to be added
     * */
    private void addRoomUsernameToHashMap(Room roomName) {

        if (!roomUsername.containsKey(roomName.getRoomName())) {
            roomUsername.put(roomName.getRoomName(), roomName);
        }
    }


    /**
     * Adds a room (Kept as a boolean as it will be needed in Phase 2)
     * @param room the room to be added
     * @return true if the room is added successfully otherwise return false
     */
    public boolean addRoom(Room room){

        if (roomsID.containsKey(room.getRoomId())){
            return false;
        }
        roomsID.put(room.getRoomId(), room);
        roomUsername.put(room.getRoomName(), room);
        return true;
    }


    /**
     * Removes a room
     * @param room the room to be removed
     * @return true if the room is removed successfully otherwise return false
     */
    public boolean removeRoom(Room room){
        if (roomsID.containsKey(room.getRoomId()) && roomUsername.containsKey(room.getRoomName())){
            roomsID.remove(room.getRoomId(), room);
            roomUsername.remove(room.getRoomName(), room);
            return true;
        }
        return false;
    }


    /**
     * Finds a room from a given username (Will need for Phase 2)
     * @param username the room username given
     * @return room object from hashmap of room objects
     * */
    public Room findRoomFromName(String username) {
        return roomUsername.get(username);
    }


    /**
     * Finds a room from a given roomId
     * @param roomId the roomId given
     * @return room object from hashmap of room objects
     * */
    public Room findRoomFromId(String roomId) {
        return roomsID.get(roomId);
    }


    /**
     * It will get all rooms from the CSV file.
     * @param loader the IGateway to be used to load
     */
    private void getAllRooms(LoadUpIGateway loader) {
        loadUpRooms = loader.getRooms();
    }


    /**
     * This method will add the room to the hashmap.
     */
    private void addRoomToHashMap() {

        if (loadUpRooms != null) {

            for (String roomString : loadUpRooms) {
                String[] roomInfo = roomString.split(",");

                Room loadedRoom = new Room(roomInfo[0], roomInfo[1]);

                roomsID.put(roomInfo[0], loadedRoom);
                roomUsername.put(roomInfo[1], loadedRoom);
            }

        }
    }


    /**
     * It will be get the room ID
     * @return ArrayList<String>
     */
    public ArrayList<String> getRoomsIds() {
        ArrayList<String> storedR = new ArrayList<String>();
        if (roomsID != null && !roomsID.isEmpty()) {
            for (Map.Entry<String, Room> o : roomsID.entrySet()) {
                storedR.add(o.getValue().getRoomId() + "\n");
            }
        }
        return storedR;
    }


    /**
     * Sets a  projector in the room
     * @param roomName the name of the room
     */
    public void setProjector(String roomName){
        roomUsername.get(roomName).setProjector();
    }


    /**
     * Sets a microphone in the room
     * @param roomName the name of the rooom
     */
    public void setMicrophone(String roomName){
        roomUsername.get(roomName).setMicrophone();
    }


    /**
     * sets tables in the room
     * @param roomName the name of the room
     */
    public void setTables(String roomName){
        roomUsername.get(roomName).setTables();
    }


    /**
     * sets a whiteboard in the room
     * @param roomName the name of the room
     */
    public void setWhiteboard(String roomName){
        roomUsername.get(roomName).setWhiteboard();
    }


    /**
     * checks if there is a projector in room
     */
    private boolean checkProjector(String roomName){
        return roomUsername.get(roomName).getHasProjector();
    }


    /**
     * checks if there is a microphone in room
     */
    private boolean checkMicrophone(String roomName){
        return roomUsername.get(roomName).getHasMicrophone();
    }


    /**
     * checks if there are tables in room
     */
    private boolean checkTables(String roomName){
        return roomUsername.get(roomName).getHasTables();
    }


    /**
     * checks if there is a whiteboard in room
     */
    private boolean checkWhiteboard(String roomName){
        return roomUsername.get(roomName).getHasWhiteboard();
    }


    /**
     * returns all the rooms containing projector
     * @return all rooms containing projector
     */
    public ArrayList<String> getRoomsWithProjector(){
        ArrayList<String> rooms = new ArrayList<String>();
        for (Map.Entry<String, Room> o:roomUsername.entrySet()){
            if (o.getValue().getHasProjector()){
                rooms.add(o.getKey());
            }
        }
        return rooms;
    }


    /**
     * returns all the rooms containing microphone
     * @return all rooms containing microphone
     */
    public ArrayList<String> getRoomsWithMicrophone(){
        ArrayList<String> rooms = new ArrayList<String>();
        for (Map.Entry<String, Room> o:roomUsername.entrySet()){
            if (o.getValue().getHasMicrophone()){
                rooms.add(o.getKey());
            }
        }
        return rooms;
    }


    /**
     * returns all the rooms containing tables
     * @return all rooms containing tables
     */
    public ArrayList<String> getRoomsWithTables(){
        ArrayList<String> rooms = new ArrayList<String>();
        for (Map.Entry<String, Room> o:roomUsername.entrySet()){
            if (o.getValue().getHasTables()){
                rooms.add(o.getKey());
            }
        }
        return rooms;
    }


    /**
     * returns all the rooms containing whiteboard
     * @return all rooms containing whiteboard
     */
    public ArrayList<String> getRoomsWithWhiteboard(){
        ArrayList<String> rooms = new ArrayList<String>();
        for (Map.Entry<String, Room> o:roomUsername.entrySet()){
            if (o.getValue().getHasWhiteboard()){
                rooms.add(o.getKey());
            }
        }
        return rooms;
    }


    /**
     * It will be storing rooms
     * @return ArrayList<String>
     */
    public ArrayList<String> store() {
        ArrayList<String> storedR = new ArrayList<String>();
        if(roomUsername != null && !roomUsername.isEmpty()) {
            for (Map.Entry<String, Room> o : roomUsername.entrySet()) {
                storedR.add(o.getValue().stringRepresentation() + "\n");
            }
        }
        return storedR;
    }

}
