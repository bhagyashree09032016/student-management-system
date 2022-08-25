DROP TABLE Student;
 CREATE TABLE IF NOT EXISTS Student(
    studentid int primary key,
    firstname varchar(255),
    lastname varchar(255),
    email varchar(255)
);