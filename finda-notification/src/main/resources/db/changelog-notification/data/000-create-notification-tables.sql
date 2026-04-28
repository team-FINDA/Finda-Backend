--liquibase formatted sql

--changeset finda-notification:000-create-notification-tables
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'tbl_notice' AND table_schema = DATABASE()
CREATE TABLE tbl_notice
(
    id           BINARY(16)   NOT NULL,
    title        VARCHAR(255) NOT NULL,
    body         TEXT         NOT NULL,
    user_id      BINARY(16)   NOT NULL,
    status       VARCHAR(50)  NOT NULL,
    notice_date  DATE         NOT NULL,
    notice_time  TIME         NOT NULL,
    created_at   DATETIME(6)  NOT NULL,
    modified_at  DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_notice_user FOREIGN KEY (user_id)
        REFERENCES tbl_user (id)
) ENGINE = InnoDB;

CREATE TABLE tbl_notification
(
    id            BINARY(16)   NOT NULL,
    title         VARCHAR(255) NOT NULL,
    body          TEXT         NOT NULL,
    type          VARCHAR(50)  NOT NULL,
    volunteer_id  BINARY(16)   NULL,
    created_at    DATETIME(6)  NOT NULL,
    modified_at   DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_notification_volunteer FOREIGN KEY (volunteer_id)
        REFERENCES tbl_volunteer (id)
) ENGINE = InnoDB;

CREATE TABLE tbl_notification_preference
(
    id          BINARY(16)   NOT NULL,
    type        VARCHAR(50)  NOT NULL,
    user_id     BINARY(16)   NOT NULL,
    enabled     BIT(1)       NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    modified_at DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_notification_pref_user FOREIGN KEY (user_id)
        REFERENCES tbl_user (id)
) ENGINE = InnoDB;

CREATE TABLE tbl_volunteer_notification_preference
(
    id            BINARY(16)  NOT NULL,
    volunteer_id  BINARY(16)  NOT NULL,
    user_id       BINARY(16)  NOT NULL,
    enabled       BIT(1)      NOT NULL,
    created_at    DATETIME(6) NOT NULL,
    modified_at   DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_volunteer_user UNIQUE (volunteer_id, user_id),
    CONSTRAINT fk_volunteer_pref_volunteer FOREIGN KEY (volunteer_id)
        REFERENCES tbl_volunteer (id),
    CONSTRAINT fk_volunteer_pref_user FOREIGN KEY (user_id)
        REFERENCES tbl_user (id)
) ENGINE = InnoDB;
