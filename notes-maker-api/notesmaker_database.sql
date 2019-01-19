DROP DATABASE IF  EXISTS notesmaker;
CREATE DATABASE notesmaker;

USE notesmaker;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';
ALTER DATABASE notesmaker CHARACTER SET utf8 COLLATE utf8_general_ci;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';

DROP TABLE IF EXISTS Notes;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users(
	ID INT NOT NULL AUTO_INCREMENT,
	name NVARCHAR(40) NOT NULL,
	surname NVARCHAR(40) NOT NULL,
	login NVARCHAR(40) NOT NULL,
	password  NVARCHAR(100) NOT NULL,
	email NVARCHAR(100) NOT NULL,
	birthDate DATE NOT NULL,
	PRIMARY KEY(ID),
	UNIQUE(login),
	UNIQUE(email)
);

CREATE TABLE Notes(
	ID INT NOT NULL AUTO_INCREMENT,
	userID INT NOT NULL,
	name NVARCHAR(100) NOT NULL,
	content NVARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
	creationDate DATETIME NOT NULL,
	FOREIGN KEY(userID) REFERENCES Users(ID),
	PRIMARY KEY(ID)
);