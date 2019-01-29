DROP TABLE IF EXISTS Notes;
DROP TABLE IF EXISTS Users;

CREATE TABLE users(
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
	content NVARCHAR(255) NOT NULL,
	creationDate DATETIME NOT NULL,
	published BIT DEFAULT 0,
	FOREIGN KEY(userID) REFERENCES Users(ID),
	PRIMARY KEY(ID)
);

INSERT INTO users(name,surname,login,password,email,birthDate) VALUES ('Jan', 'Kowalski', 'jan123','$2a$10$FabGp5zLvInm/DAcHtmRc.ws1.tQK7eXamK/mCj/BPCSlB5yDyNH2','jan123@test.com','2012-12-11');
INSERT INTO users(name,surname,login,password,email,birthDate) VALUES ('Paweł', 'Adamowski', 'pawel123','$2a$10$FabGp5zLvInm/DAcHtmRc.ws1.tQK7eXamK/mCj/BPCSlB5yDyNH2','pawel123@test.com','2011-11-01');

INSERT INTO notes(userID, name, content, creationDate) VALUES (1,'Test 1','Testing notes', '2019-01-05 12:12:12');
INSERT INTO notes(userID, name, content, creationDate, published) VALUES (2,'Test 2','Lorem ipsum', '2019-01-03 10:10:10', 1);