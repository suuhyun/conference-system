package user.attendee;


import user.UserController;
import message.MessageActions;
import event.EventActions;

import java.util.ArrayList;
import java.util.List;

public class AttendeeController extends UserController {
    private EventActions eventActions;
    private AttendeeActions attendeeActions;

    /**
     *
     * @param eventSystemActions actions related to the system of events
     * @param accountActions actions related to the account
     */
    public AttendeeController(parameterObjects.EventSystemActions eventSystemActions, parameterObjects.AccountActions accountActions) {
        super(eventSystemActions, accountActions, 'a');
        this.attendeeActions = accountActions.getAttendeeActions();
        this.eventActions = eventSystemActions.getEventActions();

    }

    @Override
    public boolean leaveEvent(String eventName, String userId){
        if (eventActions.getEventNames().containsKey(eventName)){
            String eventID = eventActions.getEventFromName(eventName).getId();
            if(this.attendeeActions.findUserFromId(userId).getEventList().contains(eventID)) {
                return this.attendeeActions.removeEventFromUser(eventID, userId);
            }
        }
        return false;
    }

    /**
     * Saves an event to list of saved events for an user.
     * @param eventName the name of the event to be save
     * @param userName the username of the user who's saved event list is updated
     * @return true if the event is successfully saved return false if not
     */
    public boolean saveEvent(String eventName, String userName){
        return this.attendeeActions.addEventToSavedEvent(eventName, userName);
    }

    /**
     * Removes an event from list of saved events for an user
     * @param eventName the name of the event to be removed
     * @param userID the id of the user who's saved event list is updated
     * @return true if the event is successfully removed return false if not
     */
    public boolean unSaveEvent(String eventName, String userID){
        String eventID = eventActions.getEventFromName(eventName).getId();
        return this.attendeeActions.removeEventFromSavedEvent(eventID, userID);
    }

    /**
     * Shows the events a given user saved
     *
     * @param user the user who wants to see their saved events
     * @return list of the events that a user saved in the form of a list
     * with the string representation of each aspect (title, dateTime, etc)
     */

    public List<List<String>> viewSavedEvents(String user) {
        Attendee a1 = attendeeActions.returnUsernameHashMap().get(user);
        List<String> savedEventList = a1.getSavedEventList();
        List<List<String>> saveEventList = new ArrayList<List<String>>();
        if (eventActions != null) {
            for (String event : savedEventList) {
                String title = eventActions.getEventFromName(event).getTitle();
                String dateTime = eventActions.getEventFromName(event).getDateTime();
                String roomId = eventActions.getEventFromName(event).getRoomID();
                List<String> speakers = eventActions.getEventFromName(event).getSpeakers();
                List<String> info = new ArrayList<String>();
                info.add(title);
                info.add(dateTime);
                info.add(roomId);
                info.addAll(speakers);
                saveEventList.add(info);
            }
        }
        return saveEventList;
    }

    /**
     * Shows the vip events to a vip attendee
     *
     * @param user the user ID on if they are a VIP
     * @return true if the attendee is a VIp
     */
    public boolean isVIP(String user){
        return attendeeActions.findUserFromUsername(user).getIsVIP();
    }

}