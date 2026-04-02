--liquibase formatted sql

--changeset byeondohwi:003-add-unique-teacher-participation-user-id
ALTER TABLE tbl_teacher_participation
    ADD CONSTRAINT uk_teacher_participation_user_id UNIQUE (user_id);
