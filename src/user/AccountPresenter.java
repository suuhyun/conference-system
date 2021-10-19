package user;

/**
 * A presenter class. The AccountPresenter is responsible for anything related to logging in,
 * logging out, and navigating through the account.
 * @author multiple
 * @version 1
 * */

public class AccountPresenter {
    String basicMenu = ""
            + "\n[1] Log out"
            + "\n[2] Send a message"
            + "\n[3] View all messages"
            + "\n[4] Add a contact"
            + "\n[5] View all contacts"
            + "\n[13] View all Conferences";

    /**
     * Failed sign up. Username already exists
     */
    public void failedUsernameExists(){
        System.out.println("This username already exists.");
    }

    /**
     * A message informing user that signup was successful
     * */
    public void successSignUp(){
        System.out.println("You have successfully made a new account! You may now login with your new account." +
                "\nNote: You must login and logout completely in order to save your account.");
    }

    /**
     * successful login message
     */
    public void successLogin(){
        System.out.println("You have successfully logged in.");
    }


    /**
     * Invalid option
     */
    public void failedInvalidMenuOption(){
        System.out.println("Invalid option. Please enter again.");
    }


    /**
     * Quite option
     */
    public void promptQuit(){
        System.out.println("Enter 'x' if you would like to try another username, and any other key if you would" +
                "like to log in instead.");
    }

    /**
     * asks for sign up or login
     */
    public void printSignUpMenu(){
        System.out.println("Enter 'x' to sign up, and any other character to login.");
    }


    /**
     * Asks for user type
     */
    public void printUserTypeMenu(){
        String display = "Enter the number that best describes your role in this conference:"
                + "\n[1] Organizer"
                + "\n[2] Speaker"
                + "\n[3] Attendee";
        System.out.println(display);
    }


    /**
     * Prints the main menu
     * */
    public void printMainMenu(){
        System.out.println(basicMenu);
    }


    /**
     * Prompt for username
     * */
    public void promptUsername(){
        System.out.println("Please enter your username.");
    }


    /**
     * Prompt for password
     * */
    public void promptPassword(){
        System.out.println("Please enter your password.");
    }


    /**
     * A message informing user that the login failed
     * */
    public void failedLogin(){
        System.out.println("Either your username or password is incorrect.");
    }


    /**
     * Prompt to return to menu
     * */
    public void promptReturn(){
        System.out.println("Enter any key to return to the main menu." +
                "\nNote: You must log out of your account in order to save new information" +
                "\nThis is accessible in the main menu.");
    }


    /**
     * A message informing user of successful logout; option to log back in or exit the program.
     * */
    public void successLogout(){
        System.out.println("You have successfully logged out. Enter any key to log into another account;" +
                "Enter 'X' to exit the program.");
    }


    /**
     * A message informing user that the menu item number does not exist
     * */
    public void printMenuError() {
        System.out.println("This number is not an option, please enter another number.");
    }


    /**
     * A message informing user that there was a mistake with their answer
     * */
    public void printTypingError(){System.out.println(("There was an mistake with your answer. Please try again."));}


    /**
     * A prompt asking the user for their VIP status
     * */
    public void VIPStatusPrompt(){System.out.println("Please input 'VIP' if you are a VIP attendee, otherwise press any other key");}

}


