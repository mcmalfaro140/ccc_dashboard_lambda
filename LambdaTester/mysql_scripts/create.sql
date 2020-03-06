DROP DATABASE IF EXISTS db;
CREATE DATABASE db;
USE db;

DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS SNSTopics CASCADE;
DROP TABLE IF EXISTS Triggers CASCADE;
DROP TABLE IF EXISTS SRefSNSTopicTrigger CASCADE;

CREATE TABLE Users (
	UserId INT NOT NULL AUTO_INCREMENT,
	Username TEXT NOT NULL,
    Password TEXT NOT NULL,
	Email TEXT NOT NULL,
	Dashboard LONGTEXT NOT NULL,
	PRIMARY KEY (UserId)
);

CREATE TABLE SNSTopics (
	SNSTopicId INT NOT NULL AUTO_INCREMENT,
	UserId INT NOT NULL,
	TopicArn TEXT NOT NULL,
	PRIMARY KEY (SNSTopicId),
	FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

CREATE TABLE Triggers (
	TriggerId INT NOT NULL AUTO_INCREMENT,
	SubscriptionName TEXT NOT NULL,
	PRIMARY KEY (TriggerId)
);

CREATE TABLE XRefSNSTopicTrigger (
	SNSTopicTriggerId INT NOT NULL AUTO_INCREMENT,
	SNSTopicId INT NOT NULL,
	TriggerId INT NOT NULL,
	PRIMARY KEY (SNSTopicTriggerId),
	FOREIGN KEY (SNSTopicId) REFERENCES SNSTopics(SNSTopicId),
	FOREIGN KEY (TriggerId) REFERENCES Triggers(TriggerId)
);

INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('AlexHorejsi', 'test1', 'alex.horejsi59@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('MisaelCorvera', 'test2', 'mcmalfaro140@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('YiWang', 'test3', 'superhotdogzz@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('ZacYou', 'test4', 'zacyou151@yahoo.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('JayDida', 'test5', 'didajateni@gmail.com', '{}');

INSERT INTO SNSTopics (UserId, TopicArn) VALUES (1, 'arn:aws:sns:us-east-1:112911356528:EmailTopic');
INSERT INTO SNSTopics (UserId, TopicArn) VALUES (2, 'arn:aws:sns:us-east-1:112911356528:PhoneTopic');
INSERT INTO SNSTopics (UserId, TopicArn) VALUES (3, 'arn:aws:sns:us-east-1:112911356528:DualTopic');

INSERT INTO Triggers (SubscriptionName) VALUES ('trigger');
INSERT INTO Triggers (SubscriptionName) VALUES ('trigger2');
INSERT INTO Triggers (SubscriptionName) VALUES ('trigger3');

INSERT INTO XRefSNSTopicTrigger (SNSTopicId, TriggerId) VALUES (1, 2);
INSERT INTO XRefSNSTopicTrigger (SNSTopicId, TriggerId) VALUES (2, 1);
INSERT INTO XRefSNSTopicTrigger (SNSTopicId, TriggerId) VALUES (3, 3);
