
-- Relational Schema for College Event Website (CEW)

CREATE TABLE University (
    universityID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    numStudents INT
);

CREATE TABLE User (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    dob DATE,
    password VARCHAR(255),
    role ENUM('student', 'admin', 'super') NOT NULL,
    universityID INT NOT NULL,
    FOREIGN KEY (universityID) REFERENCES University(universityID)
);

CREATE TABLE RSO (
    rsoID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    approved BOOLEAN DEFAULT FALSE,
    universityID INT NOT NULL,
    adminID INT,
    FOREIGN KEY (universityID) REFERENCES University(universityID),
    FOREIGN KEY (adminID) REFERENCES User(userID)
);

CREATE TABLE StudentRSO (
    userID INT,
    rsoID INT,
    PRIMARY KEY (userID, rsoID),
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (rsoID) REFERENCES RSO(rsoID)
);

CREATE TABLE Location (
    locationID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6)
);

CREATE TABLE Event (
    eventID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    date DATE,
    contactEmail VARCHAR(255),
    contactPhone VARCHAR(50),
    type VARCHAR(100),
    visibility ENUM('public', 'private', 'rso') NOT NULL,
    approved BOOLEAN DEFAULT FALSE,
    locationID INT,
    created_by INT,
    approved_by INT,
    rsoID INT,
    FOREIGN KEY (locationID) REFERENCES Location(locationID),
    FOREIGN KEY (created_by) REFERENCES User(userID),
    FOREIGN KEY (approved_by) REFERENCES User(userID),
    FOREIGN KEY (rsoID) REFERENCES RSO(rsoID)
);

CREATE TABLE Comment (
    commentID INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    userID INT NOT NULL,
    eventID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

CREATE TABLE Rating (
    ratingID INT AUTO_INCREMENT PRIMARY KEY,
    stars INT CHECK (stars >= 1 AND stars <= 5),
    userID INT NOT NULL,
    eventID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (eventID) REFERENCES Event(eventID)
);

-- CREATE ASSERTION (simulated using trigger since MySQL doesn't support CREATE ASSERTION)
-- Ensure at least 5 members must exist in an RSO to be approved
DELIMITER //
CREATE TRIGGER check_rso_members
BEFORE UPDATE ON RSO
FOR EACH ROW
BEGIN
    DECLARE memberCount INT;
    SELECT COUNT(*) INTO memberCount FROM StudentRSO WHERE rsoID = NEW.rsoID;
    IF NEW.approved = TRUE AND memberCount < 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'RSO must have at least 5 members to be approved.';
    END IF;
END;
//
DELIMITER ;
