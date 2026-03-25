--liquibase formatted sql

--changeset finda-volunteer:001-create-volunteer-tables
CREATE TABLE tbl_volunteer
(
    id                     BINARY(16)   NOT NULL,
    status                 ENUM('APPLICATION', 'WORK', 'DONE') NOT NULL,
    personnel              INT          NOT NULL,
    title                  VARCHAR(255) NOT NULL,
    description            TEXT         NOT NULL,
    unit_volunteer_hours   FLOAT        NOT NULL,
    application_start_date DATE         NOT NULL,
    application_end_date   DATE         NOT NULL,
    work_start_date        DATE         NOT NULL,
    work_end_date          DATE         NOT NULL,
    cycle_type             ENUM('WEEK', 'MONTH', 'NONE') NULL,
    user_id                BINARY(16)   NOT NULL,
    remind_time            TIME         NOT NULL,
    group_volunteer_type   ENUM('CURRICULAR', 'EXTRACURRICULAR') NOT NULL,
    volunteer_type         ENUM('NEIGHBOR_SUPPORT', 'ENVIRONMENT', 'CAMPAIGN', 'OTHER') NOT NULL,
    created_at             DATETIME(6)  NOT NULL,
    modified_at            DATETIME(6)  NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE tbl_activity
(
    id            BINARY(16)   NOT NULL,
    activity_name VARCHAR(255) NOT NULL,
    volunteer_id  BINARY(16)   NULL,
    created_at    DATETIME(6)  NOT NULL,
    modified_at   DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_activity_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE tbl_volunteer_schedule
(
    id            BINARY(16)  NOT NULL,
    schedule_date DATE        NOT NULL,
    volunteer_id  BINARY(16)  NULL,
    created_at    DATETIME(6) NOT NULL,
    modified_at   DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_volunteer_schedule_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE tbl_volunteer_record
(
    id             BINARY(16)   NOT NULL,
    user_id        BINARY(16)   NOT NULL,
    volunteer_time INT          NOT NULL,
    title          VARCHAR(255) NOT NULL,
    volunteer_id   BINARY(16)   NULL,
    created_at     DATETIME(6)  NOT NULL,
    modified_at    DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_volunteer_record_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE tbl_student_particitation
(
    id             BINARY(16)   NOT NULL,
    volunteer_id   BINARY(16)   NULL,
    status         ENUM('APPLIED', 'REJECTED', 'PARTICIPATED') NOT NULL,
    participated_at DATETIME(6) NOT NULL,
    user_id        BINARY(16)   NOT NULL,
    created_at     DATETIME(6)  NOT NULL,
    modified_at    DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_student_particitation_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE tbl_teacher_participation
(
    id           BINARY(16)  NOT NULL,
    user_id      BINARY(16)  NOT NULL,
    volunteer_id BINARY(16)  NULL,
    created_at   DATETIME(6) NOT NULL,
    modified_at  DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_teacher_participation_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE tbl_qr_code
(
    id           BINARY(16)   NOT NULL,
    volunteer_id BINARY(16)   NULL,
    code         VARCHAR(255) NOT NULL,
    generated_at DATETIME(6)  NOT NULL,
    is_used      BIT(1)       NOT NULL,
    used_at      DATETIME(6)  NULL,
    student_id   BINARY(16)   NOT NULL,
    teacher_id   BINARY(16)   NOT NULL,
    created_at   DATETIME(6)  NOT NULL,
    modified_at  DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_qr_code_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE activity_recurrence_month
(
    id           BINARY(16)  NOT NULL,
    volunteer_id BINARY(16)  NULL,
    day          INT         NOT NULL,
    created_at   DATETIME(6) NOT NULL,
    modified_at  DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_activity_recurrence_month_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE activity_recurrence_week
(
    id           BINARY(16)  NOT NULL,
    weekday      ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    volunteer_id BINARY(16)  NULL,
    created_at   DATETIME(6) NOT NULL,
    modified_at  DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_activity_recurrence_week_volunteer
        FOREIGN KEY (volunteer_id) REFERENCES tbl_volunteer (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE tbl_user_activity
(
    id          BINARY(16)  NOT NULL,
    activity_id BINARY(16)  NULL,
    created_at  DATETIME(6) NOT NULL,
    modified_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_activity_activity
        FOREIGN KEY (activity_id) REFERENCES tbl_activity (id)
) ENGINE = InnoDB;

CREATE INDEX idx_activity_volunteer_id ON tbl_activity (volunteer_id);
CREATE INDEX idx_volunteer_schedule_volunteer_id ON tbl_volunteer_schedule (volunteer_id);
CREATE INDEX idx_volunteer_record_volunteer_id ON tbl_volunteer_record (volunteer_id);
CREATE INDEX idx_student_particitation_volunteer_id ON tbl_student_particitation (volunteer_id);
CREATE INDEX idx_teacher_participation_volunteer_id ON tbl_teacher_participation (volunteer_id);
CREATE INDEX idx_qr_code_volunteer_id ON tbl_qr_code (volunteer_id);
CREATE INDEX idx_activity_recurrence_month_volunteer_id ON activity_recurrence_month (volunteer_id);
CREATE INDEX idx_activity_recurrence_week_volunteer_id ON activity_recurrence_week (volunteer_id);
CREATE INDEX idx_user_activity_activity_id ON tbl_user_activity (activity_id);
