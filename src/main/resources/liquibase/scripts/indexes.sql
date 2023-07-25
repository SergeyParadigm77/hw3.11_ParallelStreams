--liquibase formatted sql


--changeset gappasov:1
CREATE INDEX students_name ON student (name);

--changeset gappasov:2
CREATE INDEX faculties_name_color ON faculty (name, color);