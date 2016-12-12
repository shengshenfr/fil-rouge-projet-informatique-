-- Drop tables



DROP TABLE Competition;
DROP TABLE AbstractCompetitor;
DROP TABLE Player;
DROP TABLE  Team;
DROP TABLE  PlayerTeam;
DROP TABLE Entry;


DROP TABLE  Subscriber;

DROP TABLE PodiumBet;
DROP TABLE  WinnerBet;
DROP TABLE  DrawBet;


-- Create tables
CREATE TABLE Competition
(
	name		        varchar(255) 	PRIMARY KEY	NOT NULL,
	closingDate 	    date		NOT NULL,
	startingDate 		date		NOT NULL,
	setteled 			INTEGER	DEFAULT 0	CHECK(setteled in (0,1)),
	isDraw 				INTEGER	DEFAULT 0	CHECK(isDraw in (0,1)),
	totalTokens		LONG       NOT NULL
);


CREATE TABLE AbstractCompetitor
(
	competitorName	varchar(255)	PRIMARY KEY	 NOT NULL
);


CREATE TABLE Player
(
	userName		varchar(255)	PRIMARY KEY	NOT NULL ,
	firstName		varchar(255)		NOT NULL ,
	lastName		varchar(255)		NOT NULL ,
	bornDate		varchar(255)		NOT NULL 
	
);


CREATE TABLE Team
(
	teamName		varchar(255)	PRIMARY KEY	NOT NULL
);



CREATE TABLE PlayerTeam
(
	idPlayerTeam		INTEGER                 PRIMARY KEY	NOT NULL,
	playerName		varchar(255)		NOT NULL,
	teamName		varchar(255)		NOT NULL 
);


CREATE TABLE Entry
(
	competitionName		varchar(255)			NOT NULL,
	competitorName		varchar(255)			NOT NULL,
	rank			INTEGER			NOT NULL,
	idEntry             INTEGER                 PRIMARY KEY	
);



CREATE TABLE Subscriber
(
  username     varchar(255) PRIMARY KEY,

  password     varchar(255)  NOT NULL,
  firstname    varchar(255)  NOT NULL,
  lastname     varchar(255)  NOT NULL,
  borndate     date  NOT NULL,
  balance      INTEGER       NOT NULL

 
);



CREATE TABLE PodiumBet
(
	idBet		    INTEGER 	  PRIMARY KEY,
	amount		LONG	  NOT NULL,
	idEntryFirst 	INTEGER		  NOT NULL,
	idEntrySecond	INTEGER		  NOT NULL,
	idEntryThird	INTEGER	      NOT NULL,
	betOwner        varchar(255)  NOT NULL
);

CREATE TABLE WinnerBet
(
	idBet		    INTEGER 	 PRIMARY KEY,
	amount			LONG		 NOT NULL,
	idEntry   	    INTEGER		 NOT NULL,
	betOwner        varchar(255) NOT NULL
);

CREATE TABLE DrawBet
(
	idBet		    INTEGER 	  PRIMARY KEY,
	amount			LONG	  NOT NULL,
	competitorName  varchar(255)  NOT NULL,
	betOwner        varchar(255)  NOT NULL
);


-- create foreign key

alter table PlayerTeam
add foreign key (playerName) references Player(userName) ;
alter table PlayerTeam
add foreign key (teamName) references Team(teamName);


alter table Entry
add foreign key (competitionName) references Competition(name) ;
alter table Entry
add foreign key (competitorName	) references AbstractCompetitor(competitorName);

alter table PodiumBet
add foreign key (idEntryFirst) references Entry(idEntry) ;
alter table PodiumBet
add foreign key (idEntrySecond) references Entry(idEntry);
alter table PodiumBet
add foreign key (idEntryThird) references Entry(idEntry) ;
alter table PodiumBet
add foreign key (betOwner ) references Subscriber(username);

alter table WinnerBet
add foreign key (idEntry) references Entry(idEntry) ;
alter table WinnerBet
add foreign key (betOwner ) references Subscriber(username);

alter table DrawBet
add foreign key (competitorName ) references Competition(name);
alter table DrawBet
add foreign key (betOwner ) references Subscriber(username);
