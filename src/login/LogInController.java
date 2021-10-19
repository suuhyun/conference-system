package login;

import user.AccountPresenter;
import login.LoginActions;
import user.UserAccountActions;
import user.UserController;

import java.util.Scanner;

public class LogInController {
    private Scanner scan = new Scanner(System.in);  // Create a Scanner object
    private AccountPresenter accountDisplay = new AccountPresenter();
    private UserController controller;


    /**
     * This method is called when the user is logging in after they have inputted a username and password and checks if
     * the username and password are correct, if so returns the user object which was logging in, otherwise returns null
     * @param username A string the user inputs as their username
     * @param password A string the user inputs as their password
     * @return The user object of the user if the login was successful otherwise null if given username and password
     * are incorrect.
     */
    public String logIn(String username, String password, parameterObjects.AccountActions accountActions) {
        LoginActions l = new LoginActions();
        String type = l.isLogin(username, password, accountActions);
        return type;
    }


    /**
     * This method is called when the user enter 'x' to sigh up. It reads the username and password the user inputted.
     * @param userController controller responsible for all users
     * @param accountActions the use case for attendee, organizer, speaker
     */
    public void signUp(UserController userController, parameterObjects.AccountActions accountActions) {

        while (true){

            accountDisplay.printSignUpMenu();
            String loginOption = scan.nextLine();
            if (loginOption.equals("x") || loginOption.equals("X")) {

            accountDisplay.promptUsername();
            String username = scan.nextLine();  // Read user input

            accountDisplay.promptPassword();
            String password = scan.nextLine();  // Read user input


            accountDisplay.printUserTypeMenu();

                if(signUpCheck(username, password, userController, accountActions)){
                    accountDisplay.successSignUp();
                    break;
                }
            }
            else{
                break;
            }
        }

    }


    /**
     * Checks if username is unique
     * @param username A string the user inputs as their username
     * @param password A string the user inputs as their password
     * @param accountActions the use case for attendee, speaker, organizer
     * @return true if the username is unique otherwise return false
     */
    private boolean signUpCheck(String username, String password, UserController userController,
                                parameterObjects.AccountActions accountActions) {
        String userType = scan.nextLine();
        if (userController.usernameExists(username)){
            accountDisplay.failedUsernameExists();
            return false;
        } else {
            // note: implements factory pattern
            UserAccountActions userActions;
            if (userType.equals("1")) {
                userActions = accountActions.getOrganizerActions(); // .createOrganizer(username, password);
            } else if (userType.equals("2")) {
                userActions = accountActions.getSpeakerActions(); // .createSpeaker(username, password, new ArrayList<String>(), new ArrayList<String>(), false);
            } else if (userType.equals("3")) {
                userActions = accountActions.getAttendeeActions();

            } else{
                accountDisplay.printTypingError();
                return false;
            }

            userActions.createUser(username, password);
            return true;

        }
    }
}