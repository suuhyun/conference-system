READ ME 
###### **README FILE** - G-0082
## Conference System Program
_This program creates a system allowing users to login in and according to their account type, schedule for events, message other users and present personalized schedules, whilst implementing design rules from Clean Architecture._
 
### How this program works.
The user is first asked to either sign up or log in to their account. When signing up they have the choice of 3 different user types; Organizer, Speaker and Attendee. When logging into the account the user is prompted to give their username and password, if they are inputted incorrectly or if they do not exist. The user will be taken back to the starting menu.
 
Once they have logged in, depending on their role in the program, they will be presented with different menus, allowing different actions.
All users can;
            
    [1] Log out
    [2] Send a message
    [3] View all messages
    [4] Add a contact
    [5] View all contacts
            
#### Option [1] - Log Out
 
Allows them to log out, saving the information they have created in a corresponding csv file, which will be then loaded. If a user does not log out, their information will not be stored.
 
 
#### Option [2] - Send a Message
 
Allows them to send a message. The recipients of the messages will differ for each user.
- Organizers can send messages to all speakers and all attendees of an event, as well as individually
- Speakers can send messages to all attendees of one event or multiple events, as well as respond to a specific attendee
- Attendees can send messages to other attendees, as well as view messages that have been sent to them.
 
#### Option [3] - View all Messages
 
Allows users to see all the messages that have been sent, and are able to reply to them, categorized by users. 
 
#### Option [4] - Add a Contact
 
Allows users to add any contact they want.
 
#### Option [5] - View all Contacts
 
Allows users to view all the contacts they have already added.
            
 
### Organizer
The organizer-specific menu is extended by the options below;
 
    [6] Add an event and/or create new speaker account and/or create room
    [7] Remove or Reschedule an event
    [8] View all your created events
    [9] Add room
    [10] Add user
    [11] View all conferences
    [12] Add a conference
    [13] Change capacity of an event
    [14] View Statistics
                
#### Option [6]
 
Allows organizers to create an event, and in doing so, also allows them to create a new room, and/or adding a new speaker.
 - They can also use existing speakers and rooms. 
 - This option checks if speakers are double-booked or if the room is double-booked
 
#### Option [7] (Remove/reschedule)
 
Allows organizers to remove or reschedule an event. This option will also check if the event exists and if rescheduling will cause errors. Option [8] allows organizers to view all the events that the current user has created
 
#### Option [8]  (View all created events)
 
Allows organizers to view all created events
 
#### Option [9] (Add room)
 
Allows organizers to create a room specifying its requirement for projectors, tables, chairs, microphones and whiteboards.
 +***New Feature!*** Added requirements to rooms and events, which can be matched accordingly.
 
 
#### Option [10] - NEW FEATURE (Add user)
 
Allows organizers to create any type of users; Attendee, Speakers and Organizers. They can specify whether an Attendee is a VIP or not. 
 
#### Option [11] - NEW FEATURE (View all conferences)
 
Allows organizers to view all created conferences, presenting each conference title.
 
#### Option [12] - NEW FEATURE (Add a conference)
Allows organizers to create a new conference which then can have events be added into them from another option.
 
#### Option [13] - NEW FEATURE (Change the capacity of an event)
 
Allows organizers to change the capacity of an event as long as it is less than the room capacity.
 
#### Option [14] - NEW FEATURE (View Statistics)
 
Allows organizers to view the following list of statistics. 
 
    [1] View number of offered events
    [2] Most attended events
    [3] Least attended event
    [4] Top 5 events
    [5] New session attendees
    [6] Average number of attendees
    [7] Median number of attendees
    [8] Mode number of attendees
    [9] Number of events at max capacity
    [10] Events ordered by dates
    [11] Total number of speakers
    [12] Total number of attendees
 
### Speaker
The speaker-specific menu is extended by the option below;
     
    [6] View your schedule of talks
    
Option [6] allows the speaker to view the events they are scheduled to speak at.
 
 
### Attendee
The attendee-specific menu is extended by the options below;
    
    [6] Sign up for an event / Save an event"
    [7] Cancel enrollment for an event"
    [8] View all events"
    [9] View your schedule of events"
    [10] View your saved events"
    [11] View VIP events"
    [12] Sign up for conference
    
    
#### Option [6] - Sign up for an event / Save an event
 
Allows the attendee to sign up to an event after showing the available events to them. It does not present the events that conflict with events that they are already signed up for, full events, nor the events they are already signed up for. 
 
 +***New Feature!*** It also allows the attendee to save an event. This will then allow them to view these saved events at a later time and 
 
 
#### Option [7] - Cancel enrollment for an event
 
Allows the attendee to cancel their spot for an event they are already signed up for. Attendee must be signed up to an event.
 
#### Option [8] - View all your events
 
Allows the attendee to view all events in conferences they are signed up for. The attendee must be signed up for conferences, but do not have to be signed up for any of the events in the conferences. These events are the possible events available to the attendee to sign up to, _with no conflicts_. This does not include VIP events.
 
#### Option [9] - View your schedule of events
 
Allows the attendee to view the schedule of events they are already signed up for, showing the title, speaker and room that it will take place in. The attendee must be signed up for events.
 
#### Option [10] - (NEW FEATURE) View saved events
 
Allows attendees to view the events they have saved. 
 
#### Option [11] - (NEW FEATURE) View VIP events
 
Allows VIP attendees to view VIP events. If a non-VIP attendee tries this option, it will not allow the attendee to view. 
 
#### Option [12] - (NEW FEATURE) Sign up for conference
 
Allow attendees to sign up to conferences. Doing so will allow attendees to view events that they are attending.
 
 
## Design
 
Our code implements the design rules from Clean Architecture. The UML diagram of this design can be found in the UML file.
The choice to store the data in the csv files was made to prepare for the implementation for phase 2, as the way the information is processed, is similar to the way databases are used.
The design patterns we implemented are Dependency Injection and Factory methods. 
Dependency Injection is known as a technique in which a certain object receives other objects that it depends on. These other objects are known as dependencies. In our program, attendees, organizers, and speaker action classes will use dependency injection when loading their users into the program, by passing in loaded parameters.  
The factory method allows programmers to create objects without stating the class of the object. In our project, the users can be created without stating which class they are in. This is very useful because the user can possibly be speaker, organizer or attendee and that does not need to be disclosed as the object is created. We implement the factory pattern by implementing and overriding the createUser method in all userAction classes.

Extra notes:
* The storable interface is implemented in every usecase classes that needs to store the entities to make the code more readable and extensible. 
* The parameterObjects AccountActions and EventSystemActions is our solution to having too many parameters that are often used together, which is a code smell.  



NEW FEATURES: 
An amazing feature that we have added in this program is to allow the same users to log in and select which conference they want to participate in and further allow them to select events within those conferences. We allow users to both save an event, and sign up for the event. For participation means viewing and signing up for events. The inbox is made as one general inbox for all messages from all conferences to that user. 
Another feature we added is for our organizers to have  additional constraints to the scheduling.  For various types of events, they may require a  projector,  microphone, tables, and whiteboard. Our program will look through every room and provide the best one according to your requirements.  When organizers are creating events, they can see a suggested list of rooms that fit the requirements of their event. This allows all your technical needs to be met without arriving at the room.
After all the hard work our organizers will be completing to create new conferences and events, we would like to provide  a summary of everything that has been completed. Our upgraded features will allow organizers to view the following statistics: 

    [1] View number of offered events
    [2] Most attended events
    [3] Least attended event
    [4] Top 5 events
    [5] New session attendees
    [6] Average number of attendees
    [7] Median number of attendees
    [8] Mode number of attendees
    [9] Number of events at max capacity
    [10] Events ordered by dates
    [11] Total number of speakers
    [12] Total number of attendees




###### Designed and Coded by : Mizna Dada, Jiessie Tie, Cynthia Shen, Eryka Shi, Akiki Liang, Sejal Sahni, Jennifer Vo, Suhyun Park






