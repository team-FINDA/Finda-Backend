--liquibase formatted sql

--changeset finda-notification:003-add-status-column
ALTER TABLE tbl_notice
    MODIFY COLUMN status ENUM('SENT','RECEIVED') NOT NULL DEFAULT 'RECEIVED';
