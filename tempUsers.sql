CREATE DATABASE colleges;
CREATE DATABASE college;
DROP DATABASE colleges;
CREATE TABLE college.student;
CREATE TABLE college.students;
DROP TABLE college.students;
INSERT "John Smith" INTO college.students;
INSERT "Jane Doe" INTO college.students;
INSERT "Justa Beeper" INTO college.students;
INSERT "DOPE" INTO college.students;
INSERT "PEACE OUT" INTO college.students;
SELECT * FROM college.students WHERE COLUMN = "John Smith";
DELETE FROM college.students;
DELETE FROM college.students WHERE COLUMN = "Justa Beeper";
SELECT * FROM college.students;