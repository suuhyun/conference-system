package user.organizer;
import user.MainMenuController;
import user.attendee.AttendeeActions;
import conference.Conference;
import user.speaker.SpeakerActions;
import event.Event;
import user.User;
import conference.ConferenceActions;
import room.RoomActions;
import event.EventActions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A controller class for organizer that decides what to do based on user input when choosing from the main menu.
 * @author Cynthia, Mizna, Jiessie
 * @version 1
 * */
public class OrganizerMainMenuController extends MainMenuController {
    private OrganizerController controller;
    private RoomActions room;
    private SpeakerActions speaker;
    private EventActions event;
    private OrganizerActions organizer;
    private String userID;
    private ConferenceActions conference;
    private OrganizerMessagePresenter displayMessage;
    private OrganizerEventPresenter displayEvent;
    private OrganizerConferencePresenter displayConferences;
    private AttendeeActions attendee;
    private Scanner scan = new Scanner(System.in);
    private HashMap<String, User> usernameHashmap = new HashMap<String, User>();


    /**
     * Instantiates the main menu responder object
     *
     * @param userID              the user ID
     * @param organizerController the controller responsible for organizer
     * @param room the use case for handling rooms
     * @param event the use case for handling events
     * @param conferenceActions the use case for handling conferences
     * @param accountActions the use case for handling the accounts
     */
    public OrganizerMainMenuController(String userID, OrganizerController organizerController, RoomActions room,
                                       EventActions event, ConferenceActions conferenceActions,
                                       parameterObjects.AccountActions accountActions) {

        super(userID, organizerController, room, accountActions.getSpeakerActions(), conferenceActions);
        this.userID = userID;
        this.displayMessage = new OrganizerMessagePresenter();
        this.displayEvent = new OrganizerEventPresenter();
        this.displayConferences = new OrganizerConferencePresenter();
        this.room = room;
        this.controller = organizerController;
        this.speaker = accountActions.getSpeakerActions();
        this.event = event;
        this.organizer = accountActions.getOrganizerActions();
        this.attendee = accountActions.getAttendeeActions();
        this.conference = conferenceActions;
    }

    /**
     * Responds to menu option 2- send message to....
     */
    public void option2SendMessage() {
        displayMessage.printMessageMenu();
        String option = scan.nextLine();
        OrganizerMessageMenuController menuController = new OrganizerMessageMenuController(this.controller);
        if (option.equals("1")) {
            menuController.option1(); // send message to all speakers
        }
        if (option.equals("2")) {
            menuController.option2(); // send message to all attendees of an event
        }
        if (option.equals("3")) {
            super.option2SendMessage(); // send message to one person
        }
    }

    /**
     * Responds to menu option 5
     */
    public void option5ViewAllContacts() {
        String username = controller.returnUserIDHashMap().get(userID).getUsername();

        displayEvent.promptViewContacts();
        String option = scan.nextLine();

        boolean catcher = true;

        while (catcher) {

            if (option.equalsIgnoreCase("x")) {
                displayEvent.promptEvent();
                String eventName = scan.nextLine();
                if (eventName.equalsIgnoreCase("x")) {
                    return;
                } else if (!event.getEventNames().containsKey(eventName)) {
                    displayEvent.noEvent();
                } else {
                    Event eventObject = event.getEventNames().get(eventName);
                    String eventID = event.getEventNames().get(eventName).getId();
                    if (organizer.getOrganizersEvents(username).contains(eventID)) {
                        displayEvent.allYourContactsEvent(eventObject.getAttendees());
                        catcher = false;
                    } else {
                        displayEvent.notYourEvent();
                    }
                }
            } else {
                List<String> newList = new ArrayList<>();
                for (String contact : controller.returnUserIDHashMap().get(userID).getContactsList()) {
                    if (controller != null) {
                        newList.add(controller.returnUserIDHashMap().get(contact).getUsername());
                    }
                }
                displayEvent.allYourContacts(newList);
            }

        }
    }

    /**
     * Responds to menu option 6 - create an event or new speaker
     */
    public void option6EventSignupCreateView() {
        String username = controller.returnUserIDHashMap().get(userID).getUsername();

        String title = "";
        boolean isVip = false;
        boolean noConference = true;
        boolean titleRun = false;
        String conferenceTitle = "";


        // Add this event to a conference

        // display conferences
        ArrayList<List<String>> conferences = conference.returnConferences();
        if (conferences.size() == 0) {
            displayConferences.noConferences();
            return;
        } else {

            while (noConference) {
//                List<List<String>> events = event.getEventsList();
//                HashMap<String, User> userIdHash = controller.returnUserIDHashMap();
                displayConferences.displayConferences(conferences);
                // displayConferences.displayConferences(conferences, events, userIdHash);
                displayEvent.promptConference();
                String conferenceInput = scan.nextLine();
                //System.out.println("HERE!" + conference.returnTitleHashMap());
                if (conferenceInput.equalsIgnoreCase("x")) {
                    return;
                } else if (conference.conferenceExists(conferenceInput)) {
                    conferenceTitle = conferenceInput;
                    noConference = false;

                } else {
                    displayEvent.invalidConference();
                    conferenceInput = scan.nextLine();
                    if (conferenceInput.equalsIgnoreCase("x")) {
                        return;
                    }
                }
            }
        }


        while (true) {
            displayEvent.promptTitle();
            title = scan.nextLine();
            if (title.equalsIgnoreCase("x")){
                return;
            } else if (!validInput(title)) {
                displayMessage.invalidInput();
            } else if (event.eventNameExists(title)) {
                displayEvent.eventExists();
            } else {
                displayEvent.promptVIP();
                String vip = scan.nextLine();
                if (vip.equals("Y") || vip.equals("y")) {
                    isVip = true;
                    break;
                }
                if (vip.equals("N") || vip.equals("n")) {
                    break;
                } else {
                    displayEvent.bad();
                }
            }
        }

        boolean catcher = true;
        List<String> dateTimes = getDateTimeInputs();
        String roomID = "";

        while (catcher) {
            displayRooms();
            displayEvent.promptRoom();
            String roomName = scan.nextLine();
            if (room != null) {
                if (room.returnRoomUsernameHashMap() != null) {
                    if (room.returnRoomUsernameHashMap().containsKey(roomName)) {
                        roomID = room.returnRoomUsernameHashMap().get(roomName).getRoomId();
                        catcher = false;
                    } else {
                        displayMessage.badRoom();

                        String reply = scan.nextLine();
                        if (reply.equalsIgnoreCase("ADD")) {
                            option9ScheduleOrRoom();
                        }
                    }
                }
            }
        }

        List<String> speakerUsernames = new ArrayList<>();
        displayMessage.zeroSpeakers();//
        String zeroSpeaker = scan.nextLine();
        String speakerId = "";

        if(zeroSpeaker.equalsIgnoreCase("y")){
            speakerUsernames.add(speakerId);
        } else {
            displayMessage.numberOfSpeaker();
            String numberOfSpeakers = scan.nextLine();
            int i = 0;
            while (i < Integer.parseInt(numberOfSpeakers)) {
                boolean catcherUserName = true;
                while (catcherUserName && i < Integer.parseInt(numberOfSpeakers)) {
                    displayMessage.newOrNoSpeaker();
                    String speakerUserName = scan.nextLine();
                    i++;

                    if (speakerUserName.equalsIgnoreCase("NEW")) {
                        String speakerUsername = createSpeaker();
                        if (speakerUserName != null) {
                            speakerId = speaker.getIDFromName(speakerUsername);
                            speakerUsernames.add(speakerId);
                            displayMessage.speakerAdded();
                        }

                    } else {
                        int catcher1 = Integer.parseInt(numberOfSpeakers);
                        while (catcher1 > 0) {
                            displayEvent.promptSpeaker();
                            String speakerUser = scan.nextLine();

                            if (controller != null && speaker != null) {
                                if (speaker.speakerExists(speakerUser)) {
                                    speakerId = speaker.returnUsernameHashMap().get(speakerUser).getId();

                                    if (speaker.returnUsernameHashMap().get(speakerUser).getEventList() != null) {
                                        if (controller.checkTimeConflict(speakerUser, dateTimes.get(0), dateTimes.get(1))) {
                                            displayEvent.failedDoubleBookSpeaker();

                                        } else {
                                            speakerUsernames.add(speakerId);
                                            displayMessage.speakerAdded();
                                        }
                                    }
                                    speakerUserName = speakerUser;
                                    catcher1 -= 1;
                                    catcherUserName = false;
                                } else {
                                    displayMessage.speakerDoesNotExist();
                                    return;
                                }
                                catcher1 -= 1;
                            }
                        }
                    }
                }
            }
        }

        int capacity = -1;
        boolean isInt = false;
        while (!isInt) {
            displayMessage.eventCapacity();
            String capStr = scan.nextLine();
            try {
                if (Integer.parseInt(capStr) > 0) {
                    capacity = Integer.parseInt(capStr);
                    isInt = true;
                } else {
                    displayMessage.invalidInput();
                }
            } catch (NumberFormatException e) {
                displayMessage.invalidCapaity();
            }
        }

        if (controller != null && event != null) {
            List<Boolean> checks = controller.createEvent(title, speakerUsernames, dateTimes.get(0), dateTimes.get(1), roomID, conferenceTitle, capacity, isVip);


            if (checks.get(0) && checks.size() == 1) {
                String eventToAdd = event.getEventNames().get(title).getId();
                organizer.addEventToUser(eventToAdd, username);
                organizer.addEventToUser(eventToAdd, username);
                if (conference.addEvent(conferenceTitle, eventToAdd)) {
                    displayEvent.successAddEvent();
                } else {
                    displayEvent.failedAddEventToConference();
                }

            } else {
                if (!checks.get(0)) {
                    int roomCap = room.findRoomFromId(roomID).getCapacity(); // necessary?
                    displayEvent.roomCapacityLow(roomCap);
                }
                if (!checks.get(1)) {
                    displayEvent.failedDoubleBookRoom();
                }
                if (!checks.get(2)) {
                    displayEvent.failedDoubleBookSpeaker();
                }
                displayEvent.notCreated();
            }
        }

    }

    private ArrayList<List<String>> getConferences() {
        HashMap<String, Conference> conferenceUsernameHash = new HashMap<>();
        if (conference != null) {
            conferenceUsernameHash = conference.returnTitleHashMap();
        }
        ArrayList<List<String>> stringRepConferences = new ArrayList<>();
        for (Map.Entry<String, Conference> entry : conferenceUsernameHash.entrySet()) {
            Conference conference = entry.getValue();
            List<String> stringRepConference = Arrays.asList(conference.getStringRep().split(","));
            //stringRepConference.add(conference.getTitle());
            //stringRepConference.add(conference.getStartDateTime());
            //stringRepConference.add(conference.getEndDateTime());

            stringRepConferences.add(stringRepConference);
        }
        return stringRepConferences;
    }

    /**
     * displays all the rooms that match the requirements
     */
    private void displayRooms() {
        ArrayList<String> rooms = new ArrayList<String>();
        displayEvent.promptNeedProjector();
        String needProjector = scan.nextLine();
        if (needProjector.equalsIgnoreCase("y")) {
            rooms.addAll(room.getRoomsWithProjector());
        } else {
            rooms.addAll(room.returnRoomUsernameHashMap().keySet());
        }
        displayEvent.promptNeedMicrophone();
        String needMicrophone = scan.nextLine();
        if (needMicrophone.equalsIgnoreCase("y")) {
            rooms.retainAll(room.getRoomsWithMicrophone());
        } else {
            rooms.retainAll(room.returnRoomUsernameHashMap().keySet());
        }
        displayEvent.promptNeedTables();
        String needTables = scan.nextLine();
        if (needTables.equalsIgnoreCase("y")) {
            rooms.retainAll(room.getRoomsWithTables());
        } else {
            rooms.retainAll(room.returnRoomUsernameHashMap().keySet());
        }
        displayEvent.promptNeedWhiteboard();
        String needWhiteboard = scan.nextLine();
        if (needWhiteboard.equalsIgnoreCase("y")) {
            rooms.retainAll(room.getRoomsWithWhiteboard());
        } else {
            rooms.retainAll(room.returnRoomUsernameHashMap().keySet());
        }
        displayEvent.viewRooms(rooms);
    }

    /**
     * Responds to menu option 7
     * Remove/reschedule an event
     */
    public void option7Cancel() {
        option8ViewAllEvents();
        String conferenceTitle = "";
        boolean hasConference = false;
        while(!hasConference) {
            displayEvent.promptEditConference();
            conferenceTitle = scan.nextLine();
            if (conference.conferenceExists(conferenceTitle)) {
                hasConference = true;
            } else {
                displayConferences.conferenceDoesNotExists();
            }

        }

        displayEvent.promptCancelMethod();
        String option = scan.nextLine();

        if (option.equalsIgnoreCase("x")) {

            boolean catcher = true;
            while (catcher) {
                displayEvent.promptCancelEvent();
                String eventCancel = scan.nextLine();

                if (eventCancel.equals("!")) {
                    displayEvent.warning();
                    String answer = scan.nextLine();
                    if (answer.equalsIgnoreCase("x")) {
                        break;
                    }
                }
                if (event.getEventNames().containsKey(eventCancel)) {
                    if (cancelEvent(eventCancel)) {
                        String eventID = event.getEventFromName(eventCancel).getId();
                        conference.removeEvent(conferenceTitle, eventID);
                        displayEvent.successCancelEvent();
                        catcher = false;
                    }

                } else {
                    displayEvent.noEvent();
                    displayEvent.breakPlease();
                }
            }
        } else {
            displayEvent.promptRescheduleMethod();
            String eventName = "";
            boolean catcher = true;

            while (catcher) {
                eventName = scan.nextLine();
                if (event.getEventNames().containsKey(eventName)) {
                    List<String> dateTimes = getDateTimeInputs();
                    rescheduleEvent(eventName, dateTimes.get(0), dateTimes.get(1));
                    catcher = false;
                } else {
                    displayEvent.noEvent();
                    displayEvent.breakPlease();
                }
                if (eventName.equals("!")) {
                    displayEvent.warning();
                    String answer = scan.nextLine();
                    if (answer.equalsIgnoreCase("x")) {
                        break;
                    }
                }

            }
        }
    }


    /**
     * helper function to take a start and end dateTime string object from separate date and time inputs
     *
     * @return the list of strings combining date and the start and end time based on separate user inputs for date and time
     */
    private List<String> getDateTimeInputs() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);
        String date = "";
        String startTime = "";
        String endTime = "";
        boolean catcher = true;

        while (catcher) {
            displayEvent.promptDate();
            String d1 = scan.nextLine();
            try {
                Date d = dateFormat.parse(d1);
                date = d.toString();
                catcher = false;
            } catch (ParseException ex) {
                displayEvent.badTime();
            }
        }


        catcher = true;
        while (catcher) {
            displayEvent.promptStartTime();
            String t1 = scan.nextLine();

            try {
                if (Integer.parseInt(t1) < 17 && Integer.parseInt(t1) >= 9) {
                    if (Integer.parseInt(t1) < 10) {
                        startTime = "0" + t1;
                    } else {
                        startTime = t1;
                    }
                    catcher = false;
                } else {
                    displayEvent.badTime();
                }
            } catch (NumberFormatException ex) {
                //else{
                displayEvent.badTime();
            }
        }

        catcher = true;
        while (catcher) {
            displayEvent.promptEndTime();

            String t2 = scan.nextLine();

            try {
                int start = Integer.parseInt(startTime);
                if (Integer.parseInt(t2) < 17 && Integer.parseInt(t2) >= 9 && Integer.parseInt(t2) > start) {
                    endTime = t2;
                    catcher = false;
                } else {
                    displayEvent.badTime();
                }
            } catch (NumberFormatException ex) {
                //else{
                displayEvent.badTime();
            }
        }

        List<String> dateTimes = new ArrayList<>();
        dateTimes.add(date + "-" + startTime);
        dateTimes.add(date + "-" + endTime);
        return dateTimes;
    }

    /**
     * helper function to reschedule event
     *
     * @param eventTitle       the event title
     * @param newStartDateTime the new start date and time for the event to be changed to
     * @param newEndDateTime   the new end date and time for the event to be changed to
     */
    private void rescheduleEvent(String eventTitle, String newStartDateTime, String newEndDateTime) {
        if (controller != null) {
            String e = event.getEventNames().get(eventTitle).getId();
            if (controller.rescheduleEvent(e, newStartDateTime, newEndDateTime)) {
                displayEvent.successRescheduleEvent();
            } else {
                displayEvent.failedRescheduleEvent();
            }
        }
    }

    /**
     * helper function to cancel event
     *
     * @param event the event ID
     */
    private boolean cancelEvent(String event) {
        if (controller != null) {
            if (controller.cancelEvent(event)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Responds to menu option 9- create a new room
     */
    public void option9ScheduleOrRoom() {

        boolean catcher = true;

        while (catcher) {
            displayEvent.promptAddRoom();
            String roomName = scan.nextLine();
            if (room.returnRoomUsernameHashMap().containsKey(roomName)) {
                displayMessage.alreadyAddedRoom();
                catcher = false;
            } else if (controller != null) {
                if (controller.createRoom(roomName)) {
                    addRoomProperties(roomName);
                    displayMessage.addedRoom();
                    catcher = false;
                }
            }

        }
    }

    public void addRoomProperties(String roomName) {
        displayEvent.promptHasProjector();
        String hasProjector = scan.nextLine();
        if (hasProjector.equalsIgnoreCase("y")) {
            room.setProjector(roomName);
        }
        displayEvent.promptHasMicrophone();
        String hasMicrophone = scan.nextLine();
        if (hasMicrophone.equalsIgnoreCase("y")) {
            room.setMicrophone(roomName);
        }
        displayEvent.promptHasTables();
        String hasTables = scan.nextLine();
        if (hasTables.equalsIgnoreCase("y")) {
            room.setTables(roomName);
        }
        displayEvent.promptHasWhiteboard();
        String hasWhiteboard = scan.nextLine();
        if (hasWhiteboard.equalsIgnoreCase("y")) {
            room.setWhiteboard(roomName);
        }
    }

    /***
     * Responds to menu option 10- create a new user
     * note: implements factory pattern
     */
    public void option10AddOrViewEvents() {
        boolean loop = true;
        displayMessage.printUserMenu();
        while (loop) {
            String option = scan.nextLine();
            if (option.equalsIgnoreCase("x")) {
                loop = false;
            } else if (option.equals("1")) {
                createSpeaker();
                loop = false;
            } else if (option.equals("2")) {
                createAttendee();
                loop = false;
            } else if (option.equals("3")) {
                createOrganizer();
                loop = false;
            } else {
                displayMessage.notValidChoice();
            }
        }

    }
    /**
     * Responds to menu option 8- view all events
     */
    public void option8ViewAllEvents() {
        ArrayList<List<String>> conferences = conference.returnConferences();
        displayConference.displayConferences(conferences);
        displayConference.promptConference();
        String conferenceTitle = scan.nextLine();

        while (!conference.conferenceExists(conferenceTitle)) {
            //conferenceTitle = scan.nextLine();
            displayConferences.conferenceDoesNotExists();
            displayConferences.leaveOption();
            if (scan.nextLine().equalsIgnoreCase("x")) {
                return;
            }
        }
        String username = controller.returnUserIDHashMap().get(userID).getUsername();
        List<List<String>> eventsList = controller.viewAvailableSchedule(username, conferenceTitle);
        if (eventsList.size() == 0) {
            displayMessage.noEvents();
        } else {

            for (List<String> e : eventsList) {
                e.set(2, room.findRoomFromId(e.get(2)).getRoomName());
                List<String> speakerList = new ArrayList<String>();
                List<String> speakers = Arrays.asList(e.get(3).split(","));
                for (String speakerID : speakers) {
                    if (speakerID.equals("")) {
                        speakerList.add(displayMessage.noSpeakers());
                    } else {
                        speakerList.add(speaker.findUserFromId(speakerID).getUsername());
                    }
                }
                e.set(3, String.valueOf(speakerList));
                //e.set(3, speakerActions.findUserFromId(e.get(3)).getUsername());
            }
            displayEvent.displayEvents(eventsList);
        }
    }


    public void option14ViewStatistics() {
        boolean loop = true;
        while (loop) {
            displayMessage.printStatsMenu();
            String option = scan.nextLine();
            if (option.equalsIgnoreCase("x")) {
                return;
            } else if (option.equals("1")) {
                displayMessage.numberEventsAvailable(event.numberEventsAvailable());
            } else if (option.equals("2")) {
                displayMessage.mostAttendedEvents(event.mostAttendedEvents());
            } else if (option.equals("3")) {
                displayMessage.leastAttendedEvents(event.leastAttendedEvents());
            } else if (option.equals("4")) {
                displayMessage.topFiveEvents(event.topFiveEvents());
            } else if (option.equals("5")) {
                displayMessage.newSessionAttendees(attendee.newSessionAttendees());
            } else if (option.equals("6")) {
                displayMessage.averageNumberAttendees(event.averageNumberAttendees());
            } else if (option.equals("7")) {
                displayMessage.medianNumberAttendees(event.medianNumberAttendees());
            } else if (option.equals("8")) {
                displayMessage.modeNumberAttendees(event.modeNumberAttendees());
            } else if (option.equals("9")) {
                displayMessage.numAtMaxCapacity(event.numAtMaxCapacity());
            } else if (option.equals("10")) {
                displayMessage.eventsOrderedByDate(event.eventsOrderedByDate());
            } else if (option.equals("11")) {
                displayMessage.totalNumberSpeakers(speaker.totalNumberSpeakers());
            } else if (option.equals("12")) {
                displayMessage.totalNumberAttendees(attendee.totalNumberAttendees());
            } else {
                displayMessage.notValidChoice();
            }
            option = scan.nextLine();
        }
    }

    /***
     * create a new speaker
     */
    private String createSpeaker() {
        displayMessage.createSpeakerMessage();
        while (true) {
            displayMessage.userUsernamePrompt();
            String speakerUsername = scan.nextLine();

            if (speakerUsername.equalsIgnoreCase("x")) {
                displayMessage.exit();
                break;
//            } else if (userActions.getMasterUsernameHashMap().containsKey(speakerUsername)){
            } else if (controller.usernameExists(speakerUsername)) {
                displayMessage.userExists();
            } else if (controller != null && validInput(speakerUsername)) {
                displayMessage.userPasswordPrompt();
                String newSpeakerPassword = scan.nextLine();
                if (validInput(newSpeakerPassword)) {
                    controller.createSpeaker(speakerUsername, newSpeakerPassword);
                    displayMessage.userCreated();
                    return speakerUsername;
                } else {
                    displayMessage.invalidInput();
                }
            } else {
                displayMessage.invalidInput();
            }
        }
        return null;
    }

    /***
     * create a new user
     */
    private void createAttendee() {
        displayMessage.createAttendeeMessage();
        while (true) {
            displayMessage.userUsernamePrompt();
            String newAttendeeUsername = scan.nextLine();

            if (newAttendeeUsername.equals("x") || newAttendeeUsername.equals("X")) {
                break;
//            } else if (userActions.getMasterUsernameHashMap().containsKey(newAttendeeUsername)){
            } else if (controller.usernameExists(newAttendeeUsername)) {
                displayMessage.userExists();
            } else if (controller != null && validInput(newAttendeeUsername)) {
                displayMessage.userPasswordPrompt();
                String newAttendeePassword = scan.nextLine();
                displayEvent.VIPStatusPrompt();
                boolean VIPStatus = false;
                String responseInput = scan.nextLine();
                if (responseInput.equalsIgnoreCase("y")) {
                    VIPStatus = true;
                }
                if (validInput(newAttendeePassword)) {
                    controller.createAttendee(newAttendeeUsername, newAttendeePassword, VIPStatus);
                    displayMessage.userCreated();
                    return;
                } else {
                    displayMessage.invalidInput();
                }
            } else {
                displayMessage.invalidInput();
            }
        }
    }

    /***
     * create a new organizer
     */
    private void createOrganizer() {
        displayMessage.createOrganizerMessage();
        while (true) {
            displayMessage.userUsernamePrompt();
            String userUsername = scan.nextLine();

            if (userUsername.equalsIgnoreCase("x")) {
                displayMessage.exit();
                break;
//            } else if (userActions.getMasterUsernameHashMap().containsKey(userUsername)){
            } else if (controller.usernameExists(userUsername)) {
                displayMessage.userExists();
            } else if (controller != null && validInput(userUsername)) {
                displayMessage.userPasswordPrompt();
                String userPasswordPrompt = scan.nextLine();
                if (validInput(userPasswordPrompt)) {
                    controller.createOrganizer(userUsername, userPasswordPrompt);
                    displayMessage.userCreated();
                    return;
                } else {
                    displayMessage.invalidInput();
                }
            } else {
                displayMessage.invalidInput();
            }
        }
    }

    /***
     * Responds to menu option 11- view all conferences
     */
    public void option11VIPOrConferences() {
        ArrayList<List<String>> conferences = conference.returnConferences();
        displayConference.displayConferences(conferences);}




    public void option12Conference() {

        OrganizerConferenceController menuController = new OrganizerConferenceController(this.controller, conference, event);
        menuController.createConference();
    }

    public void option13ChangeCapacity() {
        option8ViewAllEvents();
        displayEvent.changeEventCapacity();
        String eventName = scan.nextLine();

        if (event.getEventNames().containsKey(eventName)) {
            displayEvent.newCapacity(eventName);
            int newCapacity = scan.nextInt();

            event.getEventNames().get(eventName).setCapacity(newCapacity);
            displayEvent.capacityChanged();

        }
        else{
                displayEvent.failedNoSuchEvent();
            }
        }
    }


//        ArrayList<List<String>> conferences = conference.returnConferences();
//        displayConference.displayConferences(conferences);
//        displayConference.promptConference();
//        String conferenceTitle = scan.nextLine();
//
//        while (!conference.conferenceExists(conferenceTitle)) {
//            //conferenceTitle = scan.nextLine();
//            displayConferences.conferenceDoesNotExists();
//            displayConferences.leaveOption();
//            if (scan.nextLine().equalsIgnoreCase("x")) {
//                return;
//            }
//        }
//        String username = controller.returnUserIDHashMap().get(userID).getUsername();
//        List<List<String>> eventsList = controller.viewAvailableSchedule(username, conferenceTitle);
//        if (eventsList.size() == 0) {
//            displayMessage.noEvents();
//        } else {
//
//            for (List<String> e : eventsList) {
//                e.set(2, room.findRoomFromId(e.get(2)).getRoomName());
//                List<String> speakerList = new ArrayList<String>();
//                List<String> speakers = Arrays.asList(e.get(3).split(","));
//                for (String speakerID : speakers) {
//                    if (speakerID.equals("")) {
//                        speakerList.add(displayMessage.noSpeakers());
//                    } else {
//                        speakerList.add(speaker.findUserFromId(speakerID).getUsername());
//                    }
//                }
//                e.set(3, String.valueOf(speakerList));
//                //e.set(3, speakerActions.findUserFromId(e.get(3)).getUsername());
//            }
//            displayEvent.displayEvents(eventsList);
//        }

/***
 *  Responds to menu option 12 - Create Conference
 */
    /*
    public void option12() {
        displayConferences.promptCreateConferenceTitle();
        boolean noConference = true;
        ArrayList<List<String>> conferences = conference.returnConferences();
        while (noConference) {
            displayConferences.displayConferences(conferences);
            displayEvent.promptConference();
            String conferenceInput = scan.nextLine();
            if (conferenceInput.equalsIgnoreCase("x")) {
                return;
            } else if (conference.conferenceExists(conferenceInput)) {
                noConference = false;

            } else {
                ArrayList<String> events = new ArrayList<>();
                conference.createConference(conferenceInput, events);
                displayConferences.successCreateConference();
                break;
            }
        }


    }
    */

//        displayConference.promptConference();
//        String conferenceTitle = scan.nextLine();
//
//        while (!conference.conferenceExists(conferenceTitle)) {
//            //conferenceTitle = scan.nextLine();
//            displayConferences.conferenceDoesNotExists();
//            displayConferences.leaveOption();
//            if (scan.nextLine().equalsIgnoreCase("x")) {
//                return;
//            }
//        }
//        String username = controller.returnUserIDHashMap().get(userID).getUsername();
//        List<List<String>> eventsList = controller.viewAvailableSchedule(username, conferenceTitle);
//        if (eventsList.size() == 0) {
//            displayMessage.noEvents();
//        } else {
//
//            for (List<String> e : eventsList) {
//                e.set(2, room.findRoomFromId(e.get(2)).getRoomName());
//                List<String> speakerList = new ArrayList<String>();
//                List<String> speakers = Arrays.asList(e.get(3).split(","));
//                for (String speakerID : speakers) {
//                    if (speakerID.equals("")) {
//                        speakerList.add(displayMessage.noSpeakers());
//                    } else {
//                        speakerList.add(speaker.findUserFromId(speakerID).getUsername());
//                    }
//                }
//                e.set(3, String.valueOf(speakerList));
//                //e.set(3, speakerActions.findUserFromId(e.get(3)).getUsername());
//            }
//            displayEvent.displayEvents(eventsList);
//        }

//    public void option12Conference() {
//        // displayConferences.printOrganizerConferenceMenu();
//        //  String option = scan.nextLine();
//        user.organizer.OrganizerConferenceController menuController = new OrganizerConferenceController(this.controller, conference, event);
//        menuController.createConference();
//
////        if (option.equals("1")) {
////           ; // send message to all speakers
////        }
////        if (option.equals("2")) {
////            menuController.option2(); // send message to all attendees of an event
////        }
//    }
