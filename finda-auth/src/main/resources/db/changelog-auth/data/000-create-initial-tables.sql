--liquibase formatted sql

--changeset finda-auth:000-create-auth-tables

--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult 0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'tbl_user' AND table_schema = DATABASE()

CREATE TABLE tbl_user
(
    id           BINARY(16)   NOT NULL,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    authority    VARCHAR(50)  NOT NULL,
    deleted_at   DATETIME(6)  NULL,
    created_at   DATETIME(6)  NOT NULL,
    modified_at  DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_email (email)
) ENGINE = InnoDB;

CREATE TABLE tbl_student
(
    id                     BINARY(16)  NOT NULL,
    user_id                BINARY(16)  NOT NULL,
    grade                  INT         NOT NULL,
    class_num              INT         NOT NULL,
    num                    INT         NOT NULL,
    total_volunteer_time   FLOAT       NOT NULL DEFAULT 0,
    created_at             DATETIME(6) NOT NULL,
    modified_at            DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_student_number UNIQUE (grade, class_num, num),
    CONSTRAINT uk_student_user UNIQUE (user_id),
    CONSTRAINT fk_student_user FOREIGN KEY (user_id)
        REFERENCES tbl_user (id)
) ENGINE = InnoDB;

CREATE TABLE tbl_teacher
(
    id           BINARY(16)  NOT NULL,
    user_id      BINARY(16)  NOT NULL,
    created_at   DATETIME(6) NOT NULL,
    modified_at  DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_teacher_user UNIQUE (user_id),
    CONSTRAINT fk_teacher_user FOREIGN KEY (user_id)
        REFERENCES tbl_user (id)
) ENGINE = InnoDB;

CREATE TABLE tbl_device_token
(
    id            BINARY(16)   NOT NULL,
    user_id       BINARY(16)   NOT NULL,
    device_token  VARCHAR(255) NOT NULL,
    os            VARCHAR(50)  NOT NULL,
    created_at    DATETIME(6)  NOT NULL,
    modified_at   DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_device_token_user FOREIGN KEY (user_id)
        REFERENCES tbl_user (id),
    CONSTRAINT uk_device_token UNIQUE (device_token)
) ENGINE = InnoDB;
