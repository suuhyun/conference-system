package message;


/**
 * Class for messages and their ID, sender ID, receiver ID and time sent
 */
public class Message {
    //private static String lastMessageIdNums = "";
    private String messageId;
    private String senderId;
    private String receiverId;
    private String message;
    private String timeSent;


    /**
     * Instantiates a new message object
     * @param messageId the ID of the message
     * @param senderId the ID of the sender
     * @param receiverId the ID of the receiver
     * @param message the message
     * @param timeSent the time the message is sent
     */
    public Message(String messageId, String senderId, String receiverId, String message, String timeSent) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timeSent = timeSent;
    }



    /**
     * This method gets the string representation of the message
     * @return the message as a string with message ID, sender ID, receiver ID and the time sent
     */
    public String getStringRep() {
        return messageId + "%2%0%7%" + senderId + "%2%0%7%" + receiverId + "%2%0%7%" + message + "%2%0%7%" + timeSent;
    }


    /**
     * Getter for the ID of message.
     * @return ID of the message
     * */
    public String getMessageId() {
        return messageId;
    }


    /**
     * Getter for the ID of sender.
     * @return ID of the sender
     * */
    public String getSenderId() {
        return senderId;
    }


    /**
     * Getter for the ID of the receiver
     * @return ID of teh receiver
     * */
    public String getReceiverId() {
        return receiverId;
    }


    /**
     * Getter for the content of the message.
     * @return content of the message
     * */
    public String getMessage() {
        return message;
    }


    /**
     * Getter for the time sent of message.
     * @return time of message send
     * */
    public String getTimeSent() {
        return timeSent;
    }
}
