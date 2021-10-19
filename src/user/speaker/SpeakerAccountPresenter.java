package user.speaker;

import user.AccountPresenter;

/**
 * A presenter class. This class is responsible for anything related to logging in,
 * logging out, and navigating through the account. It also creates a menu specific to a speaker.
 * @author multiple
 * @version 1
 * */
public class SpeakerAccountPresenter extends AccountPresenter {
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
                + "\n[6] View your schedule of talks"
                + "\nPlease select a menu item number.";
        System.out.println(display);
    }
}
