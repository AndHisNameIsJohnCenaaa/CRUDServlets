DROP TABLE IF EXISTS employee_task;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS employee;
DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE company
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL
);
CREATE UNIQUE INDEX company_unique_email_idx ON company (email);

CREATE TABLE employee
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    telegram_id      VARCHAR                           NOT NULL
);
CREATE UNIQUE INDEX employee_unique_telegram_id_idx ON employee (telegram_id);

CREATE TABLE task
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    company_id       INTEGER,
    deadline         DATE                              NOT NULL,
    description      TEXT                              NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company (id) ON DELETE CASCADE
);

CREATE TABLE employee_task
(
    task_id          INTEGER    NOT NULL    REFERENCES task (id) ON DELETE CASCADE,
    employee_id      INTEGER    NOT NULL    REFERENCES employee (id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE
);
