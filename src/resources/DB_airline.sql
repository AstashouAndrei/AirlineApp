CREATE SCHEMA `airline` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

USE airline;
CREATE TABLE `access` (
  `id` int(11) NOT NULL,
  `Access` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Access_UNIQUE` (`Access`),
  UNIQUE KEY `AccessID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
  
	INSERT airline.access(id, Access) 
VALUES
(1, 'Administrator'),
(2, 'Dispatcher');



USE airline;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `AccessID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `Login_UNIQUE` (`Login`),
  KEY `AccessID_idx` (`AccessID`),
  CONSTRAINT `AccessID` FOREIGN KEY (`AccessID`) REFERENCES `access` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

	INSERT airline.user(Login, Password, AccessID) 
VALUES
('Robert', 'RobertAdm', 1),
('Sean', 'SeanAdm', 2),
('Ronald', 'RonaldDisp', 2),
('Steave', 'SteaveDisp', 2);



USE airline;
CREATE TABLE `professions` (
  `id` int(11) NOT NULL,
  `Profession` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `profession` (`Profession`),
  UNIQUE KEY `ProfessionID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

	INSERT airline.professions(id, Profession) 
VALUES
(1, 'Captain'),
(2, 'First Officer'),
(3, 'Navigator'),
(4, 'Radio Operator'),
(5, 'Flight Attendant');


USE airline;

CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `ProfessionID` int(11) NOT NULL,
  `CurrentState` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  UNIQUE KEY `idnew_table_UNIQUE` (`id`),
  KEY `ProfessionID` (`ProfessionID`),
  CONSTRAINT `ProfessionID` FOREIGN KEY (`ProfessionID`) REFERENCES `professions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
 
INSERT airline.staff(FirstName, LastName, ProfessionID, CurrentState) 
VALUES

('Daniel', 'Jordan', 1, 'Standby'),
('Jeffrey', 'Grant', 1, 'Standby'),
('David', 'Harrell', 1, 'Standby'),
('Peter', 'Weaver', 1, 'Standby'),
('Jack', 'Parsons', 1, 'Standby'),
('Henry', 'Lucas', 1, 'Standby'),
('Nigel', 'Williams', 1, 'Standby'),
('Gilbert', 'Lambert', 1, 'Standby'),
('John', 'Fisher', 1, 'Standby'),
('Oliver', 'Caldwell', 1, 'Standby'),

('Mark', 'Wade', 2, 'Standby'),
('Robert', 'Osborne', 2, 'Standby'),
('Roger', 'McKinney', 2, 'Standby'),
('Peter', 'Lindsey', 2, 'Standby'),
('Silvester', 'Cameron', 2, 'Standby'),
('Dwayne', 'Preston', 2, 'Standby'),
('Brian', 'Jenkins', 2, 'Standby'),
('Mark', 'Welch', 2, 'Standby'),
('Steven', 'Briggs', 2, 'Standby'),
('Barnaby', 'Jacobs', 2, 'Standby'),

('Oliver', 'Hudson', 3, 'Standby'),
('Robert', 'Preston', 3, 'Standby'),
('Edmund', 'Byrd', 3, 'Standby'),
('Melvyn', 'Lloyd', 3, 'Standby'),
('John', 'Bates', 3, 'Standby'),
('Thomas', 'Harrison', 3, 'Standby'),
('Leslie', 'Williams', 3, 'Standby'),
('Randall', 'Lamb', 3, 'Standby'),
('Bennett', 'Nicholson', 3, 'Standby'),
('David', 'McDaniel', 3, 'Standby'),

('Jacob', 'Owens', 4, 'Standby'),
('John', 'Rogers', 4, 'Standby'),
('Jeffery', 'Gray', 4, 'Standby'),
('Gervais', 'Potter', 4, 'Standby'),
('Gilbert', 'Gilmore', 4, 'Standby'),
('Adam', 'Hubbard', 4, 'Standby'),
('Robert', 'McCarthy', 4, 'Standby'),
('Christopher', 'Rich', 4, 'Standby'),
('Elwin', 'McDonald', 4, 'Standby'),
('Paul', 'Manning', 4, 'Standby'),

('Loreen', 'Bell', 5, 'Standby'),
('Meredith', 'Lynch', 5, 'Standby'),
('Madison', 'Turner', 5, 'Standby'),
('Suzan', 'Neal', 5, 'Standby'),
('Elizabeth', 'Ross', 5, 'Standby'),
('Barbara', 'Walker', 5, 'Standby'),
('Kristina', 'Taylor', 5, 'Standby'),
('Pearl', 'Flowers', 5, 'Standby'),
('Loraine', 'Potter', 5, 'Standby'),
('Anne', 'Shields', 5, 'Standby'),
('Donna', 'Wilson', 5, 'Standby'),
('Emily', 'Harper', 5, 'Standby'),
('Annis', 'Baker', 5, 'Standby'),
('Helen', 'Morrison', 5, 'Standby'),
('Lucinda', 'Weaver', 5, 'Standby'),
('Brenda', 'Patrick', 5, 'Standby'),
('Kelly', 'Ferguson', 5, 'Standby'),
('Elizabeth', 'Shaw', 5, 'Standby'),
('Matilda', 'Robinson', 5, 'Standby'),
('Lindsey', 'Jefferson', 5, 'Standby'),
('Emma', 'Russell', 5, 'Standby'),
('Ann', 'McKenzie', 5, 'Standby'),
('Darleen', 'Davidson', 5, 'Standby'),
('Emily', 'Joseph', 5, 'Standby'),
('Elfreda', 'Whitehead', 5, 'Standby'),
('Elizabeth', 'Simpson', 5, 'Standby'),
('Karin', 'Wade', 5, 'Standby'),
('Emily', 'Walton', 5, 'Standby'),
('Marilynn', 'Walters', 5, 'Standby'),
('Dinah', 'Foster', 5, 'Standby'),
('Cobb', 'Moreu', 5, 'Standby'),
('Merryl', 'Horton', 5, 'Standby'), 
('Donna', 'Floyd', 5, 'Standby'),
('Olivia', 'Dennis', 5, 'Standby'),
('Helen', 'Bates', 5, 'Standby'),
('Lee', 'Ross', 5, 'Standby'),
('Thomasine', 'Richards', 5, 'Standby'),
('April', 'Walters', 5, 'Standby'),
('Welch', 'Foster', 5, 'Standby'),
('Brittney', 'Clarke', 5, 'Standby');

USE airline;

CREATE TABLE `planes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Model` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `PassengerCapacity` int(11) NOT NULL,
  `FlightRange` int(11) NOT NULL,
  `FuelConsumption` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PlaneID_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

    INSERT airline.planes (Model, PassengerCapacity, FlightRange, FuelConsumption) 
VALUES
('Airbus A330', 300, 13000, 5700),
('Boeing 737-800', 220, 9000, 3800),
('Airbus A320', 260, 10000, 4000),
('Boeing 737-200ER', 220, 8000, 4700),
('Airbus A321', 280, 12000, 4500),
('Sukhoi SJ100', 210, 7500, 4300),
('Boeing 738 MAX', 330, 12000, 4200);



USE airline;

CREATE TABLE `itinerary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FlightCode` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Departure` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Arrival` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

  INSERT airline.itinerary(FlightCode, Departure, Arrival) 
VALUES
('SU204', 'Moscow', 'Beijing'),
('SU0234', 'Moscow', 'Delhi'),
('SU273', 'Bangkok', 'Moscow'),
('SU1193', 'Kazan', 'Moscow'),
('SU0504', 'Moscow', 'Tel Aviv'),
('SU6028', 'Moscow', 'Cairo'),
('SU2352', 'Vienna', 'Moscow');



USE airline;

CREATE TABLE `flight` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ItineraryID` int(11) NOT NULL,
  `PlaneID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `FlightID_UNIQUE` (`id`),
  KEY `ItineraryID_idx` (`ItineraryID`),
  KEY `PlaneID_idx` (`PlaneID`),
  CONSTRAINT `ItineraryID` FOREIGN KEY (`ItineraryID`) REFERENCES `itinerary` (`id`),
  CONSTRAINT `PlaneID` FOREIGN KEY (`PlaneID`) REFERENCES `planes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert airline.flight (ItineraryID, PlaneID)
values
(7,1),
(6,2),
(5,3),
(4,4),
(3,5),
(2,6),
(1,7);

USE airline;

CREATE TABLE `crew` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FlightID` int(11) NOT NULL,
  `StaffID` int(11) NOT NULL,
  UNIQUE KEY `ID_UNIQUE` (`id`),
  KEY `StaffID_idx` (`StaffID`),
  KEY `FlightID_idx` (`FlightID`),
  CONSTRAINT `FlightID` FOREIGN KEY (`FlightID`) REFERENCES `flight` (`id`),
  CONSTRAINT `StaffID` FOREIGN KEY (`StaffID`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




