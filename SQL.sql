CREATE TABLE Staff (
    SID serial PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

CREATE TABLE Equipment (
    EID serial PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Available BOOLEAN NOT NULL
);

CREATE TABLE EquipmentMaintain (
    MID serial PRIMARY KEY,
    EID INT NOT NULL,
    SID INT NOT NULL,
    MaintainDate DATE NOT NULL,
    FOREIGN KEY (EID) REFERENCES Equipment(EID),
    FOREIGN KEY (SID) REFERENCES Staff(SID)
);

CREATE TABLE Member (
    MID serial PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
	LoyaltyPoint INT NOT NULL,
    FitnessGoals TEXT,
    HealthMetrics TEXT
);

CREATE TABLE Coach (
    CID serial PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

CREATE TABLE GroupClass (
    ClassID serial PRIMARY KEY,
    ClassName VARCHAR(255) NOT NULL,
	CID INT NOT NULL,
    Place VARCHAR(255) NOT NULL,
	StartDate DATE NOT NULL,
	FOREIGN KEY (CID) REFERENCES Coach(CID)
);

CREATE TABLE CoachSchedules (
    SchedulesID serial PRIMARY KEY,
    CID INT NOT NULL,
    AvailableTime DATE NOT NULL,
    FOREIGN KEY (CID) REFERENCES Coach(CID)
);

CREATE TABLE EquipmentRental (
    RentalID serial PRIMARY KEY,
    EID INT NOT NULL,
    MID INT NOT NULL,
    RentalTime TIMESTAMP NOT NULL,
    FOREIGN KEY (EID) REFERENCES Equipment(EID),
    FOREIGN KEY (MID) REFERENCES Member(MID)
);

CREATE TABLE PersonalTrain (
    PTID serial PRIMARY KEY,
    MID INT NOT NULL,
    CID INT NOT NULL,
    TrainTime TIMESTAMP NOT NULL,
    FOREIGN KEY (MID) REFERENCES Member(MID),
    FOREIGN KEY (CID) REFERENCES Coach(CID)
);

CREATE TABLE MemberJoinGroupClass (
    MJGCID serial PRIMARY KEY,
    MID INT NOT NULL,
    ClassID INT NOT NULL,
    FOREIGN KEY (MID) REFERENCES Member(MID),
    FOREIGN KEY (ClassID) REFERENCES GroupClass(ClassID)
);

INSERT INTO Staff (Name) VALUES ('Alpha Albion');
INSERT INTO Staff (Name) VALUES ('Beta Buck');

INSERT INTO Member (Name, LoyaltyPoint, FitnessGoals, HealthMetrics) VALUES ('Charlie Chong', 100, 'Lose weight', 'BMI 22');
INSERT INTO Member (Name, LoyaltyPoint) VALUES ('Delta Dice', 50);
INSERT INTO Member (Name, LoyaltyPoint, HealthMetrics) VALUES ('Echo Evergarden', 220, 'BMI 21');

INSERT INTO Coach (Name) VALUES ('Foxtrot Fort');
INSERT INTO Coach (Name) VALUES ('Golf Guy');

INSERT INTO Equipment (Name, Available) VALUES ('Treadmill', TRUE);
INSERT INTO Equipment (Name, Available) VALUES ('Dumbbell_10KG', TRUE);
INSERT INTO Equipment (Name, Available) VALUES ('Dumbbell_20KG', TRUE);
INSERT INTO Equipment (Name, Available) VALUES ('Bench rack', TRUE);

INSERT INTO CoachSchedules (CID, AvailableTime) VALUES (1, '2023-12-01');
INSERT INTO CoachSchedules (CID, AvailableTime) VALUES (1, '2023-12-04');
INSERT INTO CoachSchedules (CID, AvailableTime) VALUES (1, '2023-12-05');
INSERT INTO CoachSchedules (CID, AvailableTime) VALUES (2, '2023-12-02');
INSERT INTO CoachSchedules (CID, AvailableTime) VALUES (2, '2023-12-03');