-- Drop tables



DROP TABLE if exists  Competition;
DROP TABLE if exists  AbstractCompetitor;
DROP TABLE if exists Player;
DROP TABLE if exists Team;
DROP TABLE if exists Player_Team;
DROP TABLE if exists Entry;

DROP TABLE if exists UnregisteredCompetitor;
DROP TABLE if exists Subscriber;

DROP TABLE if exists PodiumBet;
DROP TABLE if exists WinnerBet;
DROP TABLE if exists DrawBet;

-- Create tables
CREATE TABLE Competition
(
	name		        varchar(255) 	PRIMARY KEY	NOT NULL,
	closingDate 	    timestamp		NOT NULL,
	startingDate 		timestamp		NOT NULL,
	setteled 			BIT	DEFAULT B'0'	NOT NULL,
	isDraw 				BIT	DEFAULT B'0'	NOT NULL
);


CREATE TABLE AbstractCompetitor
(
	competitorName	varchar(255)	PRIMARY KEY	 NOT NULL
);


CREATE TABLE Player
(
	userName		varchar(255)	PRIMARY KEY	NOT NULL 
	
);


CREATE TABLE Team
(
	teamName		varchar(255)	PRIMARY KEY	NOT NULL
);



CREATE TABLE Player_Team
(
	playerName		varchar(255)		NOT NULL,
	teamName		varchar(255)		NOT NULL 
);


CREATE TABLE Entry
(
	competitionName		varchar(255)			NOT NULL,
	competitorName		varchar(255)			NOT NULL,
	rank				varchar(255)			NOT NULL,
	idEntry             INTEGER                 PRIMARY KEY	
);



CREATE TABLE Subscriber
(
  username     varchar(255) PRIMARY KEY,

  password     varchar(255)  NOT NULL,
  firstname    varchar(255)  NOT NULL,
  lastname     varchar(255)  NOT NULL,
  borndate     varchar(255)  NOT NULL,
  balance      INTEGER       NOT NULL

 
);

CREATE TABLE UnregisteredCompetitor
(
	username     varchar(255)  PRIMARY KEY, 
	firstname    varchar(255)  NOT NULL,
	lastname     varchar(255)  NOT NULL
	
);


CREATE TABLE PodiumBet
(
	idBet		    INTEGER 	  PRIMARY KEY NOT NULL,
	amount			INTEGER		  NOT NULL,
	idEntryFirst 	INTEGER		  NOT NULL,
	idEntrySecond	INTEGER		  NOT NULL,
	idEntryThird	INTEGER	      NOT NULL,
	betOwner        varchar(255)  NOT NULL
);

CREATE TABLE WinnerBet
(
	idBet		    INTEGER 	 PRIMARY KEY	NOT NULL,
	amount			INTEGER		 NOT NULL,
	idEntry   	    INTEGER		 NOT NULL,
	betOwner        varchar(255) NOT NULL
);

CREATE TABLE DrawBet
(
	idBet		    INTEGER 	  PRIMARY KEY	NOT NULL,
	amount			INTEGER		  NOT NULL,
	competitorName  varchar(255)  NOT NULL,
	betOwner        varchar(255)  NOT NULL
);

-- create primary key

alter table Player_Team
add primary key (playerName	,teamName);



-- create foreign key
alter table AbstractCompetitor
add foreign key (competitorName) references Team(teamName) ;
alter table AbstractCompetitor
add foreign key (competitorName) references Player(username) ;

alter table Player
add foreign key (userName) references UnregisteredCompetitor(username) ;
alter table Player
add foreign key (userName) references Subscriber(username) ;


alter table Player_Team
add foreign key (playerName) references Player(username) ;
alter table Player_Team
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