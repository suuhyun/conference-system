package parameterObjects;
import conference.ConferenceActions;
import event.EventActions;
import message.MessageActions;
import room.RoomActions;


/**
 * Parameter object for all use case classes involved in the system of events. This includes
 * messages, conferences, rooms, and the events themselves.
 * Note that this is an extensible design that makes it more convenient to add more in the system of events
 * instead of editing all the parameter lists. (It is also more readable.)
 */
public class EventSystemActions {

    private ConferenceActions conferenceActions;
    private EventActions eventActions;
    private MessageActions messageActions;
    private RoomActions roomActions;


    /**
     * instantiates the parameter object with all the use case objects involved in handling the system of events.
     * @param conferenceActions the conference use case
     * @param eventActions the events use case
     * @param messageActions the messages use case
     * @param roomActions the room use case
     */
    public EventSystemActions(ConferenceActions conferenceActions, EventActions eventActions, MessageActions messageActions, RoomActions roomActions){
        this.conferenceActions = conferenceActions;
        this.eventActions = eventActions;
        this.messageActions = messageActions;
        this.roomActions = roomActions;
    }


    /**
     * getter for the conference use case
     * @return conference use case
     */
    public ConferenceActions getConferenceActions() {
        return conferenceActions;
    }


    /**
     * getter for the events use case
     * @return events use case
     */
    public EventActions getEventActions() {
        return eventActions;
    }


    /**
     * getter for the messages use case
     * @return messages use case
     */
    public MessageActions getMessageActions() {
        return messageActions;
    }


    /**
     * getter for the rooms use case
     * @return rooms use case
     */
    public RoomActions getRoomActions() {
        return roomActions;
    }

}
