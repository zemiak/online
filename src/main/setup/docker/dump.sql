CREATE SEQUENCE PUBLIC.HIBERNATE_SEQUENCE START WITH 42000000;

CREATE TABLE credentials (
    id bigint NOT NULL,
    created timestamp,
    name character varying(255),
    password character varying(255)
);
CREATE TABLE outage (
    id bigint NOT NULL,
    end_date timestamp,
    start_date timestamp,
    system_id bigint
);
CREATE TABLE protectedsystem (
    id bigint NOT NULL,
    created timestamp NOT NULL,
    name character varying(255) NOT NULL,
    lastseen timestamp NOT NULL,
    disabled boolean NOT NULL
);
CREATE TABLE roles (
    role_name character varying(255) NOT NULL,
    user_name character varying(255) NOT NULL
);

INSERT INTO credentials VALUES (1, '2014-08-30 11:30:57.374241', 'vasko', 'Jif60Je1RfBu4r50d2ING/J9KS6R/4Wn5bEDJvxCinw=');
INSERT INTO outage VALUES (2000, '2015-05-16 06:40:08.367', '2015-05-10 23:00:07.689', 2);
INSERT INTO protectedsystem VALUES (2, '2014-09-18 21:39:44.305', 'lenovo-server', '2015-05-24 18:10:01', false);
INSERT INTO roles VALUES ('USER', 'vasko');
INSERT INTO roles VALUES ('ADMINISTRATOR', 'vasko');

ALTER TABLE credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);
ALTER TABLE outage
    ADD CONSTRAINT outage_pkey PRIMARY KEY (id);
ALTER TABLE protectedsystem
    ADD CONSTRAINT protectedsystem_pkey PRIMARY KEY (id);
ALTER TABLE roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_name, user_name);
ALTER TABLE credentials
    ADD CONSTRAINT unq_credentials_0 UNIQUE (name);
ALTER TABLE protectedsystem
    ADD CONSTRAINT unq_protectedsystem_0 UNIQUE (name);
ALTER TABLE outage
    ADD CONSTRAINT fk_outage_system_id FOREIGN KEY (system_id) REFERENCES protectedsystem(id);
