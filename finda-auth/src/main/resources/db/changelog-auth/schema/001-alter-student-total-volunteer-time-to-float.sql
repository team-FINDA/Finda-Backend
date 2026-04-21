--liquibase formatted sql

--changeset byeondohwi:001-alter-student-total-volunteer-time-to-float
ALTER TABLE tbl_student
    MODIFY COLUMN total_volunteer_time FLOAT NOT NULL DEFAULT 0;
