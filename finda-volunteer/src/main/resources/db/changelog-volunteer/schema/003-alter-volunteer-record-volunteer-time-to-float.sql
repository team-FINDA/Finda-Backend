--liquibase formatted sql

--changeset byeondohwi:003-alter-volunteer-record-volunteer-time-to-float
ALTER TABLE tbl_volunteer_record
    MODIFY COLUMN volunteer_time FLOAT NOT NULL;
