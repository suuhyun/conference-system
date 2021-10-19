package user.organizer;

import user.AccountPresenter;

/**
 * A presenter class. This class is responsible for anything related to logging in,
 * logging out, and navigating through the account. It also creates a menu specific to an organizer.
 * @author multiple
 * @version 1
 * */
public class OrganizerAccountPresenter extends AccountPresenter {
    String basicMenu = ""
            + "\n[1] Log out"
            + "\n[2] Send a message"
            + "\n[3] View all messages"
            + "\n[4] Add a contact"
            + "\n[5] View all contacts";
    /**
     * Prints the main menu
     * */
    public void printMainMenu(){
        String display = basicMenu
                + "\n[6] Add an event/create new speaker account"
                + "\n[7] Remove/reschedule an event"
                + "\n[8] View all your created events"
                + "\n[9] Add room"
                + "\n[10] Add user"
                + "\n[11] View all conferences"
                + "\n[12] Add a conference"
                + "\n[13] Change capacity of an event"
                + "\n[14] View Statistics"
                + "\nPlease select a menu item number.";
        System.out.println(display);
    }
}
