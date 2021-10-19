package message;

import loadUp.LoadUpIGateway;
import store.Storable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * A use case class that stores a hashmap of messages
 * Allows actions to be done with messages, including creating messages, sending messages, loading messages,
 * retrieving message information, printing messages, storing messages
 */
public class MessageActions implements Storable {
    private ArrayList<String> conversations = new ArrayList<String>(); // list containing loaded messages
    private HashMap<String, Message> messages = new HashMap<String, Message>(); // hashmap containing all loaded and new messages
    private LoadUpIGateway loader;


    /**
     * Gets all messages from message.csv and adds those messages to a hashmap of all messages from the csv
     * @param loader loads up messages
     */
    public MessageActions(LoadUpIGateway loader) {
        // with message ID as key and message object as value
        this.loader = loader;
        getAllMessages(loader); // gets all messages from message.csv
        addLoadedToHashMap(); // adds those messages to a hashmap of all messages from the csv
    }


    /**
     * Create a message and generate unique ID for message
     * @param senderId the ID of the sender
     * @param receiverId the ID of the receiver
     * @param message the message created
     * @return a new message
     */
    public Message createMessage(String senderId, String receiverId, String message) {
        String messageId = "A" + String.valueOf(messages.size());
        Message newMessage = new Message(messageId, senderId, receiverId, message, generateSentTime());
        loadMessage(messageId, newMessage);
        return newMessage;
    }


    /**
     * Load new messages into HashMap of new messages
     * @param messageId the id of message
     * @param newMessage a new message
     */
    public void loadMessage(String messageId, Message newMessage){
        messages.put(messageId, newMessage);
    }


    /**
     * Generates the time of Message construction.
     * Method gets local time (time according the the timezone on the sender's computer)
     * of the sender, and converts it to Toronto time.
     * @return time of Message construction (converted to Toronto time)
     */
    private String generateSentTime() {
        final String DATE_FORMAT = "dd-M-yyyy k:mm:ss.n"; // format of date and time

        // get local time in toronto time zone
        ZoneId torontoZoneId = ZoneId.of("America/Toronto");
        LocalDateTime zonedDateTime = LocalDateTime.now(torontoZoneId);

        // format the date according to <DATE_FORMAT>
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String dateTime = format.format(zonedDateTime);
        dateTime = dateTime.substring(0, dateTime.indexOf(".") + 4);
        return dateTime;
    }


    /**
     * This method gets list of messages from the IGateway
     * @param loader loads up messages
     */
    private void getAllMessages(LoadUpIGateway loader) {
        conversations = loader.getMessagesList();
    }


    /**
     * This method adds messages loaded from the csv to <messages>
     */
    private void addLoadedToHashMap() {
        if (conversations != null) {
            for(String messageString : conversations) {
                String[] messageInfo = messageString.split("%2%0%7%");
                Message loadedMessage = new Message(messageInfo[0], messageInfo[1], messageInfo[2],messageInfo[3], messageInfo[4]);
                messages.put(messageInfo[0], loadedMessage);
            }
        }
    }


    /**
     * Send message to a specific user
     * @param message the message to send
     **/
    public void sendMessage(Message message) {
        loadMessage(message.getMessageId(), message);
    }


    /**
     * This method returns all messages sent by user with senderId
     * @param senderId the ID of the sender
     * @return messages sent by user
     */
    public List<Message> printMessages(String senderId) {
        // presenter should call this method and turn array into output
        List<Message> userMessages = new ArrayList<Message>();
        for(Map.Entry<String, Message> message : messages.entrySet()) {
            if(message.getKey().equals(senderId)) {
                userMessages.add(message.getValue());
            }
        }
        // sort userMessages by time sent
        userMessages.sort(Comparator.comparing(Message::getTimeSent));
        return userMessages;
    }


    /**
     * This method returns all messages sent by user with senderId and received by user with receiverId
     * @param senderId the ID of the sender
     * @param receiverId the ID of the receiver
     * @return messages sent and received by user
     */
    public List<Message> getMessages(String senderId, String receiverId) {
        // presenter should call this method and turn array into output
        ArrayList<Message> userMessages = new ArrayList<Message>();
        for(Map.Entry<String, Message> message : messages.entrySet()) {
            if((message.getValue().getSenderId().equals(senderId) && message.getValue().getReceiverId().equals(receiverId))
                    || (message.getValue().getSenderId().equals(receiverId) && message.getValue().getReceiverId().equals(senderId)) ) {
                userMessages.add(message.getValue());
            }
        }
        // sort userMessages by time sent
        userMessages.sort(Comparator.comparing(Message::getTimeSent));
        return userMessages;
    }


    /**
     * For if presenter needs to access message using its Id (for printMessages)
     * @param messageId the id of message
     */
    public String getMessageFromMap(String messageId) {
        return messages.get(messageId).getStringRep();
    }


    /**
     * This method stores messages in an array
     * @return an array of all messages (new and old) for storage
     */
    public ArrayList<String> store() {
        ArrayList<String> allMessages = new ArrayList<String>();
        // store messages

        if (messages != null && !messages.isEmpty()) {
            for (Map.Entry<String, Message> message : messages.entrySet()) {
                allMessages.add(message.getValue().getStringRep() + "\n");
            }
        }
        return allMessages;
    }


    /**
     * This method accesses message IDs
     * @return message IDs in a list
     */
    public ArrayList<String> getMessengerIds() {
        ArrayList<String> storedMessenger= new ArrayList<String>();
        if (messages != null && !messages.isEmpty()) {
            for (Map.Entry<String, Message> o : messages.entrySet()) {
                storedMessenger.add(o.getValue().getMessageId() + "\n");
            }
        }
        return storedMessenger;
    }
}
