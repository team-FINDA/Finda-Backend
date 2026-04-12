--liquibase formatted sql

--changeset byeondohwi:002-add-user-id-to-user-activity
ALTER TABLE tbl_user_activity
    ADD COLUMN user_id BINARY(16) NOT NULL;
