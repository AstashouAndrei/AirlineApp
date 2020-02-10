CREATE SCHEMA `airline` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

USE airline;
CREATE TABLE `access` (
  `id` int(11) NOT NULL,
  `Access` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
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
  `Login` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `AccessID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `Login_UNIQUE` (`Login`),
  KEY `AccessID_idx` (`AccessID`),
  CONSTRAINT `AccessID` FOREIGN KEY (`AccessID`) REFERENCES `access` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

	INSERT airline.user(Login, Password, Email, AccessID) 
VALUES
('Somerset', 'Aset', 'somer@gmail.com', 1),
('Wilson', 'w1on', 'netrick@google.com', 2),
('Андрей', 'ан87', 'onaps@rambler.ru', 1),
('Наталья', 'nata', 'natka@rmailr.ru', 2);



USE airline;
CREATE TABLE `professions` (
  `id` int(11) NOT NULL,
  `Profession` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
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
  `FirstName` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ProfessionID` int(11) NOT NULL,
  `CurrentState` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Standby',
  UNIQUE KEY `idnew_table_UNIQUE` (`id`),
  KEY `ProfessionID` (`ProfessionID`),
  CONSTRAINT `ProfessionID` FOREIGN KEY (`ProfessionID`) REFERENCES `professions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
 
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
  `Model` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `PassengerCapacity` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `FlightRange` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `FuelConsumption` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  `FlightCode` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Departure` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Arrival` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `FlightCode_UNIQUE` (`FlightCode`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  `ItineraryID` int(11) DEFAULT NULL,
  `PlaneID` int(11) DEFAULT NULL,
  `CurrentState` varchar(45) NOT NULL DEFAULT 'Standby',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `itin_idx` (`ItineraryID`),
  KEY `plan_idx` (`PlaneID`),
  CONSTRAINT `itin` FOREIGN KEY (`ItineraryID`) REFERENCES `itinerary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `plan` FOREIGN KEY (`PlaneID`) REFERENCES `planes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

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
  KEY `flightID_idx` (`FlightID`),
  KEY `staffID_idx` (`StaffID`),
  CONSTRAINT `flightID` FOREIGN KEY (`FlightID`) REFERENCES `flight` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `staffID` FOREIGN KEY (`StaffID`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




