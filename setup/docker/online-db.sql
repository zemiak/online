--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: credentials; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE credentials (
    id bigint NOT NULL,
    created timestamp without time zone,
    name character varying(255),
    password character varying(255)
);


ALTER TABLE public.credentials OWNER TO postgres;

--
-- Name: entity_id_seq_outage; Type: SEQUENCE; Schema: public; Owner: online_user
--

CREATE SEQUENCE entity_id_seq_outage
    START WITH 2000
    INCREMENT BY 1
    MINVALUE 2000
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.entity_id_seq_outage OWNER TO online_user;

--
-- Name: entity_id_seq_system; Type: SEQUENCE; Schema: public; Owner: online_user
--

CREATE SEQUENCE entity_id_seq_system
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.entity_id_seq_system OWNER TO online_user;

--
-- Name: outage; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outage (
    id bigint NOT NULL,
    end_date timestamp without time zone,
    start_date timestamp without time zone,
    system_id bigint
);


ALTER TABLE public.outage OWNER TO postgres;

--
-- Name: protectedsystem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE protectedsystem (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    name character varying(255) NOT NULL,
    lastseen timestamp without time zone NOT NULL,
    disabled boolean NOT NULL
);


ALTER TABLE public.protectedsystem OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE roles (
    role_name character varying(255) NOT NULL,
    user_name character varying(255) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: sequence; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


ALTER TABLE public.sequence OWNER TO postgres;

--
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO credentials VALUES (1, '2014-08-30 11:30:57.374241', 'vasko', 'Jif60Je1RfBu4r50d2ING/J9KS6R/4Wn5bEDJvxCinw=');


--
-- Name: entity_id_seq_outage; Type: SEQUENCE SET; Schema: public; Owner: online_user
--

SELECT pg_catalog.setval('entity_id_seq_outage', 2000, true);


--
-- Name: entity_id_seq_system; Type: SEQUENCE SET; Schema: public; Owner: online_user
--

SELECT pg_catalog.setval('entity_id_seq_system', 1000, false);


--
-- Data for Name: outage; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO outage VALUES (2000, '2015-05-16 06:40:08.367', '2015-05-10 23:00:07.689', 2);


--
-- Data for Name: protectedsystem; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO protectedsystem VALUES (1, '2014-09-18 21:39:43.99', 'raspberry-server', '2014-09-18 21:39:43.99', true);
INSERT INTO protectedsystem VALUES (3, '2014-09-18 21:39:44.627', 'mac-server', '2014-09-18 21:39:44.627', true);
INSERT INTO protectedsystem VALUES (2, '2014-09-18 21:39:44.305', 'lenovo-server', '2015-05-24 18:10:01', false);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO roles VALUES ('USER', 'vasko');
INSERT INTO roles VALUES ('ADMINISTRATOR', 'vasko');


--
-- Data for Name: sequence; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO sequence VALUES ('SEQ_GEN', 50);


--
-- Name: credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);


--
-- Name: outage_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outage
    ADD CONSTRAINT outage_pkey PRIMARY KEY (id);


--
-- Name: protectedsystem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY protectedsystem
    ADD CONSTRAINT protectedsystem_pkey PRIMARY KEY (id);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_name, user_name);


--
-- Name: sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);


--
-- Name: unq_credentials_0; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT unq_credentials_0 UNIQUE (name);


--
-- Name: unq_protectedsystem_0; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY protectedsystem
    ADD CONSTRAINT unq_protectedsystem_0 UNIQUE (name);


--
-- Name: fk_outage_system_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY outage
    ADD CONSTRAINT fk_outage_system_id FOREIGN KEY (system_id) REFERENCES protectedsystem(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

