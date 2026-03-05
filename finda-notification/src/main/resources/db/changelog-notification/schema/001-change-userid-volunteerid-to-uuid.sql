--liquibase formatted sql

--changeset finda-notification:001-change-userid-volunteerid-to-uuid
ALTER TABLE tbl_volunteer_notification_preference
MODIFY COLUMN id BINARY(16) NOT NULL;

ALTER TABLE tbl_volunteer_notification_preference
MODIFY COLUMN volunteer_id BINARY(16) NOT NULL;

ALTER TABLE tbl_volunteer_notification_preference
MODIFY COLUMN user_id BINARY(16) NOT NULL;

ALTER TABLE tbl_notification_preference
MODIFY COLUMN user_id BINARY(16) NOT NULL;
