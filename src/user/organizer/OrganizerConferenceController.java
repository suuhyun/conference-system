package user.organizer;

import conference.Conference;
import event.EventPresenter;
import conference.ConferenceActions;
import event.EventActions;

import java.util.*;

public class OrganizerConferenceController {
    private OrganizerController controller;
    private OrganizerConferencePresenter displayConference;
    private EventPresenter displayEvent;
    private ConferenceActions conferenceActions;
    private EventActions eventActions;
    private Scanner scan = new Scanner(System.in);

    public OrganizerConferenceController(OrganizerController controller, ConferenceActions conference, EventActions event) {
        this.controller = controller;
        this.conferenceActions = conference;
        this.eventActions = event;
        this.displayConference = new OrganizerConferencePresenter();
        this.displayEvent = new EventPresenter();
    }

    /**
     * Responds to menu option 1 - create conference
     */
    public void createConference() {
        // prompt conference constructor objects

        HashMap<String, Conference> titleHash = conferenceActions.returnTitleHashMap();

        while (true) {
            displayConference.promptCreateConferenceTitle();
            String conferenceTitle = scan.nextLine();
            if (conferenceTitle.equalsIgnoreCase("x") || conferenceTitle.equalsIgnoreCase("")) {
                displayConference.invalidInput();
            } else if (!titleHash.containsKey(conferenceTitle)) {
                // when prompt for events, print list of events
                // addEvents(conferenceTitle, true);
                conferenceActions.createConference(conferenceTitle, new ArrayList<>());
                displayConference.successCreateConference();
                break;
            } else {
                displayConference.conferenceExists();
            }
        }
    }
}

