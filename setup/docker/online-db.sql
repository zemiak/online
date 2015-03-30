--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.2
-- Dumped by pg_dump version 9.3.1
-- Started on 2015-03-27 20:04:39 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 175 (class 3079 OID 12018)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 175
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 18162)
-- Name: credentials; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE credentials (
    id bigint NOT NULL,
    created timestamp without time zone,
    name character varying(255),
    password character varying(255)
);


--
-- TOC entry 173 (class 1259 OID 18175)
-- Name: outage; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE outage (
    id bigint NOT NULL,
    end_date timestamp without time zone,
    start_date timestamp without time zone,
    system_id bigint
);


--
-- TOC entry 172 (class 1259 OID 18170)
-- Name: protectedsystem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE protectedsystem (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    name character varying(255) NOT NULL,
    lastseen timestamp without time zone NOT NULL,
    disabled boolean NOT NULL
);


--
-- TOC entry 170 (class 1259 OID 18154)
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE roles (
    role_name character varying(255) NOT NULL,
    user_name character varying(255) NOT NULL
);


--
-- TOC entry 174 (class 1259 OID 26139)
-- Name: sequence; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


--
-- TOC entry 2228 (class 0 OID 18162)
-- Dependencies: 171
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO credentials (id, created, name, password) VALUES (1, '2014-08-30 11:30:57.374241', 'vasko', 'Jif60Je1RfBu4r50d2ING/J9KS6R/4Wn5bEDJvxCinw=');


--
-- TOC entry 2230 (class 0 OID 18175)
-- Dependencies: 173
-- Data for Name: outage; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2229 (class 0 OID 18170)
-- Dependencies: 172
-- Data for Name: protectedsystem; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO protectedsystem (id, created, name, lastseen, disabled) VALUES (1, '2014-09-18 21:39:43.99', 'raspberry-server', '2014-09-18 21:39:43.99', false);
INSERT INTO protectedsystem (id, created, name, lastseen, disabled) VALUES (2, '2014-09-18 21:39:44.305', 'lenovo-server', '2014-09-18 21:39:44.305', false);
INSERT INTO protectedsystem (id, created, name, lastseen, disabled) VALUES (3, '2014-09-18 21:39:44.627', 'mac-server', '2014-09-18 21:39:44.627', false);


--
-- TOC entry 2227 (class 0 OID 18154)
-- Dependencies: 170
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO roles (role_name, user_name) VALUES ('USER', 'vasko');
INSERT INTO roles (role_name, user_name) VALUES ('ADMINISTRATOR', 'vasko');


--
-- TOC entry 2231 (class 0 OID 26139)
-- Dependencies: 174
-- Data for Name: sequence; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO sequence (seq_name, seq_count) VALUES ('SEQ_GEN', 50);


--
-- TOC entry 2108 (class 2606 OID 18169)
-- Name: credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);


--
-- TOC entry 2116 (class 2606 OID 18179)
-- Name: outage_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY outage
    ADD CONSTRAINT outage_pkey PRIMARY KEY (id);


--
-- TOC entry 2112 (class 2606 OID 18174)
-- Name: protectedsystem_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY protectedsystem
    ADD CONSTRAINT protectedsystem_pkey PRIMARY KEY (id);


--
-- TOC entry 2106 (class 2606 OID 18161)
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_name, user_name);


--
-- TOC entry 2118 (class 2606 OID 26143)
-- Name: sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);


--
-- TOC entry 2110 (class 2606 OID 18181)
-- Name: unq_credentials_0; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT unq_credentials_0 UNIQUE (name);


--
-- TOC entry 2114 (class 2606 OID 18183)
-- Name: unq_protectedsystem_0; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY protectedsystem
    ADD CONSTRAINT unq_protectedsystem_0 UNIQUE (name);


--
-- TOC entry 2119 (class 2606 OID 18184)
-- Name: fk_outage_system_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY outage
    ADD CONSTRAINT fk_outage_system_id FOREIGN KEY (system_id) REFERENCES protectedsystem(id);


-- Completed on 2015-03-27 20:04:39 CET

--
-- PostgreSQL database dump complete
--

