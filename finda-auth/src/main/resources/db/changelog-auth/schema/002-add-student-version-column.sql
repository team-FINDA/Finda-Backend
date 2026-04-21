--liquibase formatted sql

--changeset byeondohwi:002-add-student-version-column
ALTER TABLE tbl_student
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
