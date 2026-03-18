--liquibase formatted sql

--changeset finda-notification:004-change-adminid-to-userid-uuid
ALTER TABLE tbl_notice
    MODIFY COLUMN admin_id BINARY(16) NOT NULL;

ALTER TABLE tbl_notice
    CHANGE COLUMN admin_id user_id BINARY(16) NOT NULL;
