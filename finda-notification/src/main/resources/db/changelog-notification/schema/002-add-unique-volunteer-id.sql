--liquibase formatted sql

--changeset finda-notification:002-add-unique-volunteer-id
ALTER TABLE tbl_volunteer_notification_preference
ADD CONSTRAINT uq_volunteer_id UNIQUE (volunteer_id);
