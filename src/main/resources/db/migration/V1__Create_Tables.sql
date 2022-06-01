CREATE DATABASE IF NOT EXISTS tenselight CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

USE tenselight;

CREATE TABLE IF NOT EXISTS users (
  id int NOT NULL AUTO_INCREMENT,
  firstname VARCHAR(255),
  lastname VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  type TINYINT NOT NULL DEFAULT 1,
  isactive TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS projects (
  id int NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  startdate DATE,
  enddate DATE,
  isbillable TINYINT NOT NULL DEFAULT 1,
  isactive TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS trackedtime (
  id int NOT NULL AUTO_INCREMENT,
  userid int NOT NULL,
  projectid int NOT NULL,
  hours float NOT NULL,
  description text NOT NULL,
  isactive TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (userid) REFERENCES users (id),
  FOREIGN KEY (projectid) REFERENCES projects (id)
);

CREATE TABLE IF NOT EXISTS assignments (
  id int NOT NULL AUTO_INCREMENT,
  userid int NOT NULL,
  projectid int NOT NULL,
  isactive TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (userid) REFERENCES users (id),
  FOREIGN KEY (projectid) REFERENCES projects (id)
);
