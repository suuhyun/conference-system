package parameterObjects;
import user.attendee.AttendeeActions;
import user.speaker.SpeakerActions;
import user.organizer.OrganizerActions;
import user.UserAccountActions;


/**
 * Parameter object for all use cases involving using the account to access the system of events.
 * This includes account actions for attendees, speakers, and organizers.
 * Note that this is an extensible design that makes it more convenient to add more types of accounts
 * instead of editing all the parameter lists. (It is also more readable.)
 */
public class AccountActions {
    private AttendeeActions attendeeActions;
    private SpeakerActions speakerActions;
    private OrganizerActions organizerActions;
    private UserAccountActions userAccountActions;


    /**
     * Instantiates the parameter object with all the use case objects that handle the account.
     * @param attendeeActions use case for attendee account
     * @param speakerActions use case for speaker account
     * @param organizerActions use case for organizer account
     */
    public AccountActions(AttendeeActions attendeeActions, SpeakerActions speakerActions, OrganizerActions organizerActions, UserAccountActions userAccountActions){
        this.attendeeActions = attendeeActions;
        this.speakerActions = speakerActions;
        this.organizerActions = organizerActions;
        this.userAccountActions = userAccountActions;
    }


    /**
     * getter for attendee use case
     * @return attendee use case
     */
    public AttendeeActions getAttendeeActions(){
        return this.attendeeActions;
    }


    /**
     * getter for speaker use case
     * @return speaker use case
     */
    public SpeakerActions getSpeakerActions(){
        return this.speakerActions;
    }


    /**
     * getter for organizer use case
     * @return organizer use case
     */
    public OrganizerActions getOrganizerActions(){
        return this.organizerActions;
    }


    /**
     * getter for general user object use case
     * @return user use case
     */
    public UserAccountActions getUserAccountActions(){return this.userAccountActions;}

}
