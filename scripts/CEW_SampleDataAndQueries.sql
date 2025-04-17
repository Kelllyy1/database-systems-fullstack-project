
-- ============================================
-- Sample Data Insertions with Constraints
-- ============================================

-- Insert Universities
INSERT INTO university (universityID, name, location, numStudents) VALUES 
(1, 'Florida Tech University', 'Orlando, FL', 10000),
(2, 'State College of Innovation', 'Miami, FL', 8000);

-- Insert Students (Users with role 'student')
INSERT INTO user (userID, first_name, last_name, email, phone_number, dob, password, role, universityID) VALUES
(1, 'Alice', 'Smith', 'alice1@ftu.edu', '1234567890', '2000-01-01', 'pass123', 'student', 1),
(2, 'Bob', 'Johnson', 'bob2@ftu.edu', '1234567891', '2000-02-02', 'pass123', 'student', 1),
(3, 'Carol', 'Lee', 'carol3@ftu.edu', '1234567892', '2000-03-03', 'pass123', 'student', 1),
(4, 'Dave', 'Brown', 'dave4@ftu.edu', '1234567893', '2000-04-04', 'pass123', 'student', 1),
(5, 'Eve', 'Davis', 'eve5@ftu.edu', '1234567894', '2000-05-05', 'pass123', 'student', 1),
(6, 'Frank', 'White', 'frank6@sci.edu', '1234567895', '2000-06-06', 'pass123', 'student', 2),
(7, 'Grace', 'Hall', 'grace7@sci.edu', '1234567896', '2000-07-07', 'pass123', 'student', 2),
(8, 'Heidi', 'Allen', 'heidi8@sci.edu', '1234567897', '2000-08-08', 'pass123', 'student', 2),
(9, 'Ivan', 'Young', 'ivan9@sci.edu', '1234567898', '2000-09-09', 'pass123', 'student', 2),
(10, 'Judy', 'King', 'judy10@sci.edu', '1234567899', '2000-10-10', 'pass123', 'student', 2);

-- Insert Admins
INSERT INTO user (userID, first_name, last_name, email, phone_number, dob, password, role, universityID) VALUES
(11, 'Admin', 'One', 'admin1@ftu.edu', '9999990001', '1990-01-01', 'admin123', 'admin', 1),
(12, 'Admin', 'Two', 'admin2@sci.edu', '9999990002', '1990-01-02', 'admin123', 'admin', 2);

-- Insert Supers
INSERT INTO user (userID, first_name, last_name, email, phone_number, dob, password, role, universityID) VALUES
(13, 'Super', 'One', 'super1@ftu.edu', '9999990003', '1990-01-01', 'super123', 'super', 1),
(14, 'Super', 'Two', 'super2@sci.edu', '9999990004', '1990-01-02', 'super123', 'super', 2);

-- Insert RSOs
INSERT INTO rso (rsoID, name, approved, universityID, adminID) VALUES
(1, 'CyberSec Club', TRUE, 1, 11),
(2, 'AI Society', TRUE, 1, 11),
(3, 'Eco Group', TRUE, 2, 12);

-- Insert StudentRSO memberships
INSERT INTO studentrso (userID, rsoID) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
(6, 3), (7, 3), (8, 3), (9, 3), (10, 3);

-- Insert Locations
INSERT INTO location (locationID, name, latitude, longitude) VALUES
(1, 'Main Hall', 28.602, -81.200),
(2, 'Tech Center', 28.601, -81.210);

-- Insert Events
INSERT INTO event (eventID, name, description, date, contactEmail, contactPhone, type, visibility, approved, locationID, created_by, approved_by, rsoID) VALUES
(1, 'Cyber Night', 'Ethical hacking session', '2025-04-10', 'admin1@ftu.edu', '1111111111', 'Tech', 'rso', TRUE, 1, 11, 11, 1),
(2, 'AI Summit', 'Talks on AI', '2025-04-11', 'admin1@ftu.edu', '1111111112', 'Tech', 'public', FALSE, 2, 11, 11, NULL),
(3, 'Tree Planting', 'Eco event on campus', '2025-04-12', 'admin2@sci.edu', '1111111113', 'Eco', 'private', TRUE, 1, 12, 12, NULL),
(4, 'Intro to Robotics', 'Demo and workshop', '2025-04-15', 'admin1@ftu.edu', '1111111114', 'Tech', 'rso', TRUE, 2, 11, 11, 2),
(5, 'Recycling Drive', 'Promoting recycling', '2025-04-16', 'admin2@sci.edu', '1111111115', 'Eco', 'public', FALSE, 1, 12, 12, NULL);

-- Insert Comments
INSERT INTO comment (commentID, content, rating, userID, eventID) VALUES
(1, 'Awesome event!', 5, 1, 1),
(2, 'Very informative.', 4, 2, 1),
(3, 'Loved the vibe.', 5, 6, 3);

-- ============================================
-- Sample Queries
-- ============================================

-- Insert new RSO
-- (ensure minimum 5 students are added after this)
-- Results should show the inserted row
-- SELECT * FROM rso WHERE name = 'New RSO';

-- Join a student to an RSO
-- INSERT INTO studentrso (userID, rsoID) VALUES (3, 2);

-- Create a new event (RSO event)
-- INSERT INTO event (...) VALUES (...) returning the inserted ID

-- Update a comment
-- UPDATE comment SET content = 'Updated feedback', rating = 4 WHERE commentID = 1;

-- View public events
-- SELECT * FROM event WHERE visibility = 'public' AND approved = TRUE;

-- View private events by university
-- SELECT * FROM event WHERE visibility = 'private' AND approved = TRUE AND created_by IN (SELECT userID FROM user WHERE universityID = 1);

-- View RSO events where user is a member
-- SELECT * FROM event WHERE visibility = 'rso' AND rsoID IN (SELECT rsoID FROM studentrso WHERE userID = 1);
