# Sport agenda. Web application
### Project description
PURPOSE: 
This application helps to process, storage and display information about sports events in the cut of events, teams and individual players for authorised users.

TARGET AUDIENCE:
Users, interested in tracking the performance of athletes, - fans, judges, coaches, scouts, statisticians.

TARGET PRODUCT OWNERS:
Sports federations, sports clubs, statistical organisations.

### Project Major Features:
- ACCESS CONTROL
	- The application implements user authorisation and authentication based on user roles.
- 3-LEVEL APPLICATION ARCHITECTURE
	- presentation layer
	- application layer
	- data layer
- TESTED
	- 85% of the code covered with the test scripts.
- MONOLITHIC STRUCTURE
	- The application has a monolithic structure.

 ### Used Technologies
 |                 | Technologies                                                   |
 | --------------- | -------------------------------------------------------------- |
 | Front End       | Thymeleaf, Html, Bootstrap 5                                   |
 | Back End        | JAVA 11, Spring Boot, Spring Core, Spring security, Spring MVC |
 | Data Base Tools | PostgreSQL, Hibernate (Orm), Spring Data JPA                   |
 | Other tools     | Mockito, JUnit 5, IntelliJ IDEA, Maven, Git                    | 

### Relationships between classes
Classes: Event , Team , Athlete, Person

Event <-> Team (n:n) – 1 team can participate in 1+ events, and 1 event can host 1+ teams.

Team <-> Athlete (n:n) – 1 athlete can participate in 1+ teams, and 1 team can have 1+ athletes.

Event <-> Athlete (n:n) – 1 athlete can take part in 1+ events, and 1 event can host 1+ athletes.

Class Person keeps information about users.

The database contains the following tables: events, teams, athletes, person;
also linking tables: events_teams, events_athletes, teams_athletes.

### User Stories
- ORDINARY USER
	- User can view events.
	- User can view teams.
	- User can view athletes.
	- User can login to the system with login and password.
- ADMINISTRATOR
	- Admin can create, edit, delete and view events.
	- Admin can create, edit, delete and view teams.
	- Admin can create, edit, delete and view athletes.
	- Admin can add and delete athletes and teams to the event.
	- Admin can add and delete athletes and events to the team.
	- Admin can add and delete teams and events to the athlete.
	- Admin can login to the system with login and password.
	
### Access rights and authorities
![Image alt](https://github.com/ipavelbel/sportsresult/raw/master/pages/rols.png)

### Application Screens
![Image alt](https://github.com/ipavelbel/sportsresult/raw/master/pages/loginpage.png)
![Image alt](https://github.com/ipavelbel/sportsresult/raw/master/pages/listOfEvents.png)
![Image alt](https://github.com/ipavelbel/sportsresult/raw/master/pages/oneEvent.png)


