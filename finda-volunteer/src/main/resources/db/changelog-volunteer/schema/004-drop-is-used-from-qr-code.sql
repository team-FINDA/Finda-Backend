--liquibase formatted sql

--changeset finda:004-drop-is-used-from-qr-code
ALTER TABLE tbl_qr_code DROP COLUMN is_used;
